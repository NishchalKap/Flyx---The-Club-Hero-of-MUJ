import React, { useState, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';

interface ImageUploaderProps {
  onFileSelect: (file: File | null) => void;
  error?: string;
}

const ImageUploader: React.FC<ImageUploaderProps> = ({ onFileSelect, error }) => {
  const [preview, setPreview] = useState<string | null>(null);

  const onDrop = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles && acceptedFiles.length > 0) {
      const file = acceptedFiles[0];
      onFileSelect(file);
      setPreview(URL.createObjectURL(file));
    }
  }, [onFileSelect]);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: { 'image/jpeg': [], 'image/png': [] },
    maxSize: 5 * 1024 * 1024, // 5MB
    multiple: false,
  });

  return (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">Banner Image</label>
      <div
        {...getRootProps()}
        className={`w-full p-6 border-2 border-dashed rounded-lg text-center cursor-pointer ${
          isDragActive ? 'border-primary-purple bg-purple-50' : 'border-gray-300'
        } ${error ? 'border-red-500' : ''}`}
      >
        <input {...getInputProps()} />
        {preview ? (
          <img src={preview} alt="Banner preview" className="mx-auto max-h-48 rounded-lg" />
        ) : (
          <div className="text-gray-500">
            <p>Drag & drop an image here, or click to select one</p>
            <p className="text-sm mt-1">Recommended: 1600x900px, Max 5MB</p>
          </div>
        )}
      </div>
      {error && <p className="text-sm text-red-500 mt-1">{error}</p>}
    </div>
  );
};

export default ImageUploader;
