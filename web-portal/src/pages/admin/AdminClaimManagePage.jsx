import { useEffect, useState } from 'react';
import { Button, Card, Space, Table, Tag, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchClaims, reviewClaim } from '../../api/modules/business';

function AdminClaimManagePage() {
  const [list, setList] = useState([]);

  const load = () => {
    fetchClaims().then((res) => setList(res?.data || []));
  };

  useEffect(() => {
    load();
  }, []);

  const setStatus = async (id, status) => {
    await reviewClaim(id, status);
    message.success('处理完成');
    load();
  };

  return (
    <>
      <PageHeaderCard title="认领信息管理" description="查看认领申请与匹配度，执行通过/驳回并留存审核意见。" />
      <Card className="page-card">
        <Table
          rowKey="id"
          dataSource={list}
          columns={[
            { title: '申请ID', dataIndex: 'id', width: 90 },
            { title: '物品', dataIndex: 'itemTitle', width: 180 },
            { title: '申请人', dataIndex: 'applicant', width: 120 },
            { title: '学号', dataIndex: 'studentId', width: 120 },
            { title: '匹配度', dataIndex: 'score', width: 110, render: (v) => `${v}%` },
            {
              title: '状态',
              dataIndex: 'status',
              width: 120,
              render: (v) => <Tag color={v === 'approved' ? 'green' : v === 'rejected' ? 'red' : 'gold'}>{v === 'approved' ? '已通过' : v === 'rejected' ? '已驳回' : '待审核'}</Tag>,
            },
            { title: '提交时间', dataIndex: 'submittedAt', width: 180 },
            {
              title: '操作',
              width: 160,
              render: (_, record) => (
                <Space>
                  <Button size="small" onClick={() => setStatus(record.id, 'approved')}>通过</Button>
                  <Button size="small" danger onClick={() => setStatus(record.id, 'rejected')}>驳回</Button>
                </Space>
              ),
            },
          ]}
          scroll={{ x: 980 }}
        />
      </Card>
    </>
  );
}

export default AdminClaimManagePage;
