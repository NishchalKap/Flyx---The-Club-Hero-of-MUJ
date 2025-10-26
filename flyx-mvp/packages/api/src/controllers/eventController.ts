import { Request, Response, NextFunction } from 'express';
import { CreateEventDTO, EventRegistrationDTO } from '@flyx/shared';
import { query } from '../config/database';
import { uploadToR2 } from '../utils/uploadToR2';
import { verifyRazorpayPayment } from '../utils/razorpay';

// Helper function to generate a unique ticket ID
const generateTicketId = () => `TKT-${Date.now()}`;

// Helper function to generate a mock QR code URL
const generateQRCode = async (ticketId: string) => {
  const url = `${process.env.R2_PUBLIC_URL}/qr/${ticketId}.png`;
  console.log(`[MOCK QR] Generating QR Code and uploading to ${url}`);
  return url;
};

/**
 * Create a new event and publish it.
 */
export const createEvent = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const { clubId } = req.user!;
    const eventData: CreateEventDTO = req.body;
    const bannerImage = req.file;

    if (!bannerImage) {
      return res.status(400).json({ message: 'Banner image is required.' });
    }

    // 1. Upload banner image
    const bannerUrl = await uploadToR2(bannerImage, 'events');

    // 2. Prepare data for insertion
    const { title, description, venue, registrationOpens, registrationCloses, eventStarts, eventEnds, isPaid, price, capacity } = eventData;
    const status = 'published'; // This endpoint directly publishes the event

    // 3. Insert into database
    const result = await query(
      `INSERT INTO events (club_id, title, description, banner_url, venue, registration_opens, registration_closes, event_starts, event_ends, is_paid, price, capacity, status)
       VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13)
       RETURNING *`,
      [clubId, title, description, bannerUrl, venue, registrationOpens, registrationCloses, eventStarts, eventEnds, isPaid, price, capacity, status]
    );

    const newEvent = result.rows[0];
    res.status(201).json(newEvent);
  } catch (error) {
    next(error);
  }
};

/**
 * Get a single event by its ID.
 */
export const getEventById = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const { id } = req.params;
    const result = await query('SELECT * FROM events WHERE id = $1', [id]);

    if (result.rows.length === 0) {
      return res.status(404).json({ message: 'Event not found' });
    }

    res.status(200).json(result.rows[0]);
  } catch (error) {
    next(error);
  }
};

/**
 * Update an existing event (e.g., save as draft or publish).
 */
export const updateEvent = async (req: Request, res: Response, next: NextFunction) => {
    try {
        const { id } = req.params;
        const { clubId } = req.user!;
        const eventData = req.body; // Can be partial data

        // Check if the event belongs to the club admin
        const eventResult = await query('SELECT club_id FROM events WHERE id = $1', [id]);
        if (eventResult.rows.length === 0) {
            return res.status(404).json({ message: 'Event not found' });
        }
        if (eventResult.rows[0].club_id !== clubId) {
            return res.status(403).json({ message: 'You are not authorized to update this event.' });
        }

        // Dynamically build the update query
        const fields = Object.keys(eventData);
        const values = Object.values(eventData);
        const setClause = fields.map((field, index) => `"${field}" = $${index + 1}`).join(', ');

        if (fields.length === 0) {
            return res.status(400).json({ message: 'No fields to update provided.' });
        }

        const queryText = `UPDATE events SET ${setClause}, updated_at = NOW() WHERE id = $${fields.length + 1} RETURNING *`;
        const queryParams = [...values, id];

        const result = await query(queryText, queryParams);

        res.status(200).json(result.rows[0]);
    } catch (error) {
        next(error);
    }
};


/**
 * Register a student for an event.
 */
export const registerForEvent = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const { id: eventId } = req.params;
    const { id: userId, name, email } = req.user!; // Assuming name/email are in the token
    const registrationData: EventRegistrationDTO = req.body;

    // 1. Fetch event details and check for validity
    const eventResult = await query('SELECT * FROM events WHERE id = $1', [eventId]);
    const event = eventResult.rows[0];

    if (!event) {
      return res.status(404).json({ message: 'Event not found.' });
    }
    if (new Date() > new Date(event.registration_closes)) {
      return res.status(400).json({ message: 'Registration for this event has closed.' });
    }
    if (event.capacity && event.registered_count >= event.capacity) {
      return res.status(400).json({ message: 'This event is sold out.' });
    }

    // 2. Handle payment for paid events
    let paymentStatus = 'completed'; // Default for free events
    if (event.is_paid) {
      const { paymentId } = registrationData;
      if (!paymentId) {
        return res.status(400).json({ message: 'Payment ID is required for paid events.' });
      }
      const isPaymentValid = await verifyRazorpayPayment(paymentId, event.price);
      if (!isPaymentValid) {
        paymentStatus = 'failed';
        // Decide if you want to record failed payments. For now, we'll just block.
        return res.status(400).json({ message: 'Payment verification failed.' });
      }
    }

    // 3. Create ticket and QR code
    const ticketId = generateTicketId();
    const qrCodeUrl = await generateQRCode(ticketId);

    // 4. Use a transaction to ensure atomicity
    await query('BEGIN');
    const registrationResult = await query(
      `INSERT INTO event_registrations (event_id, user_id, ticket_id, name, email, phone, branch, year, payment_status, payment_id, qr_code_url)
       VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
       RETURNING *`,
      [eventId, userId, ticketId, name, email, registrationData.phone, registrationData.branch, registrationData.year, paymentStatus, registrationData.paymentId, qrCodeUrl]
    );
    await query('UPDATE events SET registered_count = registered_count + 1 WHERE id = $1', [eventId]);
    await query('COMMIT');

    const newRegistration = registrationResult.rows[0];

    res.status(201).json({
      success: true,
      ticketId: newRegistration.ticket_id,
      qrCode: newRegistration.qr_code_url,
      eventDetails: event
    });

  } catch (error) {
    await query('ROLLBACK');
    next(error);
  }
};
