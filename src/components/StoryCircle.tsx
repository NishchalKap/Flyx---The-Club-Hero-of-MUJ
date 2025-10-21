import { Link } from 'react-router-dom'
import { cn } from '@/utils/cn'

interface StoryCircleProps {
  id: string
  name: string
  image: string
  isLive?: boolean
  hasNewStories?: boolean
  className?: string
}

export default function StoryCircle({ 
  id, 
  name, 
  image, 
  isLive = false, 
  hasNewStories = false,
  className 
}: StoryCircleProps) {
  return (
    <Link
      to={`/stories/${id}`}
      className={cn('flex flex-col items-center space-y-1', className)}
    >
      <div className="relative">
        {/* Story Ring */}
        <div
          className={cn(
            'w-16 h-16 rounded-full p-0.5',
            hasNewStories
              ? 'bg-gradient-to-r from-primary-500 to-secondary-500'
              : 'bg-neutral-300'
          )}
        >
          <div className="w-full h-full rounded-full bg-white p-0.5">
            <img
              src={image}
              alt={name}
              className="w-full h-full rounded-full object-cover"
            />
          </div>
        </div>

        {/* Live indicator */}
        {isLive && (
          <div className="absolute -bottom-1 -right-1 w-4 h-4 bg-red-500 rounded-full border-2 border-white flex items-center justify-center">
            <div className="w-2 h-2 bg-white rounded-full animate-pulse" />
          </div>
        )}
      </div>

      {/* Story Name */}
      <span className="text-xs text-neutral-600 text-center max-w-16 truncate">
        {name}
      </span>
    </Link>
  )
}
