import { useState, useEffect } from 'react'
import { Event } from '@/types'
import EventCard from '@/components/EventCard'
import StoryCircle from '@/components/StoryCircle'
import LoadingSpinner from '@/components/LoadingSpinner'
import { listPublishedEvents } from '@/services/supabase'

export default function HomePage() {
  const [events, setEvents] = useState<Event[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const load = async () => {
      try {
        const data = await listPublishedEvents(20)
        setEvents(data)
      } catch (e) {
        console.error('Failed to load events', e)
        setEvents([])
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [])

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <LoadingSpinner size="lg" />
      </div>
    )
  }

  return (
    <div className="px-4 py-6">
      {/* Stories Section */}
      <div className="mb-6">
        <h2 className="text-lg font-semibold mb-3 text-neutral-900">Live Stories</h2>
        <div className="flex space-x-4 overflow-x-auto scrollbar-hide pb-2">
          {/* Story circles will be populated here */}
          <div className="flex-shrink-0">
            <StoryCircle
              id="1"
              name="Tech Club"
              image="/api/placeholder/60/60"
              isLive={true}
              hasNewStories={true}
            />
          </div>
          <div className="flex-shrink-0">
            <StoryCircle
              id="2"
              name="Cultural Fest"
              image="/api/placeholder/60/60"
              isLive={false}
              hasNewStories={true}
            />
          </div>
        </div>
      </div>

      {/* Feed Tabs */}
      <div className="flex space-x-1 mb-6 bg-neutral-100 p-1 rounded-xl">
        {['For You', 'Following', 'Trending', 'Near You'].map((tab) => (
          <button
            key={tab}
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-all ${
              tab === 'For You'
                ? 'bg-white text-primary-500 shadow-sm'
                : 'text-neutral-600 hover:text-neutral-900'
            }`}
          >
            {tab}
          </button>
        ))}
      </div>

      {/* Events Feed */}
      <div className="space-y-4">
        {events.length === 0 ? (
          <div className="text-center py-12">
            <div className="w-16 h-16 bg-neutral-200 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg className="w-8 h-8 text-neutral-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 className="text-lg font-medium text-neutral-900 mb-2">No events yet</h3>
            <p className="text-neutral-600 mb-4">
              Discover amazing events happening on your campus
            </p>
            <button className="btn-primary">
              Explore Events
            </button>
          </div>
        ) : (
          events.map((event) => (
            <EventCard key={event.id} event={event} />
          ))
        )}
      </div>

      {/* Floating Action Button for Admin */}
      <button 
        className="fixed bottom-24 right-4 w-14 h-14 bg-primary-500 text-white rounded-full shadow-lg hover:bg-primary-600 transition-all duration-200 flex items-center justify-center"
        title="Create Event"
        aria-label="Create Event"
      >
        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
        </svg>
      </button>
    </div>
  )
}
