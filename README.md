# 校园智能失物招领系统 - 项目说明文档

## 📋 项目概述

本项目是一个基于微信小程序的校园失物招领系统，采用前后端分离架构，集成AI图像识别和智能匹配算法，旨在提高校园失物招领的效率和成功率。

## 🏗️ 项目结构

```
lost-found-system/
├── backend/                          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/campus/lostfound/
│   │   │   │   ├── LostFoundApplication.java    # 启动类
│   │   │   │   ├── common/                      # 公共类
│   │   │   │   │   ├── Result.java              # 统一响应
│   │   │   │   │   ├── PageResult.java          # 分页响应
│   │   │   │   │   └── BusinessException.java   # 业务异常
│   │   │   │   ├── config/                      # 配置类
│   │   │   │   │   ├── WebConfig.java           # Web配置
│   │   │   │   │   ├── RedisConfig.java         # Redis配置
│   │   │   │   │   ├── OssConfig.java           # OSS配置
│   │   │   │   │   └── JwtConfig.java           # JWT配置
│   │   │   │   ├── controller/                  # 控制器
│   │   │   │   │   ├── UserController.java      # 用户接口
│   │   │   │   │   ├── LostItemController.java  # 失物接口
│   │   │   │   │   ├── FoundItemController.java # 招领接口
│   │   │   │   │   ├── MatchController.java     # 匹配接口
│   │   │   │   │   ├── MessageController.java   # 消息接口
│   │   │   │   │   ├── CommentController.java   # 评论接口
│   │   │   │   │   ├── FileController.java      # 文件上传接口
│   │   │   │   │   └── StatisticsController.java# 统计接口
│   │   │   │   ├── entity/                      # 实体类
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── LostItem.java
│   │   │   │   │   ├── FoundItem.java
│   │   │   │   │   ├── MatchRecord.java
│   │   │   │   │   ├── Message.java
│   │   │   │   │   ├── Comment.java
│   │   │   │   │   ├── Statistics.java
│   │   │   │   │   └── CreditLog.java
│   │   │   │   ├── dto/                         # 数据传输对象
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   ├── LostItemPublishRequest.java
│   │   │   │   │   ├── LostItemQueryRequest.java
│   │   │   │   │   └── ...
│   │   │   │   ├── vo/                          # 视图对象
│   │   │   │   │   ├── UserInfoVO.java
│   │   │   │   │   ├── LostItemVO.java
│   │   │   │   │   ├── LostItemDetailVO.java
│   │   │   │   │   ├── MatchResultVO.java
│   │   │   │   │   └── ...
│   │   │   │   ├── mapper/                      # 数据访问层
│   │   │   │   │   ├── UserMapper.java
│   │   │   │   │   ├── LostItemMapper.java
│   │   │   │   │   ├── FoundItemMapper.java
│   │   │   │   │   └── ...
│   │   │   │   ├── service/                     # 服务接口
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   ├── LostItemService.java
│   │   │   │   │   ├── FoundItemService.java
│   │   │   │   │   ├── MatchService.java        # 匹配算法
│   │   │   │   │   ├── AiService.java           # AI识别
│   │   │   │   │   ├── MessageService.java
│   │   │   │   │   ├── FileService.java
│   │   │   │   │   └── StatisticsService.java
│   │   │   │   ├── service/impl/                # 服务实现
│   │   │   │   │   └── ...
│   │   │   │   ├── interceptor/                 # 拦截器
│   │   │   │   │   └── JwtInterceptor.java      # JWT拦截器
│   │   │   │   ├── handler/                     # 处理器
│   │   │   │   │   ├── GlobalExceptionHandler.java  # 全局异常
│   │   │   │   │   └── MetaObjectHandler.java       # 字段填充
│   │   │   │   └── util/                        # 工具类
│   │   │   │       ├── JwtUtil.java
│   │   │   │       ├── WechatUtil.java
│   │   │   │       ├── OssUtil.java
│   │   │   │       └── BaiduAiUtil.java
│   │   │   └── resources/
│   │   │       ├── application.yml               # 配置文件
│   │   │       ├── schema.sql                    # 数据库脚本
│   │   │       └── mapper/                       # MyBatis映射文件
│   │   └── test/                                 # 测试代码
│   └── pom.xml                                   # Maven配置
│
├── miniprogram/                      # 小程序前端
│   ├── pages/                        # 页面
│   │   ├── index/                    # 首页
│   │   │   ├── index.js
│   │   │   ├── index.wxml
│   │   │   ├── index.wxss
│   │   │   └── index.json
│   │   ├── publish/                  # 发布页
│   │   │   ├── lost.js               # 发布失物
│   │   │   └── found.js              # 发布招领
│   │   ├── detail/                   # 详情页
│   │   │   ├── lost-detail.js
│   │   │   └── found-detail.js
│   │   ├── match/                    # 匹配页
│   │   │   └── match.js
│   │   ├── message/                  # 消息页
│   │   │   └── message.js
│   │   ├── statistics/               # 统计页
│   │   │   └── statistics.js
│   │   └── user/                     # 个人中心
│   │       └── user.js
│   ├── components/                   # 组件
│   │   ├── item-card/                # 物品卡片
│   │   ├── comment-list/             # 评论列表
│   │   └── chart/                    # 图表组件
│   ├── utils/                        # 工具类
│   │   ├── request.js                # 网络请求
│   │   ├── auth.js                   # 认证工具
│   │   └── util.js                   # 通用工具
│   ├── app.js                        # 小程序入口
│   ├── app.json                      # 全局配置
│   └── app.wxss                      # 全局样式
│
└── docs/                             # 文档
    ├── 系统分析设计文档.md
    ├── 接口文档.md
    ├── 部署文档.md
    └── 用户手册.md
```

## 🚀 快速开始

### 后端启动

1. **环境准备**
   - JDK 1.8+
   - MySQL 8.0
   - Redis 6.0
   - Maven 3.6+

2. **数据库初始化**
   ```bash
   mysql -u root -p < backend/src/main/resources/schema.sql
   ```

3. **配置修改**
   编辑 `backend/src/main/resources/application.yml`，修改以下配置：
   - 数据库连接信息
   - Redis连接信息
   - 微信小程序AppID和Secret
   - 阿里云OSS配置
   - 百度AI配置

4. **启动项目**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

5. **访问接口**
   - 接口地址: http://localhost:8080/api
   - 接口文档: http://localhost:8080/api/doc.html

### 小程序启动

1. **安装微信开发者工具**
   下载地址: https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html

2. **导入项目**
   - 打开微信开发者工具
   - 导入 `miniprogram` 目录
   - 填写AppID

3. **配置服务器域名**
   编辑 `miniprogram/utils/request.js`，修改 `baseURL` 为后端地址

4. **编译运行**
   点击"编译"按钮即可在模拟器中预览

## 🔑 核心功能实现

### 1. 微信登录流程

```
小程序端:
1. wx.login() 获取code
2. 调用后端 /api/user/login 接口

后端:
1. 使用code调用微信接口获取openid
2. 查询或创建用户
3. 生成JWT Token返回

小程序端:
4. 保存Token到本地存储
5. 后续请求携带Token
```

### 2. AI图像识别流程

```
1. 用户上传图片
2. 图片上传至OSS
3. 调用百度AI通用物体识别API
4. 解析识别结果，提取:
   - 物品名称
   - 颜色
   - 品牌
   - 其他特征
5. 自动推荐分类
6. 返回前端填充表单
```

### 3. 智能匹配算法

**多维度加权匹配算法:**

```
总分 = 分类匹配(30%) + 时间匹配(20%) + 地点匹配(20%)
     + 关键词匹配(20%) + AI标签匹配(10%)

1. 分类匹配: 完全一致得满分
2. 时间匹配: 7天内线性递减
3. 地点匹配: 基于经纬度距离计算（Haversine公式）
4. 关键词匹配: Jaccard相似度
5. AI标签匹配: 标签交集占比

匹配阈值: 60分（可配置）
```

**实现代码:** `MatchServiceImpl.java`

### 4. 数据可视化

**饼图 - 分类统计:**
```javascript
// 使用ECharts
option = {
  series: [{
    type: 'pie',
    data: [
      {value: 335, name: '证件'},
      {value: 234, name: '电子产品'},
      {value: 154, name: '书籍'},
      {value: 135, name: '钥匙'},
      {value: 100, name: '其他'}
    ]
  }]
}
```

**热力图 - 地点分布:**
```javascript
// 使用微信地图组件 + 热力图层
<map
  latitude="{{latitude}}"
  longitude="{{longitude}}"
  markers="{{markers}}"
  enable-heatmap="{{true}}"
  heatmap-data="{{heatmapData}}"
/>
```

## 📊 数据库设计要点

### 核心表关系

```
用户表 (tb_user)
  ├─ 1:N → 失物表 (tb_lost_item)
  ├─ 1:N → 招领表 (tb_found_item)
  ├─ 1:N → 消息表 (tb_message)
  └─ 1:N → 信用记录表 (tb_credit_log)

失物表 ←→ 招领表
  └─ N:M → 匹配记录表 (tb_match_record)

失物表/招领表
  └─ 1:N → 评论表 (tb_comment)

统计表 (tb_statistics)
  └─ 定时任务每日统计
```

### 索引优化

```sql
-- 高频查询字段建立索引
INDEX idx_category (category)
INDEX idx_status (status)
INDEX idx_create_time (create_time)
INDEX idx_location (location_lat, location_lng)

-- 全文索引用于关键词搜索
FULLTEXT idx_title_desc (title, description) WITH PARSER ngram
```

## 🔐 安全设计

### 1. 认证授权
- JWT Token认证
- Token有效期7天
- 支持Token刷新
- 拦截器统一鉴权

### 2. 数据安全
- 敏感信息加密存储
- SQL注入防护（MyBatis预编译）
- XSS攻击防护（输入过滤）
- CSRF防护

### 3. 接口安全
- 接口限流（防刷）
- 参数校验（Validation）
- 异常统一处理
- 日志记录

## 📈 性能优化

### 1. 数据库优化
- 合理建立索引
- 分页查询
- 避免N+1查询
- 连接池配置

### 2. 缓存策略
- Redis缓存热点数据
- 用户信息缓存
- 统计数据缓存
- 缓存过期策略

### 3. 前端优化
- 图片懒加载
- 列表虚拟滚动
- 防抖节流
- 本地缓存

## 🧪 测试

### 单元测试
```bash
mvn test
```

### 接口测试
- 使用Postman或Apifox
- 导入接口文档
- 测试各个接口

### 小程序测试
- 真机调试
- 体验版测试
- 性能分析

## 📦 部署

### 后端部署

1. **打包**
   ```bash
   mvn clean package -DskipTests
   ```

2. **上传服务器**
   ```bash
   scp target/lost-found-system.jar user@server:/app/
   ```

3. **启动服务**
   ```bash
   nohup java -jar lost-found-system.jar > app.log 2>&1 &
   ```

4. **配置Nginx**
   ```nginx
   server {
       listen 80;
       server_name your-domain.com;

       location /api/ {
           proxy_pass http://localhost:8080/api/;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
       }
   }
   ```

### 小程序部署

1. **上传代码**
   - 微信开发者工具 → 上传
   - 填写版本号和备注

2. **提交审核**
   - 登录微信公众平台
   - 小程序管理 → 版本管理
   - 提交审核

3. **发布上线**
   - 审核通过后点击发布

## 📝 开发规范

### 代码规范
- 遵循阿里巴巴Java开发手册
- 统一代码格式化
- 注释完整清晰
- 命名规范

### Git规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试
chore: 构建/工具变动
```

### 接口规范
- RESTful风格
- 统一响应格式
- 统一错误码
- 完整的接口文档

## 🐛 常见问题

### 1. 数据库连接失败
- 检查MySQL是否启动
- 检查连接配置是否正确
- 检查防火墙设置

### 2. Redis连接失败
- 检查Redis是否启动
- 检查连接配置
- 检查密码是否正确

### 3. 微信登录失败
- 检查AppID和Secret是否正确
- 检查网络连接
- 查看微信接口返回的错误信息

### 4. 图片上传失败
- 检查OSS配置
- 检查文件大小限制
- 检查文件格式

### 5. AI识别失败
- 检查百度AI配置
- 检查API调用次数
- 检查图片格式和大小

## 📞 技术支持

- 项目文档: 见 `docs/` 目录
- 问题反馈: 提交Issue
- 技术交流: 创建Discussion

## 📄 许可证

本项目仅用于学习和毕业设计，未经许可不得用于商业用途。

---

**开发者**: 肖强
**学号**: 20223590
**开发时间**: 2026年1月 - 2026年5月
**版本**: v1.0.0
