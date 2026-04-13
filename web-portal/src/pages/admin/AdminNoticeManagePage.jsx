import { useEffect, useState } from 'react';
import { Button, Card, Form, Input, Modal, Space, Table, Tag, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { createNotice, fetchNotices, updateNotice } from '../../api/modules/business';

function AdminNoticeManagePage() {
  const [list, setList] = useState([]);
  const [open, setOpen] = useState(false);
  const [current, setCurrent] = useState(null);
  const [form] = Form.useForm();

  const load = () => {
    fetchNotices().then((res) => setList(res?.data || []));
  };

  useEffect(() => {
    load();
  }, []);

  const openCreate = () => {
    setCurrent(null);
    form.resetFields();
    setOpen(true);
  };

  const openEdit = (record) => {
    setCurrent(record);
    form.setFieldsValue(record);
    setOpen(true);
  };

  const onSubmit = async () => {
    const values = await form.validateFields();
    if (current) {
      await updateNotice(current.id, values);
      message.success('公告已更新');
    } else {
      await createNotice(values);
      message.success('公告已创建');
    }
    setOpen(false);
    load();
  };

  return (
    <>
      <PageHeaderCard title="公告管理" description="维护系统公告、重要通知和应急寻物信息。" extra={<Button type="primary" onClick={openCreate}>新建公告</Button>} />
      <Card className="page-card">
        <Table
          rowKey="id"
          dataSource={list}
          columns={[
            { title: 'ID', dataIndex: 'id', width: 90 },
            { title: '标题', dataIndex: 'title', width: 220 },
            { title: '内容', dataIndex: 'content' },
            { title: '状态', dataIndex: 'status', width: 100, render: (v) => <Tag color={v === 'published' ? 'green' : 'gold'}>{v === 'published' ? '已发布' : '草稿'}</Tag> },
            { title: '创建时间', dataIndex: 'createdAt', width: 180 },
            {
              title: '操作',
              width: 150,
              render: (_, record) => (
                <Space>
                  <Button size="small" onClick={() => openEdit(record)}>编辑</Button>
                  <Button size="small" type="link" onClick={() => updateNotice(record.id, { status: record.status === 'published' ? 'draft' : 'published' }).then(load)}>
                    {record.status === 'published' ? '下线' : '发布'}
                  </Button>
                </Space>
              ),
            },
          ]}
          scroll={{ x: 900 }}
        />
      </Card>

      <Modal title={current ? '编辑公告' : '新建公告'} open={open} onOk={onSubmit} onCancel={() => setOpen(false)}>
        <Form form={form} layout="vertical" initialValues={{ status: 'draft' }}>
          <Form.Item label="标题" name="title" rules={[{ required: true, message: '请输入标题' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="内容" name="content" rules={[{ required: true, message: '请输入内容' }]}>
            <Input.TextArea rows={4} />
          </Form.Item>
          <Form.Item label="状态" name="status" rules={[{ required: true, message: '请选择状态' }]}>
            <Input placeholder="draft / published" />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}

export default AdminNoticeManagePage;
