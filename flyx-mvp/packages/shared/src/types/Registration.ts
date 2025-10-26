export interface Registration {
  id: number;
  eventId: number;
  userId: number;
  ticketId: string;
  name: string;
  email: string;
  phone: string;
  branch: string;
  year: string;
  paymentStatus: 'pending' | 'completed' | 'failed';
  paymentId?: string;
  qrCodeUrl: string;
  registeredAt: Date;
}
