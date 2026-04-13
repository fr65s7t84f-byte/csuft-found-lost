import { useEffect, useState } from 'react';
import { Card } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import AdminItemManageTable from '../../components/AdminItemManageTable';
import ItemFilterPanel from '../../components/ItemFilterPanel';
import { deleteItem, fetchLostItems, reviewItem, updateItem } from '../../api/modules/business';

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

function AdminLostManagePage() {
  const [filters, setFilters] = useState(initialFilters);
  const [list, setList] = useState([]);
  const [optionSource, setOptionSource] = useState({ categories: [], locations: [], tags: [] });

  const load = async (nextFilters = filters) => {
    const res = await fetchLostItems({ ...nextFilters, pageNum: 1, pageSize: 100 });
    const records = res?.data?.records || [];
    setList(records);
    setOptionSource(createOptionSource(records));
  };

  useEffect(() => {
    load(initialFilters);
  }, []);

  return (
    <>
      <PageHeaderCard title="失物信息管理" description="支持按关键词、标签、地点、业务状态、审核状态等维度展开筛选，便于审核与维护。" />
      <Card className="page-card" style={{ marginBottom: 16 }}>
        <ItemFilterPanel
          type="lost"
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
          title="失物列表"
          dataSource={list}
          onRefresh={() => load(filters)}
          onReview={(id, status) => reviewItem('lost', id, status)}
          onDelete={(id) => deleteItem('lost', id)}
          onEdit={(id, payload) => updateItem('lost', id, payload)}
        />
      </Card>
    </>
  );
}

export default AdminLostManagePage;
