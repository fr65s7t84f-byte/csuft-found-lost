import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { hasRole, isAuthed } from '../store/auth';

export function RequireAuth({ role }) {
  const location = useLocation();
  if (!isAuthed()) {
    return <Navigate to="/auth" replace state={{ from: location }} />;
  }
  if (role && !hasRole(role)) {
    return <Navigate to={role === 'admin' ? '/user/notices' : '/admin/dashboard'} replace />;
  }
  return <Outlet />;
}
