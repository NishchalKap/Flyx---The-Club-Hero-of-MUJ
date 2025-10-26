import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import eventRoutes from './routes/events';
import { errorHandler } from './middleware/errorHandler';

// Load environment variables
dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Health Check Route
app.get('/api/health', (req, res) => {
  res.status(200).json({ status: 'ok', message: 'API is healthy' });
});

// API Routes
app.use('/api/events', eventRoutes);

// Error Handling Middleware
app.use(errorHandler);

// Start Server
app.listen(PORT, () => {
  console.log(`API server running on http://localhost:${PORT}`);
});
