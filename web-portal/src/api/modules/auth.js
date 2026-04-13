import http from '../http';
import { mockLogin, mockRegister } from '../../mock/data';

const USE_MOCK = import.meta.env.VITE_USE_MOCK !== 'false';

export async function login(payload) {
  if (USE_MOCK) return mockLogin(payload);
  return http.post('/user/login', payload);
}

export async function register(payload) {
  if (USE_MOCK) return mockRegister(payload);
  return http.post('/auth/register', payload);
}
