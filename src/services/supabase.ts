import { createClient } from '@supabase/supabase-js'
import type { Event as AppEvent, TicketTier as AppTicketTier } from '@/types'

const supabaseUrl = import.meta.env.VITE_SUPABASE_URL || 'https://your-project.supabase.co'
const supabaseAnonKey = import.meta.env.VITE_SUPABASE_ANON_KEY || 'your-anon-key'

export const supabase = createClient(supabaseUrl, supabaseAnonKey, {
  auth: {
    autoRefreshToken: true,
    persistSession: true,
    detectSessionInUrl: true
  }
})

// Database types (these would be generated from your Supabase schema)
export interface Database {
  public: {
    Tables: {
      users: {
        Row: {
          id: string
          email: string
          name: string
          profile_picture?: string
          university: string
          branch?: string
          year?: number
          interests: string[]
          joined_clubs: string[]
          attended_events: string[]
          created_at: string
          updated_at: string
        }
        Insert: {
          id: string
          email: string
          name: string
          profile_picture?: string
          university: string
          branch?: string
          year?: number
          interests?: string[]
          joined_clubs?: string[]
          attended_events?: string[]
          created_at?: string
          updated_at?: string
        }
        Update: {
          id?: string
          email?: string
          name?: string
          profile_picture?: string
          university?: string
          branch?: string
          year?: number
          interests?: string[]
          joined_clubs?: string[]
          attended_events?: string[]
          created_at?: string
          updated_at?: string
        }
      }
      events: {
        Row: {
          id: string
          title: string
          description: string
          short_description?: string
          cover_image: string
          gallery?: string[]
          organizer_id: string
          club_id?: string
          category: string
          subcategory?: string
          date: string
          end_date?: string
          time: string
          end_time?: string
          location: string
          venue?: string
          max_capacity: number
          current_attendees: number
          is_free: boolean
          tags: string[]
          requirements?: string[]
          status: string
          is_live: boolean
          live_feed_enabled: boolean
          created_at: string
          updated_at: string
        }
        Insert: {
          id?: string
          title: string
          description: string
          short_description?: string
          cover_image: string
          gallery?: string[]
          organizer_id: string
          club_id?: string
          category: string
          subcategory?: string
          date: string
          end_date?: string
          time: string
          end_time?: string
          location: string
          venue?: string
          max_capacity: number
          current_attendees?: number
          is_free: boolean
          tags?: string[]
          requirements?: string[]
          status?: string
          is_live?: boolean
          live_feed_enabled?: boolean
          created_at?: string
          updated_at?: string
        }
        Update: {
          id?: string
          title?: string
          description?: string
          short_description?: string
          cover_image?: string
          gallery?: string[]
          organizer_id?: string
          club_id?: string
          category?: string
          subcategory?: string
          date?: string
          end_date?: string
          time?: string
          end_time?: string
          location?: string
          venue?: string
          max_capacity?: number
          current_attendees?: number
          is_free?: boolean
          tags?: string[]
          requirements?: string[]
          status?: string
          is_live?: boolean
          live_feed_enabled?: boolean
          created_at?: string
          updated_at?: string
        }
      }
      clubs: {
        Row: {
          id: string
          name: string
          description: string
          cover_image: string
          logo?: string
          category: string
          founded_year: number
          member_count: number
          follower_count: number
          social_links: any[]
          gallery: string[]
          upcoming_events: string[]
          achievements: string[]
          is_verified: boolean
          admins: string[]
          created_at: string
          updated_at: string
        }
        Insert: {
          id?: string
          name: string
          description: string
          cover_image: string
          logo?: string
          category: string
          founded_year: number
          member_count?: number
          follower_count?: number
          social_links?: any[]
          gallery?: string[]
          upcoming_events?: string[]
          achievements?: string[]
          is_verified?: boolean
          admins: string[]
          created_at?: string
          updated_at?: string
        }
        Update: {
          id?: string
          name?: string
          description?: string
          cover_image?: string
          logo?: string
          category?: string
          founded_year?: number
          member_count?: number
          follower_count?: number
          social_links?: any[]
          gallery?: string[]
          upcoming_events?: string[]
          achievements?: string[]
          is_verified?: boolean
          admins?: string[]
          created_at?: string
          updated_at?: string
        }
      }
    }
  }
}

// --- App-specific query helpers ---

type DbEventRow = Database['public']['Tables']['events']['Row']

function mapDbEventToAppEvent(row: DbEventRow): AppEvent {
  const ticketTiers: AppTicketTier[] = [] // Fetch separately when needed
  return {
    id: row.id,
    title: row.title,
    description: row.description,
    shortDescription: row.short_description,
    coverImage: row.cover_image,
    gallery: row.gallery || [],
    organizerId: row.organizer_id,
    clubId: row.club_id,
    category: row.category as AppEvent['category'],
    subcategory: row.subcategory,
    date: row.date,
    endDate: row.end_date,
    time: row.time,
    endTime: row.end_time,
    location: row.location,
    venue: row.venue,
    maxCapacity: row.max_capacity,
    currentAttendees: row.current_attendees,
    ticketTiers,
    registrationDeadline: undefined,
    isFree: row.is_free,
    tags: row.tags || [],
    requirements: row.requirements || [],
    status: row.status as AppEvent['status'],
    isLive: row.is_live,
    liveFeedEnabled: row.live_feed_enabled,
    createdAt: row.created_at,
    updatedAt: row.updated_at,
  }
}

export async function listPublishedEvents(limit = 20): Promise<AppEvent[]> {
  const { data, error } = await supabase
    .from('events')
    .select('*')
    .eq('status', 'published')
    .order('date', { ascending: true })
    .limit(limit)

  if (error) throw error
  return (data || []).map(mapDbEventToAppEvent)
}

