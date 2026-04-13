import { useState } from 'react';
import { Button, Card, Form, Input, Select, Tabs, message } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import TagInputWithAi from '../../components/TagInputWithAi';
import { publishFound, publishLost } from '../../api/modules/business';

function PublishForm({ type }) {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const api = type === 'lost' ? publishLost : publishFound;
      const res = await api(values);
      if (res?.code !== 0) throw new Error(res?.message || '提交失败');
      message.success(type === 'lost' ? '失物发布成功' : '招领发布成功');
      form.resetFields();
    } catch (e) {
      message.error(e.message || '提交失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card className="page-card" bodyStyle={{ maxWidth: 720 }}>
      <Form layout="vertical" form={form} onFinish={onFinish}>
        <Form.Item label="物品名称" name="title" rules={[{ required: true, message: '请输入物品名称' }]}>
          <Input placeholder="如：蓝色帆布书包" />
        </Form.Item>
        <Form.Item label="物品分类" name="category" rules={[{ required: true, message: '请选择分类' }]}>
          <Select
            options={[
              { label: '书包', value: '书包' },
              { label: '电子产品', value: '电子产品' },
              { label: '证件', value: '证件' },
              { label: '眼镜', value: '眼镜' },
              { label: '其他', value: '其他' },
            ]}
          />
        </Form.Item>
        <Form.Item label={type === 'lost' ? '丢失地点' : '拾取地点'} name="location" rules={[{ required: true, message: '请输入地点' }]}>
          <Input placeholder="如：图书馆三楼" />
        </Form.Item>
        <Form.Item label="联系方式" name="contact" rules={[{ required: true, message: '请输入联系方式' }]}>
          <Input placeholder="手机号 / 微信号" />
        </Form.Item>
        <Form.Item label="详细描述" name="description">
          <Input.TextArea rows={3} placeholder="可补充品牌、材质、特殊标记等" />
        </Form.Item>
        <Form.Item label="特征标签（支持手动和图片智能识别）" name="tags" rules={[{ required: true, message: '请至少添加一个标签' }]}>
          <TagInputWithAi />
        </Form.Item>
        <Button type="primary" htmlType="submit" loading={loading}>
          提交发布
        </Button>
      </Form>
    </Card>
  );
}

function UserPublishPage() {
  return (
    <>
      <PageHeaderCard title="发布中心" description="支持失物寻回和拾物招领双类型发布，智能标签自动去重与补全。" />
      <Tabs
        items={[
          { key: 'lost', label: '发布失物', children: <PublishForm type="lost" /> },
          { key: 'found', label: '发布招领', children: <PublishForm type="found" /> },
        ]}
      />
    </>
  );
}

export default UserPublishPage;
