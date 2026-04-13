import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Card, Form, Input, Select, Tabs, Tag, Typography, message } from 'antd';
import { CompassOutlined, EnvironmentOutlined, SafetyCertificateOutlined } from '@ant-design/icons';
import { login, register } from '../../api/modules/auth';
import { setAuth } from '../../store/auth';

const featureCards = [
  {
    title: '温暖联结',
    description: '把遗失与归还连接起来，让每一次拾金不昧都被看见。',
    icon: <CompassOutlined />,
  },
  {
    title: '校园定位',
    description: '围绕图书馆、教学楼、宿舍与食堂等高频区域快速检索。',
    icon: <EnvironmentOutlined />,
  },
  {
    title: '安心认领',
    description: '支持审核流转与状态跟进，帮助用户更快找回重要物品。',
    icon: <SafetyCertificateOutlined />,
  },
];

function AuthPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const onLogin = async (values) => {
    setLoading(true);
    try {
      const res = await login(values);
      if (res?.code !== 0) throw new Error(res?.message || '登录失败');
      setAuth(res.data);
      message.success('登录成功');
      navigate(res.data.role === 'admin' ? '/admin/dashboard' : '/user/notices');
    } catch (error) {
      message.error(error.message || '登录失败');
    } finally {
      setLoading(false);
    }
  };

  const onRegister = async (values) => {
    setLoading(true);
    try {
      const res = await register(values);
      if (res?.code !== 0) throw new Error(res?.message || '注册失败');
      message.success('注册成功，请登录');
    } catch (error) {
      message.error(error.message || '注册失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <Card className="auth-shell" bodyStyle={{ padding: 0 }}>
        <div className="auth-layout">
          <div className="auth-hero">
            <div className="auth-hero-orb auth-hero-orb-one" />
            <div className="auth-hero-orb auth-hero-orb-two" />
            <Tag className="auth-hero-tag" bordered={false}>
              CAMPUS LOST & FOUND
            </Tag>
            <Typography.Title level={1} className="auth-hero-title">
              校园失物招领
            </Typography.Title>
            <Typography.Paragraph className="auth-hero-subtitle">
              让遗失被及时看见，让归还更有温度。围绕校园生活场景，打造更轻松、更高效的失物寻回体验。
            </Typography.Paragraph>

            <div className="auth-scene-card">
              <div>
                <Typography.Text className="auth-scene-label">今日重点区域</Typography.Text>
                <Typography.Title level={3} className="auth-scene-title">
                  图书馆 · 教学楼 · 宿舍园区
                </Typography.Title>
              </div>
              <div className="auth-scene-dots">
                <span />
                <span />
                <span />
              </div>
            </div>

            <div className="auth-feature-grid">
              {featureCards.map((item) => (
                <div key={item.title} className="auth-feature-card">
                  <div className="auth-feature-icon">{item.icon}</div>
                  <div>
                    <Typography.Text strong className="auth-feature-title">
                      {item.title}
                    </Typography.Text>
                    <Typography.Paragraph className="auth-feature-desc">{item.description}</Typography.Paragraph>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className="auth-form-panel">
            <Typography.Title level={3} style={{ marginTop: 0, marginBottom: 8 }}>
              欢迎回来
            </Typography.Title>
            <Typography.Paragraph style={{ color: '#6b7a90', marginBottom: 24 }}>
              登录后可查看失物、发布招领，并在管理端完成审核与跟进。
            </Typography.Paragraph>

            <Tabs
              items={[
                {
                  key: 'login',
                  label: '登录',
                  children: (
                    <Form layout="vertical" onFinish={onLogin}>
                      <Form.Item label="账号" name="account" rules={[{ required: true, message: '请输入账号' }]}>
                        <Input placeholder="学号/工号/管理员账号" />
                      </Form.Item>
                      <Form.Item label="密码" name="password" rules={[{ required: true, message: '请输入密码' }]}>
                        <Input.Password placeholder="请输入密码" />
                      </Form.Item>
                      <Form.Item label="角色" name="role" initialValue="user">
                        <Select options={[{ label: '用户端', value: 'user' }, { label: '管理端', value: 'admin' }]} />
                      </Form.Item>
                      <Button loading={loading} type="primary" htmlType="submit" block>
                        登录
                      </Button>
                    </Form>
                  ),
                },
                {
                  key: 'register',
                  label: '注册',
                  children: (
                    <Form layout="vertical" onFinish={onRegister}>
                      <Form.Item label="用户名" name="username" rules={[{ required: true, message: '请输入用户名' }]}>
                        <Input placeholder="请输入用户名" />
                      </Form.Item>
                      <Form.Item label="手机号" name="phone" rules={[{ required: true, message: '请输入手机号' }]}>
                        <Input placeholder="请输入手机号" />
                      </Form.Item>
                      <Form.Item label="密码" name="password" rules={[{ required: true, message: '请输入密码' }]}>
                        <Input.Password placeholder="请输入密码" />
                      </Form.Item>
                      <Button loading={loading} type="primary" htmlType="submit" block>
                        注册
                      </Button>
                    </Form>
                  ),
                },
              ]}
            />
          </div>
        </div>
      </Card>
    </div>
  );
}

export default AuthPage;
