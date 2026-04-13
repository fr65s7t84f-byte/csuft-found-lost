import { useEffect, useState } from 'react';
import { Button, Card, Col, Form, Input, List, Modal, Row, Tag, Typography, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchAnnouncements, fetchUserNotifications } from '../../api/modules/business';

function UserAnnouncementPage() {
  const [data, setData] = useState({ todayLost: [], todayFound: [], urgent: [], important: [] });
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    Promise.all([fetchAnnouncements(), fetchUserNotifications()]).then(([announcementRes, notificationRes]) => {
      setData(announcementRes?.data || {});
      setNotifications(notificationRes?.data || []);
    });
  }, []);

  return (
    <>
      <PageHeaderCard
        title="今日公告"
        description="集中展示今日登记失物/拾物、紧急寻物、重要通知与我的站内通知。"
      />
      <Row gutter={[16, 16]}>
        <Col xs={24} lg={12}>
          <Card className="page-card" title="今日登记失物">
            <List
              dataSource={data.todayLost}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta title={item.title} description={`${item.location} · ${item.lostOrFoundTime}`} />
                  <Tag color="red">寻物</Tag>
                </List.Item>
              )}
            />
          </Card>
        </Col>
        <Col xs={24} lg={12}>
          <Card className="page-card" title="今日登记拾物">
            <List
              dataSource={data.todayFound}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta title={item.title} description={`${item.location} · ${item.lostOrFoundTime}`} />
                  <Tag color="green">招领</Tag>
                </List.Item>
              )}
            />
          </Card>
        </Col>
        <Col xs={24} lg={12}>
          <Card className="page-card" title="紧急寻物">
            <List
              dataSource={data.urgent}
              renderItem={(item) => (
                <List.Item>
                  <Typography.Text strong>{item.content}</Typography.Text>
                </List.Item>
              )}
            />
          </Card>
        </Col>
        <Col xs={24} lg={12}>
          <Card className="page-card" title="重要通知">
            <List
              dataSource={data.important}
              renderItem={(item) => (
                <List.Item>
                  <Typography.Text>{item.content}</Typography.Text>
                </List.Item>
              )}
            />
          </Card>
        </Col>
        <Col xs={24}>
          <Card className="page-card" title="我的站内通知">
            <List
              locale={{ emptyText: '暂无站内通知' }}
              dataSource={notifications}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta
                    title={<SpaceTitle title={item.title} read={item.read} />}
                    description={`${item.content}${item.createdAt ? ` · ${item.createdAt}` : ''}`}
                  />
                </List.Item>
              )}
            />
          </Card>
        </Col>
      </Row>
    </>
  );
}

function SpaceTitle({ title, read }) {
  return (
    <span>
      <Typography.Text strong>{title}</Typography.Text>
      <Tag color={read ? 'default' : 'blue'} style={{ marginLeft: 8 }}>{read ? '已读' : '未读'}</Tag>
    </span>
  );
}

export default UserAnnouncementPage;
