import dayjs from 'dayjs';
import { getAuth } from '../store/auth';

const now = dayjs();

const getCurrentPublisher = () => {
  const auth = getAuth();
  return {
    publisherId: auth?.token || 'mock-user-token',
    publisherName: auth?.name || '校园用户',
  };
};

const makeItem = (id, type, status, extra = {}) => ({
  id,
  type,
  title: extra.title || (type === 'lost' ? `遗失物品-${id}` : `拾取物品-${id}`),
  category: extra.category || (id % 2 ? '书包' : '电子产品'),
  location: extra.location || (id % 2 ? '图书馆' : '教学楼A区'),
  contact: extra.contact || '18173323763',
  description: extra.description || '黑色拉链，内有学习资料',
  tags: extra.tags || ['书包', '蓝色', '帆布'],
  imageUrl:
    extra.imageUrl ||
    `https://dummyimage.com/120x120/e8eef5/3b5368.png&text=${type === 'lost' ? 'LOST' : 'FOUND'}-${id}`,
  lostOrFoundTime: extra.lostOrFoundTime || now.subtract(id, 'hour').format('YYYY-MM-DD HH:mm'),
  publishedAt: extra.publishedAt || now.subtract(id % 12, 'hour').format('YYYY-MM-DD HH:mm'),
  publisherId: extra.publisherId || getCurrentPublisher().publisherId,
  publisherName: extra.publisherName || getCurrentPublisher().publisherName,
  status,
  urgent: Boolean(extra.urgent),
  reviewStatus: extra.reviewStatus || 'pending',
  matchScore: extra.matchScore || Math.floor(70 + Math.random() * 30),
  withdrawn: Boolean(extra.withdrawn),
});

let lostItems = [
  makeItem(1001, 'lost', 'pending_claim', {
    title: '蓝色帆布书包',
    urgent: true,
    location: '图书馆',
    tags: ['书包', '蓝色', '帆布', 'U盘'],
    description: '包内有实验课资料和一个银色U盘',
    publisherName: '校园用户',
    publisherId: 'mock-user-token',
  }),
  makeItem(1002, 'lost', 'pending_claim', {
    title: '黑色圆框眼镜',
    category: '眼镜',
    location: '教学楼A区',
    tags: ['眼镜', '黑色框', '圆形'],
    publisherName: '王同学',
    publisherId: 'mock-other-1',
  }),
  makeItem(1003, 'lost', 'closed', {
    title: '华为平板',
    category: '电子产品',
    location: '宿舍园区',
    tags: ['平板', '华为', '灰色'],
    publisherName: '校园用户',
    publisherId: 'mock-user-token',
  }),
  makeItem(1004, 'lost', 'reviewing', {
    title: '校园卡',
    category: '证件',
    location: '食堂二楼',
    tags: ['校园卡', '蓝色'],
    publisherName: '李四',
    publisherId: 'mock-other-2',
  }),
];

let foundItems = [
  makeItem(2001, 'found', 'pending_claim', {
    title: '银色保温杯',
    category: '生活用品',
    location: '图书馆',
    tags: ['保温杯', '银色'],
    publisherName: '校园用户',
    publisherId: 'mock-user-token',
  }),
  makeItem(2002, 'found', 'reviewing', {
    title: 'AirPods 保护壳',
    category: '电子配件',
    location: '教学楼A区',
    tags: ['耳机壳', '白色'],
    publisherName: '赵六',
    publisherId: 'mock-other-3',
  }),
  makeItem(2003, 'found', 'pending_claim', {
    title: '黑色雨伞',
    category: '生活用品',
    location: '食堂二楼',
    tags: ['雨伞', '黑色'],
    publisherName: '校园用户',
    publisherId: 'mock-user-token',
  }),
];

let announcements = {
  todayLost: lostItems.slice(0, 2),
  todayFound: foundItems.slice(0, 2),
  urgent: [
    { id: 1, content: '紧急寻物：蓝色帆布书包，内含实验数据U盘。', createdAt: now.format('YYYY-MM-DD HH:mm') },
    { id: 2, content: '紧急寻物：黑色华为手机，疑似遗落食堂二楼。', createdAt: now.subtract(1, 'hour').format('YYYY-MM-DD HH:mm') },
  ],
  important: [
    { id: 11, content: '公告：认领需提供有效证件并完成管理员审核。' },
    { id: 12, content: '公告：本周末系统维护，通知可能延迟 5 分钟。' },
  ],
};

const campusInsights = {
  map: {
    schoolName: '中南林业科技大学',
    imageUrl: '/maps/csuft-campus-latest.jpg',
    sourceName: '本地静态图片',
    sourceUrl: '/maps/csuft-campus-latest.jpg',
    updatedAt: now.format('YYYY-MM-DD HH:mm'),
  },
  leaderboard: [
    { rank: 1, nickname: '林小青', college: '林学院', creditScore: 168, publishedCount: 22, returnedCount: 16, keepDays: 47 },
    { rank: 2, nickname: '木木同学', college: '风景园林学院', creditScore: 156, publishedCount: 19, returnedCount: 13, keepDays: 35 },
    { rank: 3, nickname: '求是达人', college: '计算机与数学学院', creditScore: 149, publishedCount: 17, returnedCount: 12, keepDays: 28 },
    { rank: 4, nickname: '拾光者', college: '食品科学与工程学院', creditScore: 142, publishedCount: 16, returnedCount: 11, keepDays: 22 },
    { rank: 5, nickname: '崇德小队', college: '生命与环境科学学院', creditScore: 136, publishedCount: 14, returnedCount: 10, keepDays: 19 },
    { rank: 6, nickname: '归还超人', college: '机电工程学院', creditScore: 131, publishedCount: 13, returnedCount: 9, keepDays: 16 },
    { rank: 7, nickname: '知行合一', college: '经济学院', creditScore: 127, publishedCount: 11, returnedCount: 8, keepDays: 14 },
    { rank: 8, nickname: '南林志愿者', college: '土木工程学院', creditScore: 123, publishedCount: 10, returnedCount: 7, keepDays: 12 },
  ],
  hotspots: [
    { id: 1, name: '求是楼', type: 'teaching', x: 38, y: 37, weight: 95, pickups: 29, peakTime: '11:40-13:10' },
    { id: 2, name: '崇德楼', type: 'teaching', x: 45, y: 40, weight: 88, pickups: 24, peakTime: '13:30-15:00' },
    { id: 3, name: '致知楼', type: 'teaching', x: 53, y: 34, weight: 72, pickups: 18, peakTime: '16:50-18:00' },
    { id: 4, name: '新图书馆', type: 'facility', x: 61, y: 44, weight: 84, pickups: 22, peakTime: '20:00-21:40' },
    { id: 5, name: '大学生活动中心', type: 'facility', x: 66, y: 56, weight: 63, pickups: 14, peakTime: '18:00-20:00' },
    { id: 6, name: '体艺馆', type: 'facility', x: 73, y: 48, weight: 67, pickups: 15, peakTime: '17:00-19:00' },
    { id: 7, name: '体育场', type: 'facility', x: 78, y: 60, weight: 58, pickups: 11, peakTime: '19:00-21:00' },
    { id: 8, name: '学生宿舍北区', type: 'dorm', x: 31, y: 60, weight: 76, pickups: 20, peakTime: '22:00-23:00' },
    { id: 9, name: '学生宿舍南区', type: 'dorm', x: 25, y: 70, weight: 69, pickups: 17, peakTime: '21:30-23:00' },
    { id: 10, name: '第一食堂', type: 'facility', x: 46, y: 57, weight: 90, pickups: 26, peakTime: '11:30-13:00' },
  ],
};

let claims = [
  {
    id: 3001,
    itemId: 1001,
    itemType: 'lost',
    itemTitle: '蓝色帆布书包',
    applicant: '张三',
    studentId: '20221234',
    score: 89,
    status: 'pending',
    submittedAt: now.subtract(2, 'hour').format('YYYY-MM-DD HH:mm'),
  },
  {
    id: 3002,
    itemId: 2003,
    itemType: 'found',
    itemTitle: '黑色雨伞',
    applicant: '李四',
    studentId: '20229876',
    score: 78,
    status: 'approved',
    submittedAt: now.subtract(1, 'day').format('YYYY-MM-DD HH:mm'),
  },
];

let users = [
  { id: 1, nickname: '管理员', phone: '13800138000', studentId: 'admin', role: 'admin', status: 'active' },
  { id: 2, nickname: '肖强', phone: '18173323763', studentId: '20223590', role: 'user', status: 'active' },
  { id: 3, nickname: '王同学', phone: '13900001234', studentId: '20224567', role: 'user', status: 'frozen' },
];

let notices = [
  { id: 501, title: '开学季防丢提醒', content: '请妥善保管个人贵重物品。', status: 'published', createdAt: now.subtract(1, 'day').format('YYYY-MM-DD HH:mm') },
  { id: 502, title: '系统升级通知', content: '周日 2:00-4:00 升级维护。', status: 'draft', createdAt: now.format('YYYY-MM-DD HH:mm') },
];

let notifications = [
  {
    id: 9001,
    userId: 'mock-user-token',
    title: '认领结果通知',
    content: '您提交的黑色雨伞认领申请已审核通过，请及时联系发布人线下交接。',
    read: false,
    sendStatus: 'sent',
    createdAt: now.subtract(3, 'hour').format('YYYY-MM-DD HH:mm'),
  },
  {
    id: 9002,
    userId: 'mock-user-token',
    title: '系统消息',
    content: '欢迎使用校园智能失物招领系统，发布和认领动态会在此展示。',
    read: true,
    sendStatus: 'sent',
    createdAt: now.subtract(1, 'day').format('YYYY-MM-DD HH:mm'),
  },
];

const pageWrap = (list, pageNum = 1, pageSize = 10) => ({
  records: list.slice((pageNum - 1) * pageSize, pageNum * pageSize),
  total: list.length,
  pageNum,
  pageSize,
});

const normalizeText = (value) => String(value || '').trim().toLowerCase();

const getSourceByType = (type) => (type === 'lost' ? lostItems : foundItems);

const matchItemFilters = (item, params = {}) => {
  const keyword = normalizeText(params.keyword);
  const category = normalizeText(params.category);
  const location = normalizeText(params.location);
  const tag = normalizeText(params.tag);
  const status = normalizeText(params.status);
  const reviewStatus = normalizeText(params.reviewStatus);
  const publisherId = normalizeText(params.publisherId);
  const searchText = [item.title, item.category, item.location, item.description, item.tags.join(',')]
    .join('|')
    .toLowerCase();

  return (
    (!keyword || searchText.includes(keyword)) &&
    (!category || normalizeText(item.category) === category) &&
    (!location || normalizeText(item.location) === location) &&
    (!tag || item.tags.some((itemTag) => normalizeText(itemTag) === tag)) &&
    (!status || normalizeText(item.status) === status) &&
    (!reviewStatus || normalizeText(item.reviewStatus) === reviewStatus) &&
    (!publisherId || normalizeText(item.publisherId) === publisherId)
  );
};

const computeMatchScore = (baseItem, targetItem) => {
  let score = 45;
  const overlapTags = baseItem.tags.filter((tag) => targetItem.tags.includes(tag));
  score += Math.min(overlapTags.length * 12, 36);
  if (baseItem.category === targetItem.category) score += 12;
  if (baseItem.location === targetItem.location) score += 10;

  const baseTitleChars = new Set(String(baseItem.title || ''));
  const sharedChars = String(targetItem.title || '')
    .split('')
    .filter((char) => baseTitleChars.has(char)).length;
  score += Math.min(sharedChars * 2, 12);

  return Math.min(score, 99);
};

const buildMatchReasons = (baseItem, targetItem) => {
  const reasons = [];
  const overlapTags = baseItem.tags.filter((tag) => targetItem.tags.includes(tag));
  if (overlapTags.length) reasons.push(`共同标签：${overlapTags.join('、')}`);
  if (baseItem.category === targetItem.category) reasons.push(`同类物品：${baseItem.category}`);
  if (baseItem.location === targetItem.location) reasons.push(`地点接近：${baseItem.location}`);
  if (!reasons.length) reasons.push('综合标题、描述与标签语义相近');
  return reasons;
};

export const statusText = {
  pending_claim: '待认领',
  reviewing: '审核中',
  claimed: '已认领',
  closed: '已关闭',
};

export async function mockLogin(payload) {
  const role = payload?.role || (String(payload?.account || '').includes('admin') ? 'admin' : 'user');
  return {
    code: 0,
    message: '登录成功',
    data: {
      token: role === 'admin' ? `mock-admin-token-${Date.now()}` : 'mock-user-token',
      role,
      name: role === 'admin' ? '系统管理员' : '校园用户',
      avatarUrl: '',
    },
  };
}

export async function mockRegister() {
  return { code: 0, message: '注册成功', data: true };
}

export async function mockLostItems(params = {}) {
  const list = lostItems.filter((item) => matchItemFilters(item, params));
  return { code: 0, data: pageWrap(list, Number(params.pageNum || 1), Number(params.pageSize || 10)) };
}

export async function mockFoundItems(params = {}) {
  const list = foundItems.filter((item) => matchItemFilters(item, params));
  return { code: 0, data: pageWrap(list, Number(params.pageNum || 1), Number(params.pageSize || 10)) };
}

export async function mockPublishItem(type, payload) {
  const source = getSourceByType(type);
  const publisher = getCurrentPublisher();
  source.unshift(
    makeItem(Date.now(), type, 'pending_claim', {
      ...payload,
      publisherId: publisher.publisherId,
      publisherName: publisher.publisherName,
      publishedAt: dayjs().format('YYYY-MM-DD HH:mm'),
      lostOrFoundTime: dayjs().format('YYYY-MM-DD HH:mm'),
      imageUrl:
        payload?.imageUrl ||
        `https://dummyimage.com/120x120/e8eef5/3b5368.png&text=${type === 'lost' ? 'LOST' : 'FOUND'}-NEW`,
      reviewStatus: 'pending',
      withdrawn: false,
    }),
  );
  return { code: 0, message: '发布成功', data: true };
}

export async function mockMyPublishedItems(params = {}) {
  const publisher = getCurrentPublisher();
  const targetType = params.type;
  const allItems = [
    ...(targetType === 'found' ? [] : lostItems),
    ...(targetType === 'lost' ? [] : foundItems),
  ]
    .filter((item) => item.publisherId === publisher.publisherId)
    .filter((item) => matchItemFilters(item, params))
    .sort((left, right) => dayjs(right.publishedAt).valueOf() - dayjs(left.publishedAt).valueOf());

  return { code: 0, data: pageWrap(allItems, Number(params.pageNum || 1), Number(params.pageSize || 10)) };
}

export async function mockItemDetail(type, id) {
  const source = getSourceByType(type);
  const item = source.find((record) => String(record.id) === String(id));
  return { code: item ? 0 : 1, message: item ? '查询成功' : '记录不存在', data: item || null };
}

export async function mockItemMatches(type, id) {
  const currentItem = getSourceByType(type).find((record) => String(record.id) === String(id));
  if (!currentItem) return { code: 1, message: '记录不存在', data: [] };

  const targetList = type === 'lost' ? foundItems : lostItems;
  const records = targetList
    .map((item) => ({
      ...item,
      score: computeMatchScore(currentItem, item),
      matchReasons: buildMatchReasons(currentItem, item),
    }))
    .sort((left, right) => right.score - left.score)
    .slice(0, 5);

  return { code: 0, message: '匹配成功', data: records };
}

export async function mockWithdrawItem(type, id) {
  const source = getSourceByType(type);
  const idx = source.findIndex((item) => String(item.id) === String(id));
  if (idx >= 0) {
    source[idx] = {
      ...source[idx],
      status: 'closed',
      withdrawn: true,
      reviewStatus: source[idx].reviewStatus === 'approved' ? 'approved' : source[idx].reviewStatus,
    };
  }
  return { code: 0, message: '撤回成功', data: true };
}

export async function mockAnnouncements() {
  return { code: 0, data: announcements };
}

export async function mockCampusInsights() {
  return { code: 0, data: campusInsights };
}

export async function mockDashboard() {
  return {
    code: 0,
    data: {
      totalItems: lostItems.length + foundItems.length,
      claimRate: 72.3,
      activeUsers: users.filter((user) => user.status === 'active').length,
      avgProcessHours: 19.6,
      trendLabels: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      lostTrend: [16, 12, 18, 14, 19, 11, 13],
      foundTrend: [8, 11, 7, 9, 13, 10, 6],
      categoryNames: ['书包', '电子产品', '证件', '眼镜', '其他'],
      categoryValues: [35, 25, 15, 10, 15],
      retrievalByType: [85, 72, 69, 68, 60],
    },
  };
}

export async function mockClaims() {
  return { code: 0, data: claims };
}

export async function mockUsers() {
  return { code: 0, data: users };
}

export async function mockNotices() {
  return { code: 0, data: notices };
}

export async function mockUpdateItem(type, id, payload) {
  const source = getSourceByType(type);
  const idx = source.findIndex((item) => item.id === id);
  if (idx >= 0) source[idx] = { ...source[idx], ...payload };
  return { code: 0, message: '更新成功', data: true };
}

export async function mockDeleteItem(type, id) {
  if (type === 'lost') {
    lostItems = lostItems.filter((item) => item.id !== id);
  } else {
    foundItems = foundItems.filter((item) => item.id !== id);
  }
  return { code: 0, message: '删除成功', data: true };
}

export async function mockReviewItem(type, id, reviewStatus) {
  const source = getSourceByType(type);
  const idx = source.findIndex((item) => item.id === id);
  if (idx >= 0) source[idx] = { ...source[idx], reviewStatus };
  return { code: 0, message: '审核成功', data: true };
}

export async function mockUpdateClaim(id, status) {
  const claim = claims.find((item) => item.id === id);
  claims = claims.map((item) => (item.id === id ? { ...item, status } : item));

  if (claim && status === 'approved') {
    const source = claim.itemType === 'lost' ? lostItems : foundItems;
    const idx = source.findIndex((item) => item.id === claim.itemId);
    if (idx >= 0) {
      source[idx] = { ...source[idx], status: 'claimed', reviewStatus: 'approved' };
      notifications.unshift({
        id: Date.now(),
        userId: source[idx].publisherId,
        title: '认领申请已通过',
        content: `物品“${source[idx].title}”的认领申请已审核通过，请及时线下交接。`,
        read: false,
        sendStatus: 'sent',
        createdAt: dayjs().format('YYYY-MM-DD HH:mm'),
      });
    }
    notifications.unshift({
      id: Date.now() + 1,
      userId: 'mock-user-token',
      title: '认领申请审核通过',
      content: `您提交的“${claim.itemTitle}”认领申请已通过，相关物品状态已更新。`,
      read: false,
      sendStatus: 'sent',
      createdAt: dayjs().format('YYYY-MM-DD HH:mm'),
    });
    claims = claims.map((item) => (item.itemId === claim.itemId && item.id !== id && item.status === 'pending' ? { ...item, status: 'rejected' } : item));
  }

  if (claim && status === 'rejected') {
    notifications.unshift({
      id: Date.now() + 2,
      userId: 'mock-user-token',
      title: '认领申请未通过',
      content: `您提交的“${claim.itemTitle}”认领申请未通过，请补充更多证明信息后再试。`,
      read: false,
      sendStatus: 'sent',
      createdAt: dayjs().format('YYYY-MM-DD HH:mm'),
    });
  }

  return { code: 0, message: '处理成功', data: true };
}

export async function mockSubmitClaimApplication(type, id, payload) {
  const source = getSourceByType(type);
  const item = source.find((record) => String(record.id) === String(id));
  const publisher = getCurrentPublisher();
  if (!item) return { code: 1, message: '记录不存在', data: null };
  if (String(item.publisherId) === String(publisher.publisherId)) {
    return { code: 1, message: '不能认领自己发布的物品', data: null };
  }

  const claimId = Date.now();
  claims.unshift({
    id: claimId,
    itemId: item.id,
    itemType: type,
    itemTitle: item.title,
    applicant: publisher.publisherName,
    studentId: '2022MOCK',
    score: Math.min(95, 60 + String(payload?.evidenceText || '').length),
    status: 'pending',
    submittedAt: dayjs().format('YYYY-MM-DD HH:mm'),
  });

  notifications.unshift({
    id: claimId + 1,
    userId: publisher.publisherId,
    title: '认领申请已提交',
    content: `您提交了“${item.title}”的认领申请，请等待管理员审核。`,
    read: false,
    sendStatus: 'sent',
    createdAt: dayjs().format('YYYY-MM-DD HH:mm'),
  });
  notifications.unshift({
    id: claimId + 2,
    userId: item.publisherId,
    title: '收到新的认领申请',
    content: `物品“${item.title}”收到新的认领申请，请留意管理员审核结果。`,
    read: false,
    sendStatus: 'sent',
    createdAt: dayjs().format('YYYY-MM-DD HH:mm'),
  });

  return { code: 0, message: '提交成功', data: claimId };
}

export async function mockUserNotifications() {
  const publisher = getCurrentPublisher();
  const list = notifications.filter((item) => String(item.userId) === String(publisher.publisherId));
  return { code: 0, data: list };
}

export async function mockUpdateUser(id, payload) {
  users = users.map((user) => (user.id === id ? { ...user, ...payload } : user));
  return { code: 0, message: '更新成功', data: true };
}

export async function mockUpdateNotice(id, payload) {
  notices = notices.map((notice) => (notice.id === id ? { ...notice, ...payload } : notice));
  return { code: 0, message: '更新成功', data: true };
}

export async function mockCreateNotice(payload) {
  notices.unshift({
    id: Date.now(),
    createdAt: dayjs().format('YYYY-MM-DD HH:mm'),
    status: 'draft',
    ...payload,
  });
  return { code: 0, message: '新增成功', data: true };
}

