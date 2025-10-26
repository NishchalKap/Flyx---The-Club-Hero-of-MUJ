import React, { useState } from 'react';

interface SuccessModalProps {
  isOpen: boolean;
  onClose: () => void;
  shareableUrl: string;
}

const SuccessModal: React.FC<SuccessModalProps> = ({ isOpen, onClose, shareableUrl }) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(shareableUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000); // Reset after 2 seconds
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white p-8 rounded-lg shadow-xl text-center max-w-sm w-full">
        <div className="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-green-100">
          <svg className="h-6 w-6 text-green-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7" />
          </svg>
        </div>
        <h3 className="text-lg leading-6 font-medium text-gray-900 mt-4">Success!</h3>
        <p className="mt-2 text-sm text-gray-500">Your event is now live.</p>

        <div className="mt-4 p-2 bg-gray-100 rounded-lg text-left">
          <label className="text-xs text-gray-500">Shareable Link</label>
          <input
            type="text"
            readOnly
            value={shareableUrl}
            className="w-full bg-transparent text-sm text-gray-800 outline-none"
          />
        </div>

        <div className="mt-6 flex gap-4">
          <button
            onClick={handleCopy}
            className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-primary-purple text-base font-medium text-white hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-purple"
          >
            {copied ? 'Copied!' : 'Copy Link'}
          </button>
          <button
            onClick={onClose}
            type="button"
            className="w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default SuccessModal;
