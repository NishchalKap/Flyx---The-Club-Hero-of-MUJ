import { Link } from 'react-router-dom'
import { Calendar, MapPin, Users, Clock } from 'lucide-react'
import { Event } from '@/types'
import { cn } from '@/utils/cn'

interface EventCardProps {
  event: Event
  className?: string
}

export default function EventCard({ event, className }: EventCardProps) {
  const formatDate = (dateString: string) => {
    const date = new Date(dateString)
    return date.toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
    })
  }

  const formatTime = (timeString: string) => {
    return new Date(`2000-01-01T${timeString}`).toLocaleTimeString('en-US', {
      hour: 'numeric',
      minute: '2-digit',
      hour12: true,
    })
  }

  return (
    <Link
      to={`/event/${event.id}`}
      className={cn('block', className)}
    >
      <div className="card hover:shadow-elevated transition-all duration-200 overflow-hidden">
        {/* Event Image */}
        <div className="relative h-48 bg-neutral-200">
          <img
            src={event.coverImage}
            alt={event.title}
            className="w-full h-full object-cover"
          />
          {event.isLive && (
            <div className="absolute top-3 left-3 bg-red-500 text-white px-2 py-1 rounded-full text-xs font-medium flex items-center">
              <div className="w-2 h-2 bg-white rounded-full mr-1 animate-pulse" />
              LIVE
            </div>
          )}
          {!event.isFree && (
            <div className="absolute top-3 right-3 bg-white/90 backdrop-blur-sm px-2 py-1 rounded-lg text-xs font-medium">
              Paid Event
            </div>
          )}
        </div>

        {/* Event Details */}
        <div className="p-4">
          <div className="flex items-start justify-between mb-2">
            <h3 className="text-lg font-semibold text-neutral-900 line-clamp-2">
              {event.title}
            </h3>
            <span className="ml-2 text-xs text-neutral-500 bg-neutral-100 px-2 py-1 rounded-full">
              {event.category}
            </span>
          </div>

          <p className="text-neutral-600 text-sm mb-3 line-clamp-2">
            {event.shortDescription || event.description}
          </p>

          {/* Event Meta */}
          <div className="space-y-2">
            <div className="flex items-center text-sm text-neutral-600">
              <Calendar className="w-4 h-4 mr-2" />
              <span>{formatDate(event.date)}</span>
              <Clock className="w-4 h-4 ml-3 mr-2" />
              <span>{formatTime(event.time)}</span>
            </div>

            <div className="flex items-center text-sm text-neutral-600">
              <MapPin className="w-4 h-4 mr-2" />
              <span className="truncate">{event.location}</span>
            </div>

            <div className="flex items-center justify-between text-sm">
              <div className="flex items-center text-neutral-600">
                <Users className="w-4 h-4 mr-2" />
                <span>{event.currentAttendees}/{event.maxCapacity} attending</span>
              </div>
              {event.ticketTiers.length > 0 && (
                <div className="text-primary-500 font-medium">
                  {event.isFree ? 'Free' : `From ₹${Math.min(...event.ticketTiers.map(t => t.price))}`}
                </div>
              )}
            </div>
          </div>

          {/* Tags */}
          {event.tags.length > 0 && (
            <div className="flex flex-wrap gap-1 mt-3">
              {event.tags.slice(0, 3).map((tag) => (
                <span
                  key={tag}
                  className="text-xs bg-primary-50 text-primary-600 px-2 py-1 rounded-full"
                >
                  {tag}
                </span>
              ))}
              {event.tags.length > 3 && (
                <span className="text-xs text-neutral-500">
                  +{event.tags.length - 3} more
                </span>
              )}
            </div>
          )}
        </div>
      </div>
    </Link>
  )
}



