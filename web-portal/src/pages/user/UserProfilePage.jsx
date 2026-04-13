import { useState } from 'react';
import { Button, Card, Col, Form, Input, Row, Select, Statistic, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { getAuth } from '../../store/auth';

function UserProfilePage() {
  const [loading, setLoading] = useState(false);
  const auth = getAuth();

  const onSave = async (values) => {
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
      message.success('个人信息已更新');
      console.log(values);
    }, 500);
  };

  return (
    <>
      <PageHeaderCard title="个人信息管理" description="维护个人资料与联系方式，查看个人参与数据。" />
      <Row gutter={[16, 16]}>
        <Col xs={24} lg={16}>
          <Card className="page-card" title="基本信息">
            <Form
              layout="vertical"
              onFinish={onSave}
              initialValues={{
                nickname: '肖强',
                studentId: '20223590',
                phone: '18173323763',
                role: auth?.role || 'user',
              }}
            >
              <Form.Item label="昵称" name="nickname" rules={[{ required: true, message: '请输入昵称' }]}>
                <Input />
              </Form.Item>
              <Form.Item label="用户角色" name="role">
                <Select
                  disabled
                  options={[
                    { label: '普通用户', value: 'user' },
                    { label: '管理员', value: 'admin' },
                  ]}
                />
              </Form.Item>
              <Form.Item label="学号/工号" name="studentId" rules={[{ required: true, message: '请输入学号/工号' }]}>
                <Input />
              </Form.Item>
              <Form.Item label="手机号" name="phone" rules={[{ required: true, message: '请输入手机号' }]}>
                <Input />
              </Form.Item>
              <Form.Item label="个性签名" name="signature">
                <Input.TextArea rows={3} placeholder="请输入个性签名" />
              </Form.Item>
              <Button loading={loading} type="primary" htmlType="submit">
                保存修改
              </Button>
            </Form>
          </Card>
        </Col>
        <Col xs={24} lg={8}>
          <Card className="page-card" title="个人数据">
            <Statistic title="累计发布" value={26} suffix="条" />
            <Statistic title="成功找回" value={18} suffix="条" style={{ marginTop: 20 }} />
            <Statistic title="信用分" value={98} style={{ marginTop: 20 }} />
          </Card>
        </Col>
      </Row>
    </>
  );
}

export default UserProfilePage;
