// User Types
export interface User {
  id: string;
  email: string;
  name: string;
  profilePicture?: string;
  university: string;
  branch?: string;
  year?: number;
  interests: string[];
  joinedClubs: string[];
  attendedEvents: string[];
  achievements: Achievement[];
  createdAt: string;
  updatedAt: string;
}

export interface Achievement {
  id: string;
  title: string;
  description: string;
  icon: string;
  earnedAt: string;
  eventId?: string;
  clubId?: string;
}

// Event Types
export interface Event {
  id: string;
  title: string;
  description: string;
  shortDescription?: string;
  coverImage: string;
  gallery?: string[];
  organizerId: string;
  clubId?: string;
  category: EventCategory;
  subcategory?: string;
  date: string;
  endDate?: string;
  time: string;
  endTime?: string;
  location: string;
  venue?: string;
  maxCapacity: number;
  currentAttendees: number;
  ticketTiers: TicketTier[];
  registrationDeadline?: string;
  isFree: boolean;
  tags: string[];
  requirements?: string[];
  status: EventStatus;
  isLive: boolean;
  liveFeedEnabled: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface TicketTier {
  id: string;
  name: string;
  price: number;
  description?: string;
  maxQuantity?: number;
  currentSold: number;
  benefits?: string[];
  isMemberOnly?: boolean;
}

export type EventCategory = 
  | 'technical'
  | 'cultural'
  | 'sports'
  | 'academic'
  | 'entrepreneurship'
  | 'social'
  | 'workshop'
  | 'competition'
  | 'conference'
  | 'other';

export type EventStatus = 
  | 'draft'
  | 'published'
  | 'cancelled'
  | 'completed';

// Club Types
export interface Club {
  id: string;
  name: string;
  description: string;
  coverImage: string;
  logo?: string;
  category: ClubCategory;
  foundedYear: number;
  memberCount: number;
  followerCount: number;
  socialLinks: SocialLink[];
  gallery: string[];
  upcomingEvents: string[];
  achievements: string[];
  isVerified: boolean;
  admins: string[];
  createdAt: string;
  updatedAt: string;
}

export interface SocialLink {
  platform: 'instagram' | 'linkedin' | 'twitter' | 'facebook' | 'website';
  url: string;
}

export type ClubCategory = 
  | 'technical'
  | 'cultural'
  | 'sports'
  | 'academic'
  | 'entrepreneurship'
  | 'social'
  | 'environmental'
  | 'other';

// Booking Types
export interface Booking {
  id: string;
  eventId: string;
  userId: string;
  ticketTierId: string;
  quantity: number;
  totalAmount: number;
  status: BookingStatus;
  paymentId?: string;
  qrCode: string;
  checkInStatus: CheckInStatus;
  teamMembers?: TeamMember[];
  customFields?: Record<string, any>;
  createdAt: string;
  updatedAt: string;
}

export interface TeamMember {
  id: string;
  name: string;
  email: string;
  phone?: string;
  customFields?: Record<string, any>;
}

export type BookingStatus = 
  | 'pending'
  | 'confirmed'
  | 'cancelled'
  | 'refunded';

export type CheckInStatus = 
  | 'not_checked_in'
  | 'checked_in'
  | 'late';

// Story Types
export interface Story {
  id: string;
  userId: string;
  eventId?: string;
  content: StoryContent;
  isLive: boolean;
  views: number;
  expiresAt: string;
  createdAt: string;
}

export interface StoryContent {
  type: 'image' | 'video' | 'text';
  url?: string;
  text?: string;
  backgroundColor?: string;
  textColor?: string;
}

// Notification Types
export interface Notification {
  id: string;
  userId: string;
  type: NotificationType;
  title: string;
  message: string;
  data?: Record<string, any>;
  isRead: boolean;
  createdAt: string;
}

export type NotificationType = 
  | 'event_reminder'
  | 'event_cancelled'
  | 'club_update'
  | 'booking_confirmed'
  | 'new_follower'
  | 'achievement_earned'
  | 'general';

// Opportunity Types
export interface Opportunity {
  id: string;
  title: string;
  description: string;
  organizer: string;
  category: OpportunityCategory;
  deadline: string;
  prize?: string;
  requirements: string[];
  link: string;
  isExternal: boolean;
  tags: string[];
  createdAt: string;
}

export type OpportunityCategory = 
  | 'hackathon'
  | 'competition'
  | 'internship'
  | 'scholarship'
  | 'fellowship'
  | 'conference'
  | 'workshop'
  | 'other';

// API Response Types
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  data: T[];
  pagination: {
    page: number;
    limit: number;
    total: number;
    totalPages: number;
  };
}

// Form Types
export interface LoginForm {
  email: string;
  password: string;
}

export interface RegisterForm {
  name: string;
  email: string;
  password: string;
  university: string;
  branch?: string;
  year?: number;
}

export interface OnboardingForm {
  interests: string[];
  clubs: string[];
  profilePicture?: File;
}

// Search Types
export interface SearchFilters {
  category?: string;
  date?: string;
  price?: 'free' | 'paid';
  location?: string;
  tags?: string[];
}

export interface SearchResult {
  events: Event[];
  clubs: Club[];
  opportunities: Opportunity[];
  users: User[];
}

