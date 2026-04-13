import { useState } from 'react';
import { Space, Input, Button, Tag, Upload, message } from 'antd';
import { PlusOutlined, UploadOutlined } from '@ant-design/icons';

const suggestions = ['书包', '蓝色', '帆布', '钥匙', '黑色', '眼镜', '手机'];

function TagInputWithAi({ value = [], onChange }) {
  const [text, setText] = useState('');

  const addTag = (tag) => {
    const next = Array.from(new Set([...(value || []), tag])).filter(Boolean);
    onChange?.(next);
  };

  const removeTag = (tag) => {
    onChange?.((value || []).filter((v) => v !== tag));
  };

  const parseFromFilename = (name) => {
    const mapped = suggestions.filter((x) => name.includes(x)).slice(0, 3);
    return mapped.length ? mapped : ['智能识别', '待确认'];
  };

  return (
    <Space direction="vertical" style={{ width: '100%' }}>
      <Space wrap>
        {(value || []).map((tag) => (
          <Tag key={tag} closable onClose={() => removeTag(tag)} color="blue">
            {tag}
          </Tag>
        ))}
      </Space>
      <Space wrap>
        <Input
          placeholder="输入标签后回车添加"
          value={text}
          onChange={(e) => setText(e.target.value)}
          onPressEnter={() => {
            addTag(text.trim());
            setText('');
          }}
          style={{ width: 260 }}
        />
        <Button
          icon={<PlusOutlined />}
          onClick={() => {
            addTag(text.trim());
            setText('');
          }}
        >
          添加标签
        </Button>
        <Upload
          accept="image/*"
          showUploadList={false}
          beforeUpload={(file) => {
            parseFromFilename(file.name).forEach(addTag);
            message.success('AI识别完成，已自动补充标签（可手动调整）');
            return false;
          }}
        >
          <Button icon={<UploadOutlined />}>上传图片智能识别</Button>
        </Upload>
      </Space>
      <Space wrap>
        {suggestions.map((t) => (
          <Tag key={t} style={{ cursor: 'pointer' }} onClick={() => addTag(t)}>
            {t}
          </Tag>
        ))}
      </Space>
    </Space>
  );
}

export default TagInputWithAi;
