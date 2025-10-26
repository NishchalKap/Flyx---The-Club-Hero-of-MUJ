export interface Event {
  id: number;
  clubId: number;
  title: string;
  description: string;
  bannerUrl: string;
  venue: string;
  registrationOpens: Date;
  registrationCloses: Date;
  eventStarts: Date;
  eventEnds: Date;
  isPaid: boolean;
  price?: number;
  capacity?: number;
  registeredCount: number;
  status: 'draft' | 'published' | 'archived';
  createdAt: Date;
  updatedAt: Date;
}

// DTO for creating a new event from the admin panel
export interface CreateEventDTO {
  clubId: number;
  title: string;
  description: string;
  // bannerImage will be handled as a file upload, not JSON
  venue: string;
  registrationOpens: string; // ISO 8601 string
  registrationCloses: string; // ISO 8601 string
  eventStarts: string; // ISO 8601 string
  eventEnds: string; // ISO 8601 string
  isPaid: boolean;
  price?: number;
  capacity?: number;
}

// DTO for student registration
export interface EventRegistrationDTO {
  phone: string;
  branch: string;
  year: string;
  paymentId?: string; // For paid events, from Razorpay
}
