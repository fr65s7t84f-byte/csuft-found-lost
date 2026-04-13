import { useMemo } from 'react';
import { Layout, Menu, Typography, Button, Space, Avatar } from 'antd';
import { LogoutOutlined } from '@ant-design/icons';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { clearAuth, getAuth } from '../store/auth';

const { Sider, Header, Content } = Layout;

function AppShell({ title, menus }) {
  const navigate = useNavigate();
  const location = useLocation();
  const auth = getAuth();

  const selectedKeys = useMemo(() => [location.pathname], [location.pathname]);
  const openKeys = useMemo(
    () => menus.filter((item) => item.children?.some((child) => child.key === location.pathname)).map((item) => item.key),
    [location.pathname, menus],
  );

  return (
    <Layout style={{ minHeight: '100vh', background: 'var(--bg-main)' }}>
      <Sider width={252} theme="light" className="app-sider">
        <div className="brand-wrap">
          <div className="brand-dot" />
          <Typography.Title level={4} style={{ margin: 0 }}>
            校园失物招领
          </Typography.Title>
        </div>
        <Typography.Text type="secondary" style={{ margin: '0 18px 12px', display: 'block' }}>
          {title}
        </Typography.Text>
        <Menu
          mode="inline"
          selectedKeys={selectedKeys}
          defaultOpenKeys={openKeys}
          items={menus}
          onClick={(e) => navigate(e.key)}
          style={{ borderInlineEnd: 'none', paddingInline: 10 }}
        />
      </Sider>
      <Layout>
        <Header className="app-header">
          <Typography.Title level={4} style={{ margin: 0, color: '#fff' }}>
            智能标签输入 · 精准匹配 · 高效管理
          </Typography.Title>
          <Space>
            <Avatar src={auth?.avatarUrl}>{(auth?.name || 'U').slice(0, 1)}</Avatar>
            <Typography.Text style={{ color: '#fff' }}>{auth?.name || '未登录用户'}</Typography.Text>
            <Button
              icon={<LogoutOutlined />}
              onClick={() => {
                clearAuth();
                navigate('/auth');
              }}
            >
              退出
            </Button>
          </Space>
        </Header>
        <Content style={{ padding: 20 }}>
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  );
}

export default AppShell;
