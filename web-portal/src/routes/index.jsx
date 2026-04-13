import {
  AppstoreOutlined,
  BellOutlined,
  DashboardOutlined,
  FileProtectOutlined,
  FileSearchOutlined,
  FireOutlined,
  FormOutlined,
  HeatMapOutlined,
  NotificationOutlined,
  TeamOutlined,
  UnorderedListOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { Navigate } from 'react-router-dom';
import { RequireAuth } from './RequireAuth';
import AppShell from '../layouts/AppShell';
import AuthPage from '../pages/auth/AuthPage';
import UserAnnouncementPage from '../pages/user/UserAnnouncementPage';
import UserProfilePage from '../pages/user/UserProfilePage';
import UserLostFoundPage from '../pages/user/UserLostFoundPage';
import UserPublishPage from '../pages/user/UserPublishPage';
import UserPublishManagePage from '../pages/user/UserPublishManagePage';
import UserItemDetailPage from '../pages/user/UserItemDetailPage';
import UserLeaderboardPage from '../pages/user/UserLeaderboardPage';
import UserCampusHeatmapPage from '../pages/user/UserCampusHeatmapPage';
import AdminDashboardPage from '../pages/admin/AdminDashboardPage';
import AdminLostManagePage from '../pages/admin/AdminLostManagePage';
import AdminFoundManagePage from '../pages/admin/AdminFoundManagePage';
import AdminClaimManagePage from '../pages/admin/AdminClaimManagePage';
import AdminUserManagePage from '../pages/admin/AdminUserManagePage';
import AdminNoticeManagePage from '../pages/admin/AdminNoticeManagePage';

export const userMenus = [
  { key: '/user/notices', icon: <BellOutlined />, label: '今日公告' },
  { key: '/user/profile', icon: <UserOutlined />, label: '个人信息管理' },
  { key: '/user/lost-found', icon: <AppstoreOutlined />, label: '失物/招领' },
  { key: '/user/leaderboard', icon: <FireOutlined />, label: '积分榜' },
  { key: '/user/heatmap', icon: <HeatMapOutlined />, label: '校园热点图' },
  {
    key: 'user-publish',
    icon: <FormOutlined />,
    label: '发布',
    children: [
      { key: '/user/publish/manage', icon: <UnorderedListOutlined />, label: '发布管理' },
      { key: '/user/publish/create', icon: <FormOutlined />, label: '发布' },
    ],
  },
];

export const adminMenus = [
  { key: '/admin/dashboard', icon: <DashboardOutlined />, label: '数据看板' },
  { key: '/admin/lost-items', icon: <FileSearchOutlined />, label: '失物信息管理' },
  { key: '/admin/found-items', icon: <FileProtectOutlined />, label: '招领信息管理' },
  { key: '/admin/claims', icon: <NotificationOutlined />, label: '认领信息管理' },
  { key: '/admin/users', icon: <TeamOutlined />, label: '用户信息管理' },
  { key: '/admin/notices', icon: <BellOutlined />, label: '公告管理' },
];

export const appRoutes = [
  { path: '/auth', element: <AuthPage /> },
  {
    element: <RequireAuth role="user" />,
    children: [
      {
        path: '/user',
        element: <AppShell title="用户端" menus={userMenus} />,
        children: [
          { index: true, element: <Navigate to="/user/notices" replace /> },
          { path: 'notices', element: <UserAnnouncementPage /> },
          { path: 'profile', element: <UserProfilePage /> },
          { path: 'lost-found', element: <UserLostFoundPage /> },
          { path: 'leaderboard', element: <UserLeaderboardPage /> },
          { path: 'heatmap', element: <UserCampusHeatmapPage /> },
          { path: 'publish', element: <Navigate to="/user/publish/create" replace /> },
          { path: 'publish/manage', element: <UserPublishManagePage /> },
          { path: 'publish/create', element: <UserPublishPage /> },
          { path: 'items/:type/:id', element: <UserItemDetailPage /> },
        ],
      },
    ],
  },
  {
    element: <RequireAuth role="admin" />,
    children: [
      {
        path: '/admin',
        element: <AppShell title="管理端" menus={adminMenus} />,
        children: [
          { index: true, element: <Navigate to="/admin/dashboard" replace /> },
          { path: 'dashboard', element: <AdminDashboardPage /> },
          { path: 'lost-items', element: <AdminLostManagePage /> },
          { path: 'found-items', element: <AdminFoundManagePage /> },
          { path: 'claims', element: <AdminClaimManagePage /> },
          { path: 'users', element: <AdminUserManagePage /> },
          { path: 'notices', element: <AdminNoticeManagePage /> },
        ],
      },
    ],
  },
];
