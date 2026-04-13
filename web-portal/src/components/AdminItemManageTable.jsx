import { useState } from 'react';
import { Button, Form, Input, Modal, Popconfirm, Space, Table, Tag, message } from 'antd';
import { statusText } from '../mock/data';

function AdminItemManageTable({ title, dataSource, onRefresh, onReview, onDelete, onEdit }) {
  const [open, setOpen] = useState(false);
  const [current, setCurrent] = useState(null);
  const [form] = Form.useForm();

  const openEdit = (record) => {
    setCurrent(record);
    form.setFieldsValue({
      title: record.title,
      category: record.category,
      location: record.location,
      contact: record.contact,
      description: record.description,
    });
    setOpen(true);
  };

  const submitEdit = async () => {
    try {
      const values = await form.validateFields();
      await onEdit(current.id, values);
      message.success('编辑成功');
      setOpen(false);
      onRefresh();
    } catch (e) {
      if (e?.message) message.error(e.message);
    }
  };

  const columns = [
    { title: 'ID', dataIndex: 'id', width: 80 },
    { title: '标题', dataIndex: 'title', width: 180 },
    { title: '分类', dataIndex: 'category', width: 120 },
    { title: '地点', dataIndex: 'location', width: 140 },
    { title: '标签', dataIndex: 'tags', render: (tags) => tags?.map((t) => <Tag key={t}>{t}</Tag>) },
    {
      title: '审核状态',
      dataIndex: 'reviewStatus',
      width: 120,
      render: (s) => (
        <Tag color={s === 'approved' ? 'green' : s === 'rejected' ? 'red' : 'gold'}>
          {s === 'approved' ? '已通过' : s === 'rejected' ? '已驳回' : '待审核'}
        </Tag>
      ),
    },
    {
      title: '业务状态',
      dataIndex: 'status',
      width: 120,
      render: (v) => <Tag color="processing">{statusText[v] || v}</Tag>,
    },
    {
      title: '操作',
      fixed: 'right',
      width: 300,
      render: (_, record) => (
        <Space>
          <Button size="small" onClick={() => onReview(record.id, 'approved')}>
            审核通过
          </Button>
          <Button size="small" danger onClick={() => onReview(record.id, 'rejected')}>
            驳回
          </Button>
          <Button size="small" type="link" onClick={() => openEdit(record)}>
            编辑
          </Button>
          <Popconfirm title="确认删除该记录吗？" onConfirm={async () => { await onDelete(record.id); message.success('已删除'); onRefresh(); }}>
            <Button size="small" type="link" danger>
              删除
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Table
        rowKey="id"
        size="middle"
        columns={columns}
        dataSource={dataSource}
        scroll={{ x: 1200 }}
        title={() => title}
      />
      <Modal title="编辑记录" open={open} onOk={submitEdit} onCancel={() => setOpen(false)}>
        <Form layout="vertical" form={form}>
          <Form.Item label="标题" name="title" rules={[{ required: true, message: '请输入标题' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="分类" name="category" rules={[{ required: true, message: '请输入分类' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="地点" name="location" rules={[{ required: true, message: '请输入地点' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="联系方式" name="contact">
            <Input />
          </Form.Item>
          <Form.Item label="描述" name="description">
            <Input.TextArea rows={3} />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}

export default AdminItemManageTable;
