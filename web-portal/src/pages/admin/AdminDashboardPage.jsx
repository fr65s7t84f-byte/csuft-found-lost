import { useEffect, useMemo, useState } from 'react';
import { Card, Col, Row, Statistic } from 'antd';
import ReactECharts from 'echarts-for-react';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchDashboard } from '../../api/modules/business';

function AdminDashboardPage() {
  const [data, setData] = useState(null);

  useEffect(() => {
    fetchDashboard().then((res) => setData(res?.data || {}));
  }, []);

  const trendOption = useMemo(() => ({
    tooltip: { trigger: 'axis' },
    legend: { data: ['失物登记', '拾物登记'] },
    xAxis: { type: 'category', data: data?.trendLabels || [] },
    yAxis: { type: 'value' },
    series: [
      { name: '失物登记', type: 'line', smooth: true, data: data?.lostTrend || [] },
      { name: '拾物登记', type: 'line', smooth: true, data: data?.foundTrend || [] },
    ],
  }), [data]);

  const pieOption = useMemo(() => ({
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: ['40%', '68%'],
      data: (data?.categoryNames || []).map((name, idx) => ({ name, value: data?.categoryValues?.[idx] || 0 })),
    }],
  }), [data]);

  const barOption = useMemo(() => ({
    xAxis: { type: 'category', data: data?.categoryNames || [] },
    yAxis: { type: 'value', max: 100 },
    series: [{ type: 'bar', data: data?.retrievalByType || [], itemStyle: { borderRadius: [6, 6, 0, 0] } }],
  }), [data]);

  return (
    <>
      <PageHeaderCard title="数据看板" description="展示失物趋势、找回率、类型分布与运营关键指标。" />
      <Row gutter={[16, 16]}>
        <Col xs={24} md={12} lg={6}><Card className="page-card"><Statistic title="总登记量" value={data?.totalItems || 0} /></Card></Col>
        <Col xs={24} md={12} lg={6}><Card className="page-card"><Statistic title="找回率" value={data?.claimRate || 0} suffix="%" precision={1} /></Card></Col>
        <Col xs={24} md={12} lg={6}><Card className="page-card"><Statistic title="活跃用户" value={data?.activeUsers || 0} /></Card></Col>
        <Col xs={24} md={12} lg={6}><Card className="page-card"><Statistic title="平均处理时长" value={data?.avgProcessHours || 0} suffix="小时" precision={1} /></Card></Col>
      </Row>
      <Row gutter={[16, 16]} style={{ marginTop: 2 }}>
        <Col xs={24} lg={14}><Card className="page-card" title="登记趋势"><ReactECharts option={trendOption} style={{ height: 320 }} /></Card></Col>
        <Col xs={24} lg={10}><Card className="page-card" title="物品类型占比"><ReactECharts option={pieOption} style={{ height: 320 }} /></Card></Col>
        <Col xs={24}><Card className="page-card" title="分类型找回率"><ReactECharts option={barOption} style={{ height: 320 }} /></Card></Col>
      </Row>
    </>
  );
}

export default AdminDashboardPage;
