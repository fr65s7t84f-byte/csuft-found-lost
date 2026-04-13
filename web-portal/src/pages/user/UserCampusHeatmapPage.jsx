import { useEffect, useMemo, useState } from 'react';
import { Card, Col, Row, Segmented, Tooltip, Typography } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchCampusInsights } from '../../api/modules/business';

const hotspotTypeMeta = {
  all: { label: '综合热点', color: '#ea580c' },
  teaching: { label: '教学楼', color: '#2563eb' },
  facility: { label: '基础设施', color: '#16a34a' },
  dorm: { label: '宿舍区', color: '#9333ea' },
};

const buildBubbleSize = (weight) => {
  const min = 16;
  const max = 42;
  return min + ((Number(weight || 0) - 50) / 50) * (max - min);
};

function UserCampusHeatmapPage() {
  const [map, setMap] = useState(null);
  const [hotspots, setHotspots] = useState([]);
  const [hotspotType, setHotspotType] = useState('all');

  useEffect(() => {
    fetchCampusInsights().then((res) => {
      setMap(res?.data?.map || null);
      setHotspots(res?.data?.hotspots || []);
    });
  }, []);

  const visibleHotspots = useMemo(
    () => (hotspotType === 'all' ? hotspots : hotspots.filter((item) => item.type === hotspotType)),
    [hotspots, hotspotType],
  );

  return (
    <>
      <PageHeaderCard title="校园拾取热点图" description="基于教学楼与校园基础设施的拾取频次，展示近期热点点位与高峰时段。" />
      <Row gutter={[16, 16]}>
        <Col xs={24}>
          <Card className="page-card" title="热点分布">
            <div className="heatmap-toolbar">
              <Segmented
                options={[
                  { label: hotspotTypeMeta.all.label, value: 'all' },
                  { label: hotspotTypeMeta.teaching.label, value: 'teaching' },
                  { label: hotspotTypeMeta.facility.label, value: 'facility' },
                  { label: hotspotTypeMeta.dorm.label, value: 'dorm' },
                ]}
                value={hotspotType}
                onChange={setHotspotType}
              />
              <Typography.Text type="secondary">地图更新：{map?.updatedAt || '-'}</Typography.Text>
            </div>
            <div className="campus-map-wrap">
              <img src={map?.imageUrl} alt={`${map?.schoolName || '校园'}地图`} className="campus-map-image" />
              {visibleHotspots.map((spot) => (
                <Tooltip key={spot.id} title={`${spot.name} · 拾取${spot.pickups}次 · 高峰${spot.peakTime}`}>
                  <span
                    className="campus-hotspot-dot"
                    style={{
                      left: `${spot.x}%`,
                      top: `${spot.y}%`,
                      width: buildBubbleSize(spot.weight),
                      height: buildBubbleSize(spot.weight),
                      background: hotspotTypeMeta[spot.type]?.color,
                    }}
                  />
                </Tooltip>
              ))}
            </div>
            <Typography.Text type="secondary" style={{ display: 'block', marginTop: 12 }}>
              地图来源：{map?.sourceName || '本地静态图片'}
            </Typography.Text>
          </Card>
        </Col>
      </Row>
    </>
  );
}

export default UserCampusHeatmapPage;
