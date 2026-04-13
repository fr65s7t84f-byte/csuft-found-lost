const AUTH_KEY = 'lf_auth';

export function getAuth() {
  const raw = localStorage.getItem(AUTH_KEY);
  if (!raw) return null;
  try {
    return JSON.parse(raw);
  } catch {
    return null;
  }
}

export function setAuth(payload) {
  localStorage.setItem(AUTH_KEY, JSON.stringify(payload));
}

export function clearAuth() {
  localStorage.removeItem(AUTH_KEY);
}

export function isAuthed() {
  return Boolean(getAuth()?.token);
}

export function hasRole(role) {
  return getAuth()?.role === role;
}
