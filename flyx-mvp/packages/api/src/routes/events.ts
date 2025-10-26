import { Router } from 'express';
import multer from 'multer';
import { createEvent, getEventById, registerForEvent, updateEvent } from '../controllers/eventController';
import { authMiddleware, clubAdminMiddleware } from '../middleware/auth';

const router = Router();

// Configure multer for image uploads
const storage = multer.memoryStorage();
const upload = multer({
  storage: storage,
  limits: { fileSize: 5 * 1024 * 1024 }, // 5MB limit
  fileFilter: (req, file, cb) => {
    if (file.mimetype === 'image/jpeg' || file.mimetype === 'image/png') {
      cb(null, true);
    } else {
      cb(new Error('Invalid file type. Only JPEG and PNG are allowed.'));
    }
  }
});

// --- Public Routes ---
router.get('/:id', getEventById);

// --- Student Routes ---
router.post('/:id/register', authMiddleware, registerForEvent);

// --- Club Admin Routes ---
router.post('/', authMiddleware, clubAdminMiddleware, upload.single('bannerImage'), createEvent);
router.patch('/:id', authMiddleware, clubAdminMiddleware, updateEvent);
// Note: A dedicated draft endpoint might be redundant if the main create/update handles a 'status' field.
// Let's stick to POST for creation and PATCH for updates (including saving drafts).

export default router;
