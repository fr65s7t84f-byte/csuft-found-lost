# 后端初版说明

## 启动
```bash
cd backend
mvn spring-boot:run
```

## 已实现接口（全模块初版）
- 认证：`POST /api/user/login`、`POST /api/auth/register`
- 失物：`GET /api/lost/list`、`POST /api/lost/publish`、`PUT /api/lost/update/{id}`、`DELETE /api/lost/delete/{id}`
- 招领：`GET /api/found/list`、`POST /api/found/publish`、`PUT /api/found/update/{id}`、`DELETE /api/found/delete/{id}`
- 用户发布：`GET /api/user/publish/list`、`GET /api/user/items/{type}/{id}`、`GET /api/user/items/{type}/{id}/matches`、`PUT /api/user/publish/{type}/{id}/withdraw`
- 公告：`GET /api/announcement/today`
- 管理端：`/api/admin/dashboard/overview`、`/api/admin/claims`、`/api/admin/users`、`/api/admin/notices` 及审核更新接口
- 可视化：`GET /api/statistics/leaderboard`、`GET /api/statistics/heatmap`、`GET /api/statistics/campus-insights`
- 智能：`POST /api/intelligence/analyze-image`、`POST /api/intelligence/match-preview`

## 说明
- 当前为数据库驱动（H2 MySQL 模式）+ 规则模拟AI版本，用于前后端联调与架构验证。
- 数据库脚本：`src/main/resources/schema.sql`、`src/main/resources/data.sql`
- 关键表：`tb_user`、`tb_user_session`、`tb_lost_item`、`tb_found_item`、`tb_claim_application`、`tb_notification_record`
- 除登录/注册外，所有 `/api/**` 请求都需要 `Authorization: Bearer <token>`
- `/api/admin/**` 仅管理员可访问
