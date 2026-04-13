import { useEffect, useMemo, useState } from 'react';
import { Card, Col, Row, Table, Tag, Typography } from 'antd';
import PageHeaderCard from '../../components/PageHeaderCard';
import { fetchCampusInsights } from '../../api/modules/business';

function UserLeaderboardPage() {
  const [leaderboard, setLeaderboard] = useState([]);

  useEffect(() => {
    fetchCampusInsights().then((res) => {
      setLeaderboard(res?.data?.leaderboard || []);
    });
  }, []);

  const columns = useMemo(
    () => [
      {
        title: '排名',
        dataIndex: 'rank',
        width: 88,
        render: (rank) => (rank <= 3 ? <Tag color={rank === 1 ? 'gold' : rank === 2 ? 'geekblue' : 'cyan'}>TOP {rank}</Tag> : `NO.${rank}`),
      },
      { title: '昵称', dataIndex: 'nickname', ellipsis: true },
      { title: '学院', dataIndex: 'college', ellipsis: true },
      { title: '信用积分', dataIndex: 'creditScore', sorter: (a, b) => a.creditScore - b.creditScore, defaultSortOrder: 'descend' },
      { title: '发布数', dataIndex: 'publishedCount' },
      { title: '归还数', dataIndex: 'returnedCount' },
      { title: '连续活跃天', dataIndex: 'keepDays' },
    ],
    [],
  );

  return (
    <>
      <PageHeaderCard title="拾金不昧积分榜" description="按近30天用户发布、归还、连续活跃等行为计算信用积分排行。" />
      <Row gutter={[16, 16]}>
        <Col xs={24}>
          <Card className="page-card" title="积分榜明细">
            <Table rowKey="rank" columns={columns} dataSource={leaderboard} pagination={false} />
            <Typography.Text type="secondary">
              积分规则：发布 +2，成功归还 +8，连续活跃额外加分。
            </Typography.Text>
          </Card>
        </Col>
      </Row>
    </>
  );
}

export default UserLeaderboardPage;
