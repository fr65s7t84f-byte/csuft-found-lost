import { useState } from 'react';
import { Button, Col, Input, Row, Select, Space, Typography } from 'antd';
import { DownOutlined, FilterOutlined, RedoOutlined, SearchOutlined, UpOutlined } from '@ant-design/icons';

const statusOptionsMap = {
  lost: [
    { label: '待寻回', value: 'pending_claim' },
    { label: '审核中', value: 'reviewing' },
    { label: '已认领', value: 'claimed' },
    { label: '已完结', value: 'closed' },
  ],
  found: [
    { label: '待认领', value: 'pending_claim' },
    { label: '审核中', value: 'reviewing' },
    { label: '已认领', value: 'claimed' },
    { label: '已关闭', value: 'closed' },
  ],
};

const reviewOptions = [
  { label: '待审核', value: 'pending' },
  { label: '已通过', value: 'approved' },
  { label: '已驳回', value: 'rejected' },
];

function buildSelectOptions(values = []) {
  return values.filter(Boolean).map((value) => ({ label: value, value }));
}

function ItemFilterPanel({
  value,
  onChange,
  onSearch,
  onReset,
  optionSource,
  type = 'lost',
  showReviewStatus = false,
  keywordPlaceholder = '请输入关键词',
}) {
  const [expanded, setExpanded] = useState(false);

  const setField = (field, fieldValue) => {
    onChange({ ...value, [field]: fieldValue });
  };

  return (
    <div className="item-filter-card">
      <div className="item-filter-toolbar">
        <Space wrap size={12}>
          <Input
            allowClear
            value={value.keyword}
            placeholder={keywordPlaceholder}
            prefix={<SearchOutlined />}
            onChange={(event) => setField('keyword', event.target.value)}
            onPressEnter={onSearch}
            style={{ width: 320 }}
          />
          <Button type="primary" icon={<SearchOutlined />} onClick={onSearch}>
            查询
          </Button>
          <Button icon={<RedoOutlined />} onClick={onReset}>
            重置
          </Button>
        </Space>
        <Button type="text" icon={expanded ? <UpOutlined /> : <DownOutlined />} onClick={() => setExpanded((prev) => !prev)}>
          {expanded ? '收起筛选' : '展开筛选'}
        </Button>
      </div>

      {expanded && (
        <div className="item-filter-panel">
          <div className="item-filter-panel-title">
            <FilterOutlined />
            <Typography.Text strong>高级筛选</Typography.Text>
          </div>
          <Row gutter={[16, 16]}>
            <Col xs={24} sm={12} lg={6}>
              <Select
                allowClear
                value={value.category}
                placeholder="选择分类"
                options={buildSelectOptions(optionSource.categories)}
                onChange={(fieldValue) => setField('category', fieldValue)}
                style={{ width: '100%' }}
              />
            </Col>
            <Col xs={24} sm={12} lg={6}>
              <Select
                allowClear
                value={value.location}
                placeholder="选择地点"
                options={buildSelectOptions(optionSource.locations)}
                onChange={(fieldValue) => setField('location', fieldValue)}
                style={{ width: '100%' }}
              />
            </Col>
            <Col xs={24} sm={12} lg={6}>
              <Select
                allowClear
                value={value.tag}
                placeholder="选择标签"
                options={buildSelectOptions(optionSource.tags)}
                onChange={(fieldValue) => setField('tag', fieldValue)}
                style={{ width: '100%' }}
              />
            </Col>
            <Col xs={24} sm={12} lg={6}>
              <Select
                allowClear
                value={value.status}
                placeholder="选择业务状态"
                options={statusOptionsMap[type]}
                onChange={(fieldValue) => setField('status', fieldValue)}
                style={{ width: '100%' }}
              />
            </Col>
            {showReviewStatus && (
              <Col xs={24} sm={12} lg={6}>
                <Select
                  allowClear
                  value={value.reviewStatus}
                  placeholder="选择审核状态"
                  options={reviewOptions}
                  onChange={(fieldValue) => setField('reviewStatus', fieldValue)}
                  style={{ width: '100%' }}
                />
              </Col>
            )}
          </Row>
        </div>
      )}
    </div>
  );
}

export default ItemFilterPanel;
