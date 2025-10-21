import { Bell, Search } from 'lucide-react'
import { Link } from 'react-router-dom'
import { useAuth } from '@/contexts/AuthContext'

export default function TopNavigation() {
  const { user } = useAuth()

  return (
    <header className="fixed top-0 left-0 right-0 z-50 bg-white/80 backdrop-blur-md border-b border-neutral-200 safe-area-top">
      <div className="flex items-center justify-between px-4 h-16">
        {/* University Logo/Branding */}
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8 bg-gradient-to-r from-primary-500 to-secondary-500 rounded-lg flex items-center justify-center">
            <span className="text-white font-bold text-sm">F</span>
          </div>
          <span className="text-xl font-bold text-gradient">Flyx</span>
        </div>

        {/* Right side actions */}
        <div className="flex items-center space-x-3">
          <Link
            to="/search"
            className="p-2 text-neutral-600 hover:text-primary-500 transition-colors"
          >
            <Search className="w-5 h-5" />
          </Link>
          <Link
            to="/notifications"
            className="p-2 text-neutral-600 hover:text-primary-500 transition-colors relative"
          >
            <Bell className="w-5 h-5" />
            {/* Notification badge */}
            <span className="absolute -top-1 -right-1 w-3 h-3 bg-accent-500 rounded-full text-xs flex items-center justify-center text-white">
              3
            </span>
          </Link>
        </div>
      </div>
    </header>
  )
}



