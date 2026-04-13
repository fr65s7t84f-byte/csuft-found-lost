import { Card, Typography, Space } from 'antd';

function PageHeaderCard({ title, description, extra }) {
  return (
    <Card className="page-card hero-box" bodyStyle={{ padding: 16 }}>
      <Space direction="vertical" size={2} style={{ width: '100%' }}>
        <Typography.Title className="section-title" level={4}>
          {title}
        </Typography.Title>
        <Typography.Text type="secondary">{description}</Typography.Text>
        {extra}
      </Space>
    </Card>
  );
}

export default PageHeaderCard;
