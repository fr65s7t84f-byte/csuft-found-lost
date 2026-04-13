import axios from 'axios';
import { getAuth } from '../store/auth';

const http = axios.create({
  baseURL: '/api',
  timeout: 12000,
});

http.interceptors.request.use((config) => {
  const token = getAuth()?.token;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

http.interceptors.response.use(
  (res) => res.data,
  (err) => Promise.reject(err?.response?.data || err),
);

export default http;
