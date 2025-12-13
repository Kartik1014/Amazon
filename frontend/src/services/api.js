import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const authService = {
  register: async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
  },
  login: async (credentials) => {
    const response = await api.post('/auth/login', credentials);
    return response.data;
  },
};

export const sweetService = {
  getAllSweets: async () => {
    const response = await api.get('/sweets');
    return response.data;
  },
  getSweetById: async (id) => {
    const response = await api.get(`/sweets/${id}`);
    return response.data;
  },
  createSweet: async (sweet) => {
    const response = await api.post('/sweets', sweet);
    return response.data;
  },
  updateSweet: async (id, sweet) => {
    const response = await api.put(`/sweets/${id}`, sweet);
    return response.data;
  },
  deleteSweet: async (id) => {
    const response = await api.delete(`/sweets/${id}`);
    return response.data;
  },
  searchSweets: async (params) => {
    const response = await api.get('/sweets/search', { params });
    return response.data;
  },
  purchaseSweet: async (id, quantity) => {
    const response = await api.post(`/sweets/${id}/purchase`, { quantity });
    return response.data;
  },
  restockSweet: async (id, quantity) => {
    const response = await api.post(`/sweets/${id}/restock`, { quantity });
    return response.data;
  },
};

export default api;
