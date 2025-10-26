import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:3000/api',
});

// Function to get the auth token from the URL
const getTokenFromUrl = () => {
  const params = new URLSearchParams(window.location.search);
  return params.get('token');
};

// Interceptor to add the auth token to every request
apiClient.interceptors.request.use(
  (config) => {
    const token = getTokenFromUrl();
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
