import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:3000/api', // The backend API URL
  headers: {
    'Content-Type': 'application/json',
  },
});

// Function to get a mock token for a club admin
const getMockAdminToken = () => {
  // In a real app, this would come from an auth context or secure storage
  const mockPayload = { id: 1, clubId: 123, role: 'admin' };
  // This is a simplified, insecure way to create a JWT-like string for the mock backend.
  // We're just encoding the payload, not signing it.
  return `bearer.${btoa(JSON.stringify(mockPayload))}.signature`;
};

// Interceptor to add the auth token to every request
apiClient.interceptors.request.use(
  (config) => {
    const token = getMockAdminToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;
