# Lost Found Web Portal

React + Ant Design 前端门户，包含用户端与管理端双菜单系统。

## 功能覆盖
- 用户端：今日公告、个人信息管理、失物/招领查询、发布中心
- 管理端：数据看板、失物信息管理、招领信息管理、认领信息管理、用户信息管理、公告管理
- 统一：登录/注册、路由鉴权、可替换 Mock API 层

## 技术栈
- React 18
- React Router 6
- Ant Design 5
- ECharts
- Axios
- Vite

## 启动
```bash
npm install
npm run dev
```

默认使用 Mock 数据。若切换真实接口，可配置：
```bash
# .env.local
VITE_USE_MOCK=false
```

后端默认代理：`http://localhost:8080/api`
