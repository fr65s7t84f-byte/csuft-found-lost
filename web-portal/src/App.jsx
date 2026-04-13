import { Navigate, useRoutes } from 'react-router-dom';
import { appRoutes } from './routes';

function App() {
  const element = useRoutes([
    ...appRoutes,
    { path: '/', element: <Navigate to="/auth" replace /> },
    { path: '*', element: <Navigate to="/auth" replace /> },
  ]);

  return element;
}

export default App;
