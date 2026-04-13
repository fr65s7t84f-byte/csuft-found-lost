import { useEffect, useState } from 'react';
import { Button, Card, Image, Space, Table, Tabs, Tag, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import ItemFilterPanel from '../../components/ItemFilterPanel';
import { fetchFoundItems, fetchLostItems } from '../../api/modules/business';
import { statusText } from '../../mock/data';

const initialFilters = {
  keyword: '',
  category: undefined,
  location: undefined,
  tag: undefined,
  status: undefined,
};

const createOptionSource = (records = []) => ({
  categories: [...new Set(records.map((item) => item.category).filter(Boolean))],
  locations: [...new Set(records.map((item) => item.location).filter(Boolean))],
  tags: [...new Set(records.flatMap((item) => item.tags || []).filter(Boolean))],
});

const buildQueryParams = (filters) => ({
  ...filters,
  pageNum: 1,
  pageSize: 100,
});

function UserLostFoundPage() {
  const [lostFilters, setLostFilters] = useState(initialFilters);
  const [foundFilters, setFoundFilters] = useState(initialFilters);
  const [lostList, setLostList] = useState([]);
  const [foundList, setFoundList] = useState([]);
  const [lostOptions, setLostOptions] = useState({ categories: [], locations: [], tags: [] });
  const [foundOptions, setFoundOptions] = useState({ categories: [], locations: [], tags: [] });

  const loadLost = async (filters = lostFilters) => {
    const res = await fetchLostItems(buildQueryParams(filters));
    const records = res?.data?.records || [];
    setLostList(records);
    setLostOptions(createOptionSource(records));
  };

  const loadFound = async (filters = foundFilters) => {
    const res = await fetchFoundItems(buildQueryParams(filters));
    const records = res?.data?.records || [];
    setFoundList(records);
    setFoundOptions(createOptionSource(records));
  };

  useEffect(() => {
    loadLost(initialFilters);
    loadFound(initialFilters);
  }, []);

  const lostStatusText = {
    pending_claim: '待寻回',
    reviewing: '审核中',
    claimed: '已认领',
    closed: '已完结',
  };

  const onLostPickup = (id) => {
    setLostList((prev) => prev.map((item) => (item.id === id ? { ...item, status: 'reviewing' } : item)));
    message.success('已标记为拾得，进入审核中');
  };

  const onFoundClaim = (id) => {
    setFoundList((prev) => prev.map((item) => (item.id === id ? { ...item, status: 'reviewing' } : item)));
    message.success('已发起认领，进入审核中');
  };

  const baseColumns = [
    { title: '标题', dataIndex: 'title', width: 180 },
    {
      title: '图片',
      dataIndex: 'imageUrl',
      width: 110,
      render: (url) => (
        <Image
          width={64}
          height={64}
          src={url}
          style={{ objectFit: 'cover', borderRadius: 8 }}
          fallback="https://dummyimage.com/64x64/e6edf5/5c7287.png&text=IMG"
          preview={false}
        />
      ),
    },
    { title: '分类', dataIndex: 'category', width: 120 },
    { title: '地点', dataIndex: 'location', width: 160 },
    { title: '匹配标签', dataIndex: 'tags', render: (tags) => tags?.map((tag) => <Tag key={tag}>{tag}</Tag>) },
  ];

  const lostColumns = [
    ...baseColumns,
    {
      title: '状态',
      dataIndex: 'status',
      width: 120,
      render: (status) => <Tag color="processing">{lostStatusText[status] || status}</Tag>,
    },
    {
      title: '操作',
      width: 180,
      render: (_, record) => (
        <Space>
          {record.status === 'pending_claim' && (
            <Button size="small" type="primary" onClick={() => onLostPickup(record.id)}>
              拾得
            </Button>
          )}
        </Space>
      ),
    },
  ];

  const foundColumns = [
    ...baseColumns,
    {
      title: '状态',
      dataIndex: 'status',
      width: 120,
      render: (status) => <Tag color="processing">{statusText[status] || status}</Tag>,
    },
    {
      title: '操作',
      width: 180,
      render: (_, record) => (
        <Space>
          {record.status === 'pending_claim' && (
            <Button size="small" type="primary" onClick={() => onFoundClaim(record.id)}>
              认领
            </Button>
          )}
        </Space>
      ),
    },
  ];

  const renderListSection = ({ type, filters, setFilters, onSearch, onReset, optionSource, columns, dataSource }) => (
    <Space direction="vertical" size={16} style={{ width: '100%' }}>
      <ItemFilterPanel
        type={type}
        value={filters}
        onChange={setFilters}
        onSearch={onSearch}
        onReset={onReset}
        optionSource={optionSource}
        keywordPlaceholder="输入关键词，可搜索标题、描述、标签"
      />
      <Table rowKey="id" columns={columns} dataSource={dataSource} pagination={{ pageSize: 5 }} scroll={{ x: 1180 }} />
    </Space>
  );

  return (
    <>
      <PageHeaderCard title="失物/招领" description="支持展开筛选条件，从标签、地点、分类、状态等维度快速检索校园失物与招领信息。" />
      <Card className="page-card">
        <Tabs
          items={[
            {
              key: 'lost',
              label: '失物列表',
              children: renderListSection({
                type: 'lost',
                filters: lostFilters,
                setFilters: setLostFilters,
                onSearch: () => loadLost(lostFilters),
                onReset: () => {
                  setLostFilters(initialFilters);
                  loadLost(initialFilters);
                },
                optionSource: lostOptions,
                columns: lostColumns,
                dataSource: lostList,
              }),
            },
            {
              key: 'found',
              label: '招领列表',
              children: renderListSection({
                type: 'found',
                filters: foundFilters,
                setFilters: setFoundFilters,
                onSearch: () => loadFound(foundFilters),
                onReset: () => {
                  setFoundFilters(initialFilters);
                  loadFound(initialFilters);
                },
                optionSource: foundOptions,
                columns: foundColumns,
                dataSource: foundList,
              }),
            },
          ]}
        />
      </Card>
    </>
  );
}

export default UserLostFoundPage;
