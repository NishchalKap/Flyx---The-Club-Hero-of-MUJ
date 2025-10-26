import { useState } from 'react';
import { CreateEventDTO, Event } from '@flyx/shared';
import apiClient from '../utils/apiClient';

type FormState = Omit<CreateEventDTO, 'clubId' | 'bannerImage'> & {
  bannerImage: File | null;
};
type FormErrors = Partial<Record<keyof FormState, string>>;

const useEventForm = () => {
  const [formData, setFormData] = useState<FormState>({
    title: '',
    description: '',
    bannerImage: null,
    venue: '',
    registrationOpens: '',
    registrationCloses: '',
    eventStarts: '',
    eventEnds: '',
    isPaid: false,
    price: 0,
    capacity: 0,
  });
  const [errors, setErrors] = useState<FormErrors>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [shareableUrl, setShareableUrl] = useState('');

  const validate = (): boolean => {
    const newErrors: FormErrors = {};
    if (!formData.title) newErrors.title = 'Title is required.';
    if (!formData.description) newErrors.description = 'Description is required.';
    if (!formData.bannerImage) newErrors.bannerImage = 'Banner image is required.';
    if (!formData.venue) newErrors.venue = 'Venue is required.';
    // Add date validation etc. here
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) {
      return;
    }
    setIsSubmitting(true);

    const postData = new FormData();
    Object.entries(formData).forEach(([key, value]) => {
      if (value !== null) {
        if (key === 'bannerImage' && value instanceof File) {
          postData.append(key, value, value.name);
        } else if (typeof value === 'boolean') {
          postData.append(key, String(value));
        }
        else {
          postData.append(key, value as string);
        }
      }
    });
    // Add a mock clubId as the backend expects it from the authenticated user
    postData.append('clubId', '123');

    try {
      const response = await apiClient.post<Event>('/events', postData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      setIsSuccess(true);
      setShareableUrl(`http://localhost:3002/register/${response.data.id}`); // Link to registration page
    } catch (error) {
      console.error('Failed to create event:', error);
      // Handle API errors here, e.g., setErrors(...)
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value, type } = e.target;
    setFormData(prev => ({ ...prev, [name]: type === 'number' ? Number(value) : value }));
  };

  const handleFileSelect = (file: File | null) => {
    setFormData(prev => ({ ...prev, bannerImage: file }));
  };

  const handleToggle = (enabled: boolean) => {
    setFormData(prev => ({ ...prev, isPaid: enabled }));
  };

  const closeModal = () => {
    setIsSuccess(false);
    // Optionally reset form here
  };

  return {
    formData,
    errors,
    isSubmitting,
    isSuccess,
    shareableUrl,
    handleChange,
    handleFileSelect,
    handleToggle,
    handleSubmit,
    closeModal
  };
};

export default useEventForm;
