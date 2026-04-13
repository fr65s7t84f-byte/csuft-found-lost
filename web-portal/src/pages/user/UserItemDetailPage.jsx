import { useEffect, useState } from 'react';
import { ArrowLeftOutlined } from '@ant-design/icons';
import { Button, Card, Descriptions, Empty, Form, Image, Input, Modal, Space, Tag, Typography, message } from 'antd';
import { useNavigate, useParams } from 'react-router-dom';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchItemDetail, submitClaimApplication } from '../../api/modules/business';
import { statusText } from '../../mock/data';

const reviewText = {
  pending: '待审核',
  approved: '已通过',
  rejected: '已驳回',
};

const typeText = {
  lost: '失物',
  found: '招领',
};

function UserItemDetailPage() {
  const navigate = useNavigate();
  const { type, id } = useParams();
  const [detail, setDetail] = useState(null);
  const [claimVisible, setClaimVisible] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [form] = Form.useForm();

  useEffect(() => {
    fetchItemDetail(type, id).then((res) => {
      setDetail(res?.data || null);
    });
  }, [id, type]);

  const handleSubmitClaim = async () => {
    const values = await form.validateFields();
    setSubmitting(true);
    try {
      const res = await submitClaimApplication(type, id, values);
      if (res?.code !== 0) throw new Error(res?.message || '提交失败');
      message.success('认领申请已提交');
      setClaimVisible(false);
      form.resetFields();
    } catch (error) {
      message.error(error.message || '提交失败');
    } finally {
      setSubmitting(false);
    }
  };

  if (!detail) {
    return (
      <>
        <PageHeaderCard title="物品详情" description="查看失物或招领记录的完整信息。" />
        <Card className="page-card">
          <Empty description="未找到对应记录" />
        </Card>
      </>
    );
  }

  const canClaim = detail.reviewStatus === 'approved' && !['closed', 'claimed'].includes(detail.status);

  return (
    <>
      <PageHeaderCard title="物品详情" description="可查看记录的分类、地点、标签、联系人和审核状态等完整信息，并提交认领申请。" />
      <Card className="page-card" style={{ marginBottom: 16 }}>
        <Space style={{ marginBottom: 16 }}>
          <Button icon={<ArrowLeftOutlined />} onClick={() => navigate(-1)}>
            返回
          </Button>
          <Tag color={detail.type === 'lost' ? 'red' : 'green'}>{typeText[detail.type] || detail.type}</Tag>
          <Tag color={detail.status === 'closed' ? 'default' : detail.status === 'claimed' ? 'green' : 'processing'}>
            {statusText[detail.status] || detail.status}
          </Tag>
          <Tag color={detail.reviewStatus === 'approved' ? 'green' : detail.reviewStatus === 'rejected' ? 'red' : 'gold'}>
            {reviewText[detail.reviewStatus] || detail.reviewStatus}
          </Tag>
          <Button type="primary" disabled={!canClaim} onClick={() => setClaimVisible(true)}>
            提交认领申请
          </Button>
        </Space>

        <Space align="start" size={24} wrap>
          <Image
            width={220}
            height={220}
            src={detail.imageUrl}
            style={{ objectFit: 'cover', borderRadius: 16 }}
            fallback="https://dummyimage.com/220x220/e6edf5/5c7287.png&text=ITEM"
          />
          <div style={{ flex: 1, minWidth: 320 }}>
            <Typography.Title level={3} style={{ marginTop: 0 }}>
              {detail.title}
            </Typography.Title>
            <Descriptions column={2} bordered size="middle">
              <Descriptions.Item label="物品类型">{typeText[detail.type] || detail.type}</Descriptions.Item>
              <Descriptions.Item label="物品分类">{detail.category}</Descriptions.Item>
              <Descriptions.Item label="地点">{detail.location}</Descriptions.Item>
              <Descriptions.Item label={detail.type === 'lost' ? '丢失时间' : '拾取时间'}>{detail.lostOrFoundTime}</Descriptions.Item>
              <Descriptions.Item label="发布时间">{detail.publishedAt}</Descriptions.Item>
              <Descriptions.Item label="发布人">{detail.publisherName}</Descriptions.Item>
              <Descriptions.Item label="联系方式" span={2}>{detail.contact}</Descriptions.Item>
              <Descriptions.Item label="标签" span={2}>
                {(detail.tags || []).map((tag) => (
                  <Tag key={tag}>{tag}</Tag>
                ))}
              </Descriptions.Item>
              <Descriptions.Item label="详细描述" span={2}>{detail.description || '暂无描述'}</Descriptions.Item>
            </Descriptions>
            {!canClaim && (
              <Typography.Text type="secondary" style={{ display: 'block', marginTop: 12 }}>
                当前仅支持对“已审核通过且未关闭”的记录提交认领申请。
              </Typography.Text>
            )}
          </div>
        </Space>
      </Card>

      <Modal
        title="提交认领申请"
        open={claimVisible}
        onCancel={() => setClaimVisible(false)}
        onOk={handleSubmitClaim}
        confirmLoading={submitting}
        okText="提交申请"
        cancelText="取消"
      >
        <Form form={form} layout="vertical">
          <Form.Item label="认领说明" name="evidenceText" rules={[{ required: true, message: '请填写认领说明' }]}> 
            <Input.TextArea rows={5} placeholder="请填写能够证明物品归属的细节，如颜色、内容物、特殊标记、遗失经过等。" />
          </Form.Item>
          <Form.Item label="补充图片地址（可选）" name="evidenceImages">
            <Input placeholder="如有补充证据图片，可填写图片 URL，多张可逗号分隔" />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}

export default UserItemDetailPage;
