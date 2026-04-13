import { useEffect, useState } from 'react';
import { Card } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import AdminItemManageTable from '../../components/AdminItemManageTable';
import ItemFilterPanel from '../../components/ItemFilterPanel';
import { deleteItem, fetchFoundItems, reviewItem, updateItem } from '../../api/modules/business';

const initialFilters = {
  keyword: '',
  category: undefined,
  location: undefined,
  tag: undefined,
  status: undefined,
  reviewStatus: undefined,
};

const createOptionSource = (records = []) => ({
  categories: [...new Set(records.map((item) => item.category).filter(Boolean))],
  locations: [...new Set(records.map((item) => item.location).filter(Boolean))],
  tags: [...new Set(records.flatMap((item) => item.tags || []).filter(Boolean))],
});

function AdminFoundManagePage() {
  const [filters, setFilters] = useState(initialFilters);
  const [list, setList] = useState([]);
  const [optionSource, setOptionSource] = useState({ categories: [], locations: [], tags: [] });

  const load = async (nextFilters = filters) => {
    const res = await fetchFoundItems({ ...nextFilters, pageNum: 1, pageSize: 100 });
    const records = res?.data?.records || [];
    setList(records);
    setOptionSource(createOptionSource(records));
  };

  useEffect(() => {
    load(initialFilters);
  }, []);

  return (
    <>
      <PageHeaderCard title="招领信息管理" description="支持按关键词、标签、地点、业务状态、审核状态等维度展开筛选，提升招领审核效率。" />
      <Card className="page-card" style={{ marginBottom: 16 }}>
        <ItemFilterPanel
          type="found"
          showReviewStatus
          value={filters}
          onChange={setFilters}
          onSearch={() => load(filters)}
          onReset={() => {
            setFilters(initialFilters);
            load(initialFilters);
          }}
          optionSource={optionSource}
          keywordPlaceholder="搜索标题、描述、标签"
        />
      </Card>
      <Card className="page-card">
        <AdminItemManageTable
          title="招领列表"
          dataSource={list}
          onRefresh={() => load(filters)}
          onReview={(id, status) => reviewItem('found', id, status)}
          onDelete={(id) => deleteItem('found', id)}
          onEdit={(id, payload) => updateItem('found', id, payload)}
        />
      </Card>
    </>
  );
}

export default AdminFoundManagePage;
