import React from 'react';
import TextInput from '../components/TextInput';
import TextareaInput from '../components/TextareaInput';
import ImageUploader from '../components/ImageUploader';
import DateTimePicker from '../components/DateTimePicker';
import ToggleSwitch from '../components/ToggleSwitch';
import useEventForm from '../hooks/useEventForm';
import SuccessModal from '../components/SuccessModal';

const CreateEventPage = () => {
  const {
    formData,
    errors,
    isSubmitting,
    isSuccess,
    shareableUrl,
    handleChange,
    handleFileSelect,
    handleToggle,
    handleSubmit,
    closeModal,
  } = useEventForm();

  return (
    <>
      <div className="container mx-auto p-4 sm:p-6 lg:p-8 bg-background-light">
        <header className="mb-8">
          <h1 className="text-3xl font-bold text-text-dark">Create New Event</h1>
          <p className="text-gray-500 mt-1">Fill out the details below to publish your event.</p>
        </header>

        <form onSubmit={handleSubmit} className="space-y-12">
          {/* Section 1: Event Details */}
          <div className="p-8 bg-white rounded-lg shadow">
            <h2 className="text-xl font-semibold text-text-dark mb-6">Event Details</h2>
            <div className="space-y-6">
              <TextInput label="Event Title" name="title" value={formData.title} onChange={handleChange} error={errors.title} placeholder="e.g., E-Cell Annual Summit" maxLength={100} required />
              <TextareaInput label="Event Description" name="description" value={formData.description} onChange={handleChange} error={errors.description} placeholder="Describe what your event is about..." required />
              <ImageUploader onFileSelect={handleFileSelect} error={errors.bannerImage} />
            </div>
          </div>

          {/* Section 2: Logistics */}
          <div className="p-8 bg-white rounded-lg shadow">
            <h2 className="text-xl font-semibold text-text-dark mb-6">Date & Logistics</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <DateTimePicker label="Registration Opens" name="registrationOpens" value={formData.registrationOpens} onChange={handleChange} error={errors.registrationOpens} required />
              <DateTimePicker label="Registration Closes" name="registrationCloses" value={formData.registrationCloses} onChange={handleChange} error={errors.registrationCloses} required />
              <DateTimePicker label="Event Starts" name="eventStarts" value={formData.eventStarts} onChange={handleChange} error={errors.eventStarts} required />
              <DateTimePicker label="Event Ends" name="eventEnds" value={formData.eventEnds} onChange={handleChange} error={errors.eventEnds} required />
              <TextInput label="Venue" name="venue" value={formData.venue} onChange={handleChange} error={errors.venue} placeholder="e.g., University Auditorium" required className="md:col-span-2" />
            </div>
          </div>

          {/* Section 3: Ticketing */}
          <div className="p-8 bg-white rounded-lg shadow">
            <h2 className="text-xl font-semibold text-text-dark mb-6">Ticketing</h2>
            <ToggleSwitch label="This is a Paid Event" enabled={formData.isPaid} onChange={handleToggle} />
            {formData.isPaid && (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
                <TextInput label="Price (₹)" name="price" type="number" value={formData.price} onChange={handleChange} error={errors.price} placeholder="e.g., 150" min="0" required />
                <TextInput label="Total Capacity" name="capacity" type="number" value={formData.capacity} onChange={handleChange} error={errors.capacity} placeholder="e.g., 200" min="0" required />
              </div>
            )}
          </div>

          {/* Action Buttons */}
          <div className="flex justify-end gap-4">
            <button type="button" className="px-6 py-2 rounded-lg bg-gray-200 text-text-dark font-semibold" disabled={isSubmitting}>
              Save as Draft
            </button>
            <button type="submit" className="px-6 py-2 rounded-lg bg-primary-purple text-white font-semibold" disabled={isSubmitting}>
              {isSubmitting ? 'Publishing...' : 'Publish Event'}
            </button>
          </div>
        </form>
      </div>
      <SuccessModal isOpen={isSuccess} onClose={closeModal} shareableUrl={shareableUrl} />
    </>
  );
};

export default CreateEventPage;
