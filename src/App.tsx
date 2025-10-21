import { Routes, Route } from 'react-router-dom'
import { AuthProvider } from '@/contexts/AuthContext'
import { ThemeProvider } from '@/contexts/ThemeContext'
import Layout from '@/components/Layout'

// Pages
import HomePage from '@/pages/HomePage'
import LoginPage from '@/pages/LoginPage'
import RegisterPage from '@/pages/RegisterPage'
import OnboardingPage from '@/pages/OnboardingPage'
import EventDetailPage from '@/pages/EventDetailPage'
import BookingPage from '@/pages/BookingPage'
import PaymentPage from '@/pages/PaymentPage'
import TicketPage from '@/pages/TicketPage'
import ClubDetailPage from '@/pages/ClubDetailPage'
import ClubsPage from '@/pages/ClubsPage'
import OpportunitiesPage from '@/pages/OpportunitiesPage'
import SearchPage from '@/pages/SearchPage'
import ProfilePage from '@/pages/ProfilePage'
import EditProfilePage from '@/pages/EditProfilePage'
import NotificationsPage from '@/pages/NotificationsPage'
import TicketsPage from '@/pages/TicketsPage'
import CalendarPage from '@/pages/CalendarPage'
import WalletPage from '@/pages/WalletPage'
import SettingsPage from '@/pages/SettingsPage'
import AdminDashboardPage from '@/pages/admin/AdminDashboardPage'
import CreateEventPage from '@/pages/admin/CreateEventPage'
import MemberManagementPage from '@/pages/admin/MemberManagementPage'
import StoryViewerPage from '@/pages/StoryViewerPage'
import LiveEventPage from '@/pages/LiveEventPage'

function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <div className="min-h-screen bg-light">
          <Routes>
            {/* Public Routes */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            
            {/* Protected Routes */}
            <Route path="/" element={<Layout />}>
              <Route index element={<HomePage />} />
              <Route path="onboarding" element={<OnboardingPage />} />
              
              {/* Event Routes */}
              <Route path="event/:id" element={<EventDetailPage />} />
              <Route path="booking/:eventId" element={<BookingPage />} />
              <Route path="payment/:bookingId" element={<PaymentPage />} />
              <Route path="ticket/:ticketId" element={<TicketPage />} />
              
              {/* Club Routes */}
              <Route path="club/:id" element={<ClubDetailPage />} />
              <Route path="clubs" element={<ClubsPage />} />
              
              {/* Discovery Routes */}
              <Route path="opportunities" element={<OpportunitiesPage />} />
              <Route path="search" element={<SearchPage />} />
              
              {/* User Routes */}
              <Route path="profile" element={<ProfilePage />} />
              <Route path="profile/edit" element={<EditProfilePage />} />
              <Route path="notifications" element={<NotificationsPage />} />
              <Route path="tickets" element={<TicketsPage />} />
              <Route path="calendar" element={<CalendarPage />} />
              <Route path="wallet" element={<WalletPage />} />
              <Route path="settings" element={<SettingsPage />} />
              
              {/* Admin Routes */}
              <Route path="admin/dashboard" element={<AdminDashboardPage />} />
              <Route path="admin/event/create" element={<CreateEventPage />} />
              <Route path="admin/members" element={<MemberManagementPage />} />
              
              {/* Social Routes */}
              <Route path="stories/:id" element={<StoryViewerPage />} />
              <Route path="live/:eventId" element={<LiveEventPage />} />
            </Route>
          </Routes>
        </div>
      </AuthProvider>
    </ThemeProvider>
  )
}

export default App

