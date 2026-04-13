import { useEffect, useState } from 'react';
import { Button, Card, Modal, Space, Table, Tag, Typography, message } from 'antd';
import { RobotOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchItemMatches, fetchMyPublishedItems, withdrawPublishedItem } from '../../api/modules/business';
import { statusText } from '../../mock/data';

const reviewText = {
  pending: '待审核',
  approved: '已通过',
  rejected: '已驳回',
};

function UserPublishManagePage() {
  const navigate = useNavigate();
  const [lostList, setLostList] = useState([]);
  const [foundList, setFoundList] = useState([]);
  const [matchOpen, setMatchOpen] = useState(false);
  const [matchingItem, setMatchingItem] = useState(null);
  const [matchLoading, setMatchLoading] = useState(false);
  const [matchRecords, setMatchRecords] = useState([]);

  const loadByType = async (type) => {
    const res = await fetchMyPublishedItems({ type, pageNum: 1, pageSize: 100 });
    const records = res?.data?.records || [];
    if (type === 'lost') {
      setLostList(records);
    } else {
      setFoundList(records);
    }
  };

  const loadAll = async () => {
    await Promise.all([loadByType('lost'), loadByType('found')]);
  };

  useEffect(() => {
    loadAll();
  }, []);

  const handleWithdraw = async (record) => {
    const res = await withdrawPublishedItem(record.type, record.id);
    if (res?.code !== 0) {
      message.error(res?.message || '撤回失败');
      return;
    }
    message.success('撤回发布成功');
    loadByType(record.type);
  };

  const openMatchModal = async (record) => {
    setMatchingItem(record);
    setMatchOpen(true);
    setMatchLoading(true);
    try {
      const res = await fetchItemMatches(record.type, record.id);
      if (res?.code !== 0) throw new Error(res?.message || '智能匹配失败');
      setMatchRecords(res?.data || []);
    } catch (error) {
      message.error(error.message || '智能匹配失败');
      setMatchRecords([]);
    } finally {
      setMatchLoading(false);
    }
  };

  const baseColumns = [
    { title: '标题', dataIndex: 'title', width: 180 },
    { title: '分类', dataIndex: 'category', width: 120 },
    { title: '地点', dataIndex: 'location', width: 140 },
    {
      title: '标签',
      dataIndex: 'tags',
      render: (tags) => tags?.map((tag) => <Tag key={tag}>{tag}</Tag>),
    },
    { title: '发布时间', dataIndex: 'publishedAt', width: 160 },
    {
      title: '审核状态',
      dataIndex: 'reviewStatus',
      width: 120,
      render: (value) => <Tag color={value === 'approved' ? 'green' : value === 'rejected' ? 'red' : 'gold'}>{reviewText[value] || value}</Tag>,
    },
    {
      title: '业务状态',
      dataIndex: 'status',
      width: 120,
      render: (value) => <Tag color={value === 'closed' ? 'default' : 'processing'}>{statusText[value] || value}</Tag>,
    },
    {
      title: '操作',
      fixed: 'right',
      width: 250,
      render: (_, record) => (
        <Space>
          <Button size="small" onClick={() => navigate(`/user/items/${record.type}/${record.id}`)}>
            详情
          </Button>
          <Button size="small" icon={<RobotOutlined />} onClick={() => openMatchModal(record)}>
            智能匹配
          </Button>
          <Button size="small" danger disabled={record.status === 'closed' || record.status === 'claimed'} onClick={() => handleWithdraw(record)}>
            撤回发布
          </Button>
        </Space>
      ),
    },
  ];

  const matchColumns = [
    { title: '标题', dataIndex: 'title', width: 160 },
    { title: '分类', dataIndex: 'category', width: 120 },
    { title: '地点', dataIndex: 'location', width: 140 },
    {
      title: '匹配度',
      dataIndex: 'score',
      width: 100,
      render: (value) => <Tag color={value >= 85 ? 'green' : value >= 70 ? 'processing' : 'gold'}>{value}%</Tag>,
    },
    {
      title: '匹配依据',
      dataIndex: 'matchReasons',
      render: (reasons) => <Typography.Text>{(reasons || []).join('；')}</Typography.Text>,
    },
    {
      title: '操作',
      width: 100,
      render: (_, record) => (
        <Button
          size="small"
          type="link"
          onClick={() => {
            setMatchOpen(false);
            navigate(`/user/items/${record.type}/${record.id}`);
          }}
        >
          查看详情
        </Button>
      ),
    },
  ];

  return (
    <>
      <PageHeaderCard title="发布管理" description="集中管理自己发布的失物与招领信息，可撤回发布并查看智能匹配 Top 5 候选结果。" />
      <Card className="page-card" title="我发布的失物" style={{ marginBottom: 16 }}>
        <Table rowKey="id" columns={baseColumns} dataSource={lostList} pagination={{ pageSize: 5 }} scroll={{ x: 1280 }} />
      </Card>
      <Card className="page-card" title="我发布的招领">
        <Table rowKey="id" columns={baseColumns} dataSource={foundList} pagination={{ pageSize: 5 }} scroll={{ x: 1280 }} />
      </Card>

      <Modal
        title={matchingItem ? `智能匹配 Top 5 · ${matchingItem.title}` : '智能匹配 Top 5'}
        open={matchOpen}
        onCancel={() => setMatchOpen(false)}
        footer={null}
        width={960}
      >
        <Table rowKey="id" loading={matchLoading} columns={matchColumns} dataSource={matchRecords} pagination={false} scroll={{ x: 900 }} />
      </Modal>
    </>
  );
}

export default UserPublishManagePage;
