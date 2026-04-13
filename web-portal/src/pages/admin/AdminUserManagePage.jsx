import { useEffect, useState } from 'react';
import { Button, Card, Select, Space, Table, Tag, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchUsers, updateUserStatus } from '../../api/modules/business';

function AdminUserManagePage() {
  const [list, setList] = useState([]);

  const load = () => {
    fetchUsers().then((res) => setList(res?.data || []));
  };

  useEffect(() => {
    load();
  }, []);

  const onStatusChange = async (id, status) => {
    await updateUserStatus(id, status);
    message.success('用户状态已更新');
    load();
  };

  return (
    <>
      <PageHeaderCard title="用户信息管理" description="支持用户查询、状态冻结/解冻、角色识别与账号治理。" />
      <Card className="page-card">
        <Table
          rowKey="id"
          dataSource={list}
          columns={[
            { title: 'ID', dataIndex: 'id', width: 80 },
            { title: '昵称', dataIndex: 'nickname', width: 120 },
            { title: '学号/工号', dataIndex: 'studentId', width: 140 },
            { title: '手机号', dataIndex: 'phone', width: 140 },
            { title: '角色', dataIndex: 'role', width: 100, render: (v) => <Tag color={v === 'admin' ? 'purple' : 'blue'}>{v}</Tag> },
            { title: '状态', dataIndex: 'status', width: 100, render: (v) => <Tag color={v === 'active' ? 'green' : 'red'}>{v === 'active' ? '正常' : '冻结'}</Tag> },
            {
              title: '操作',
              width: 180,
              render: (_, record) => (
                <Space>
                  <Select
                    size="small"
                    value={record.status}
                    options={[
                      { label: '正常', value: 'active' },
                      { label: '冻结', value: 'frozen' },
                    ]}
                    style={{ width: 100 }}
                    onChange={(v) => onStatusChange(record.id, v)}
                  />
                  <Button size="small">详情</Button>
                </Space>
              ),
            },
          ]}
          scroll={{ x: 900 }}
        />
      </Card>
    </>
  );
}

export default AdminUserManagePage;
