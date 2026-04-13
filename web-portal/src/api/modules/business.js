import http from '../http';
import {
  mockAnnouncements,
  mockClaims,
  mockCreateNotice,
  mockDashboard,
  mockDeleteItem,
  mockFoundItems,
  mockCampusInsights,
  mockItemDetail,
  mockItemMatches,
  mockLostItems,
  mockMyPublishedItems,
  mockNotices,
  mockPublishItem,
  mockReviewItem,
  mockSubmitClaimApplication,
  mockUpdateClaim,
  mockUpdateItem,
  mockUpdateNotice,
  mockUpdateUser,
  mockUserNotifications,
  mockUsers,
  mockWithdrawItem,
} from '../../mock/data';

const USE_MOCK = import.meta.env.VITE_USE_MOCK !== 'false';

export async function fetchLostItems(params = {}) {
  if (USE_MOCK) return mockLostItems(params);
  return http.get('/lost/list', { params });
}

export async function fetchFoundItems(params = {}) {
  if (USE_MOCK) return mockFoundItems(params);
  return http.get('/found/list', { params });
}

export async function publishLost(payload) {
  if (USE_MOCK) return mockPublishItem('lost', payload);
  return http.post('/lost/publish', payload);
}

export async function publishFound(payload) {
  if (USE_MOCK) return mockPublishItem('found', payload);
  return http.post('/found/publish', payload);
}

export async function fetchMyPublishedItems(params = {}) {
  if (USE_MOCK) return mockMyPublishedItems(params);
  return http.get('/user/publish/list', { params });
}

export async function fetchItemDetail(type, id) {
  if (USE_MOCK) return mockItemDetail(type, id);
  return http.get(`/user/items/${type}/${id}`);
}

export async function fetchItemMatches(type, id) {
  if (USE_MOCK) return mockItemMatches(type, id);
  return http.get(`/user/items/${type}/${id}/matches`);
}

export async function submitClaimApplication(type, id, payload) {
  if (USE_MOCK) return mockSubmitClaimApplication(type, id, payload);
  return http.post(`/user/items/${type}/${id}/claim`, payload);
}

export async function fetchUserNotifications() {
  if (USE_MOCK) return mockUserNotifications();
  return http.get('/user/notifications');
}

export async function withdrawPublishedItem(type, id) {
  if (USE_MOCK) return mockWithdrawItem(type, id);
  return http.put(`/user/publish/${type}/${id}/withdraw`);
}

export async function fetchAnnouncements() {
  if (USE_MOCK) return mockAnnouncements();
  return http.get('/announcement/today');
}

export async function fetchCampusInsights() {
  if (USE_MOCK) return mockCampusInsights();
  return http.get('/statistics/campus-insights');
}

export async function fetchDashboard() {
  if (USE_MOCK) return mockDashboard();
  return http.get('/admin/dashboard/overview');
}

export async function fetchClaims() {
  if (USE_MOCK) return mockClaims();
  return http.get('/admin/claims');
}

export async function reviewClaim(id, status) {
  if (USE_MOCK) return mockUpdateClaim(id, status);
  return http.put(`/admin/claims/${id}/review`, { status });
}

export async function fetchUsers() {
  if (USE_MOCK) return mockUsers();
  return http.get('/admin/users');
}

export async function updateUserStatus(id, status) {
  if (USE_MOCK) return mockUpdateUser(id, { status });
  return http.put(`/admin/users/${id}`, { status });
}

export async function fetchNotices() {
  if (USE_MOCK) return mockNotices();
  return http.get('/admin/notices');
}

export async function createNotice(payload) {
  if (USE_MOCK) return mockCreateNotice(payload);
  return http.post('/admin/notices', payload);
}

export async function updateNotice(id, payload) {
  if (USE_MOCK) return mockUpdateNotice(id, payload);
  return http.put(`/admin/notices/${id}`, payload);
}

export async function updateItem(type, id, payload) {
  if (USE_MOCK) return mockUpdateItem(type, id, payload);
  return http.put(`/${type}/update/${id}`, payload);
}

export async function deleteItem(type, id) {
  if (USE_MOCK) return mockDeleteItem(type, id);
  return http.delete(`/${type}/delete/${id}`);
}

export async function reviewItem(type, id, reviewStatus) {
  if (USE_MOCK) return mockReviewItem(type, id, reviewStatus);
  return http.put(`/admin/${type}-items/${id}/review`, { reviewStatus });
}
