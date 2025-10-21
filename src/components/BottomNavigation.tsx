import { Home, Search, Calendar, User, Menu } from 'lucide-react'
import { Link, useLocation } from 'react-router-dom'
import { cn } from '@/utils/cn'

export default function BottomNavigation() {
  const location = useLocation()

  const navItems = [
    {
      path: '/',
      icon: Home,
      label: 'Home',
    },
    {
      path: '/search',
      icon: Search,
      label: 'Search',
    },
    {
      path: '/calendar',
      icon: Calendar,
      label: 'Calendar',
    },
    {
      path: '/profile',
      icon: User,
      label: 'Profile',
    },
    {
      path: '/settings',
      icon: Menu,
      label: 'Menu',
    },
  ]

  return (
    <nav className="fixed bottom-0 left-0 right-0 z-50 bg-white/90 backdrop-blur-md border-t border-neutral-200 safe-area-bottom">
      <div className="flex items-center justify-around px-2 py-2">
        {navItems.map((item) => {
          const isActive = location.pathname === item.path
          const Icon = item.icon

          return (
            <Link
              key={item.path}
              to={item.path}
              className={cn(
                'flex flex-col items-center justify-center p-2 rounded-xl transition-all duration-200 min-w-0 flex-1',
                isActive
                  ? 'text-primary-500 bg-primary-50'
                  : 'text-neutral-600 hover:text-primary-500 hover:bg-primary-50'
              )}
            >
              <Icon className={cn('w-5 h-5', isActive && 'scale-110')} />
              <span className="text-xs font-medium mt-1 truncate">
                {item.label}
              </span>
            </Link>
          )
        })}
      </div>
    </nav>
  )
}



