import React, { useEffect, useState } from 'react';
import { Event, EventRegistrationDTO } from '@flyx/shared';
import apiClient from '../utils/apiClient';
import RegistrationForm from '../components/RegistrationForm';
import Confetti from 'react-confetti';

// Extend the Window interface for postMessage
declare global {
  interface Window {
    ReactNativeWebView: {
      postMessage: (message: string) => void;
    };
  }
}

const RegistrationPage = () => {
  const [event, setEvent] = useState<Event | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [registrationSuccess, setRegistrationSuccess] = useState(false);

  const eventId = window.location.pathname.split('/').pop() || '1';

  useEffect(() => {
    const fetchEvent = async () => {
      try {
        const response = await apiClient.get<Event>(`/events/${eventId}`);
        setEvent(response.data);
      } catch (err) {
        setError('Failed to load event details.');
      } finally {
        setIsLoading(false);
      }
    };
    fetchEvent();
  }, [eventId]);

  const handleRegistration = async (formData: EventRegistrationDTO) => {
    setIsSubmitting(true);
    try {
      // For paid events, this is where Razorpay checkout would be initiated.
      // We will mock this and proceed directly to API call.

      const response = await apiClient.post(`/events/${eventId}/register`, formData);

      setRegistrationSuccess(true);

      // Send message to React Native app
      setTimeout(() => {
        if (window.ReactNativeWebView) {
          window.ReactNativeWebView.postMessage(JSON.stringify({
            type: 'REGISTRATION_SUCCESS',
            ...response.data
          }));
        }
      }, 2000); // Wait for confetti animation

    } catch (err) {
      setError('Registration failed. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  if (isLoading) return <div className="p-8 text-center">Loading...</div>;
  if (error) return <div className="p-8 text-center text-red-500">{error}</div>;
  if (!event) return <div className="p-8 text-center">Event not found.</div>;

  if (registrationSuccess) {
    return (
      <div className="text-center p-8">
        <Confetti numberOfPieces={200} />
        <h2 className="text-2xl font-bold text-green-600">Registration Complete!</h2>
        <p>You can now close this window.</p>
      </div>
    );
  }

  return (
    <div className="max-w-md mx-auto bg-white shadow-lg rounded-lg overflow-hidden my-4">
      <img src={event.bannerUrl} alt={event.title} className="w-full h-48 object-cover" />
      <div className="p-6">
        <h1 className="text-2xl font-bold text-text-dark mb-2">{event.title}</h1>
        <p className="text-gray-600 mb-4">{event.description}</p>
      </div>
      <RegistrationForm event={event} onSubmit={handleRegistration} isSubmitting={isSubmitting} />
    </div>
  );
};

export default RegistrationPage;
