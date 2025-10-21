import { Outlet } from 'react-router-dom'
import { useAuth } from '@/contexts/AuthContext'
import BottomNavigation from '@/components/BottomNavigation'
import TopNavigation from '@/components/TopNavigation'
import LoadingSpinner from '@/components/LoadingSpinner'

export default function Layout() {
  const { user, loading } = useAuth()

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <LoadingSpinner size="lg" />
      </div>
    )
  }

  if (!user) {
    // Redirect to login - this will be handled by the router
    return null
  }

  return (
    <div className="min-h-screen bg-light">
      <TopNavigation />
      <main className="pb-20 pt-16">
        <Outlet />
      </main>
      <BottomNavigation />
    </div>
  )
}



