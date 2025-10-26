import React from 'react';

interface DateTimePickerProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  error?: string;
}

const DateTimePicker: React.FC<DateTimePickerProps> = ({ label, name, error, ...props }) => {
  return (
    <div>
      <label htmlFor={name} className="block text-sm font-medium text-gray-700 mb-1">
        {label}
      </label>
      <input
        type="datetime-local"
        id={name}
        name={name}
        className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 ${
          error ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-primary-purple'
        }`}
        {...props}
      />
      {error && <p className="text-sm text-red-500 mt-1">{error}</p>}
    </div>
  );
};

export default DateTimePicker;
