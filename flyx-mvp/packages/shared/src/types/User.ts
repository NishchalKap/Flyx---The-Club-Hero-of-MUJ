export interface User {
  id: number;
  name: string;
  email: string;
  // Add other user properties as needed, e.g., roles
}

export interface AuthenticatedUser {
  id: number;
  clubId?: number; // For admins
  role: 'student' | 'admin';
  name: string;
  email: string;
}
