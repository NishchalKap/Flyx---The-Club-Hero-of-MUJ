import React, { useState, useEffect } from 'react';
import { Event, EventRegistrationDTO } from '@flyx/shared';

interface RegistrationFormProps {
  event: Event;
  onSubmit: (formData: EventRegistrationDTO) => void;
  isSubmitting: boolean;
}

const RegistrationForm: React.FC<RegistrationFormProps> = ({ event, onSubmit, isSubmitting }) => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    branch: '',
    year: '',
  });

  useEffect(() => {
    // Pre-fill from URL params
    const params = new URLSearchParams(window.location.search);
    setFormData({
      name: params.get('name') || '',
      email: params.get('email') || '',
      phone: params.get('phone') || '',
      branch: '',
      year: '',
    });
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const submissionData: EventRegistrationDTO = {
      phone: formData.phone,
      branch: formData.branch,
      year: formData.year,
    };
    onSubmit(submissionData);
  };

  return (
    <form onSubmit={handleSubmit} className="p-6 space-y-4">
      <input type="text" name="name" value={formData.name} readOnly placeholder="Name" className="w-full p-3 border rounded-lg bg-gray-100" />
      <input type="email" name="email" value={formData.email} readOnly placeholder="Email" className="w-full p-3 border rounded-lg bg-gray-100" />
      <input type="tel" name="phone" value={formData.phone} onChange={handleChange} placeholder="Phone Number" className="w-full p-3 border rounded-lg" required />
      <select name="branch" value={formData.branch} onChange={handleChange} className="w-full p-3 border rounded-lg" required>
        <option value="" disabled>Select Branch</option>
        <option>CSE</option>
        <option>ECE</option>
        <option>ME</option>
        <option>CE</option>
        <option>EE</option>
        <option>Other</option>
      </select>
      <select name="year" value={formData.year} onChange={handleChange} className="w-full p-3 border rounded-lg" required>
        <option value="" disabled>Select Year</option>
        <option>1st</option>
        <option>2nd</option>
        <option>3rd</option>
        <option>4th</option>
      </select>
      <button type="submit" disabled={isSubmitting} className="w-full p-3 bg-primary-purple text-white font-bold rounded-lg disabled:bg-gray-400">
        {isSubmitting ? 'Processing...' : (event.isPaid ? `Pay & Register for ₹${event.price}` : 'Register for Free')}
      </button>
    </form>
  );
};

export default RegistrationForm;
