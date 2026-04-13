-- 校园智能失物招领系统 MySQL 8.0 建库建表脚本
-- 说明：本脚本面向 MySQL 8.0，兼顾当前系统实现与后续扩展。

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS campus_lost_found
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE campus_lost_found;

DROP TABLE IF EXISTS tb_notification_record;
DROP TABLE IF EXISTS tb_claim_application;
DROP TABLE IF EXISTS tb_found_item;
DROP TABLE IF EXISTS tb_lost_item;
DROP TABLE IF EXISTS tb_user_session;
DROP TABLE IF EXISTS tb_notice;
DROP TABLE IF EXISTS tb_hotspot;
DROP TABLE IF EXISTS tb_user;

CREATE TABLE tb_user (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  username VARCHAR(64) NOT NULL COMMENT '登录用户名',
  password_hash VARCHAR(128) NOT NULL COMMENT '密码哈希',
  phone VARCHAR(32) DEFAULT NULL COMMENT '手机号',
  email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
  student_id VARCHAR(64) DEFAULT NULL COMMENT '学号',
  real_name VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
  nickname VARCHAR(64) NOT NULL COMMENT '昵称',
  avatar_url VARCHAR(255) DEFAULT NULL COMMENT '头像地址',
  gender VARCHAR(16) DEFAULT 'unknown' COMMENT '性别',
  college VARCHAR(128) DEFAULT NULL COMMENT '学院',
  major VARCHAR(128) DEFAULT NULL COMMENT '专业',
  grade VARCHAR(32) DEFAULT NULL COMMENT '年级',
  role VARCHAR(16) NOT NULL DEFAULT 'user' COMMENT '角色：admin/user',
  status VARCHAR(16) NOT NULL DEFAULT 'active' COMMENT '状态：active/frozen',
  credit_score INT NOT NULL DEFAULT 100 COMMENT '信用分',
  publish_count INT NOT NULL DEFAULT 0 COMMENT '发布次数',
  success_return_count INT NOT NULL DEFAULT 0 COMMENT '成功找回次数',
  continuous_active_days INT NOT NULL DEFAULT 0 COMMENT '连续活跃天数',
  last_login_at DATETIME DEFAULT NULL COMMENT '最后登录时间',
  last_login_ip VARCHAR(64) DEFAULT NULL COMMENT '最后登录IP',
  remark VARCHAR(255) DEFAULT NULL COMMENT '备注',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_username (username),
  UNIQUE KEY uk_user_phone (phone),
  UNIQUE KEY uk_user_email (email),
  UNIQUE KEY uk_user_student_id (student_id),
  KEY idx_user_role_status (role, status),
  KEY idx_user_credit_score (credit_score)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE tb_user_session (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话主键',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  token VARCHAR(128) NOT NULL COMMENT '登录令牌',
  issued_at DATETIME NOT NULL COMMENT '签发时间',
  expired_at DATETIME NOT NULL COMMENT '过期时间',
  last_access_at DATETIME NOT NULL COMMENT '最后访问时间',
  login_ip VARCHAR(64) DEFAULT NULL COMMENT '登录IP',
  user_agent VARCHAR(255) DEFAULT NULL COMMENT '客户端标识',
  status VARCHAR(16) NOT NULL DEFAULT 'active' COMMENT '状态：active/disabled/expired',
  PRIMARY KEY (id),
  UNIQUE KEY uk_session_token (token),
  KEY idx_session_user_status (user_id, status),
  KEY idx_session_expired_at (expired_at),
  CONSTRAINT fk_session_user FOREIGN KEY (user_id) REFERENCES tb_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户会话表';
CREATE TABLE tb_lost_item (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '失物主键',
  lost_code VARCHAR(64) NOT NULL COMMENT '失物编码',
  title VARCHAR(128) NOT NULL COMMENT '标题',
  category VARCHAR(64) NOT NULL COMMENT '一级分类',
  sub_category VARCHAR(64) DEFAULT NULL COMMENT '二级分类',
  description TEXT COMMENT '详细描述',
  lost_time DATETIME DEFAULT NULL COMMENT '丢失时间',
  lost_location VARCHAR(255) NOT NULL COMMENT '丢失地点',
  location_lat DECIMAL(10,7) DEFAULT NULL COMMENT '纬度',
  location_lng DECIMAL(10,7) DEFAULT NULL COMMENT '经度',
  contact_phone VARCHAR(32) DEFAULT NULL COMMENT '联系电话',
  contact_wechat VARCHAR(64) DEFAULT NULL COMMENT '联系微信',
  image_url VARCHAR(255) DEFAULT NULL COMMENT '主图地址',
  image_urls TEXT COMMENT '多图地址列表',
  ai_tags TEXT COMMENT 'AI识别标签',
  manual_tags TEXT COMMENT '人工标签',
  ocr_text TEXT COMMENT 'OCR文本',
  brand VARCHAR(64) DEFAULT NULL COMMENT '品牌',
  color VARCHAR(64) DEFAULT NULL COMMENT '颜色',
  material VARCHAR(64) DEFAULT NULL COMMENT '材质',
  reward_amount DECIMAL(10,2) DEFAULT NULL COMMENT '悬赏金额',
  urgency_level VARCHAR(16) NOT NULL DEFAULT 'normal' COMMENT '紧急程度：low/normal/high',
  status VARCHAR(32) NOT NULL DEFAULT 'pending_claim' COMMENT '状态：pending_claim/claimed/closed',
  review_status VARCHAR(32) NOT NULL DEFAULT 'pending' COMMENT '审核状态：pending/approved/rejected',
  publisher_id BIGINT NOT NULL COMMENT '发布人ID',
  publisher_name VARCHAR(64) DEFAULT NULL COMMENT '发布人姓名',
  published_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  withdrawn TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否撤回',
  withdrawn_at DATETIME DEFAULT NULL COMMENT '撤回时间',
  closed_at DATETIME DEFAULT NULL COMMENT '关闭时间',
  extra_json TEXT COMMENT '扩展JSON文本',
  PRIMARY KEY (id),
  UNIQUE KEY uk_lost_code (lost_code),
  KEY idx_lost_review_status (review_status, status),
  KEY idx_lost_category_status (category, status),
  KEY idx_lost_location_time (lost_location, lost_time),
  KEY idx_lost_publisher_time (publisher_id, published_at),
  CONSTRAINT fk_lost_user FOREIGN KEY (publisher_id) REFERENCES tb_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='失物表';

CREATE TABLE tb_found_item (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '招领主键',
  found_code VARCHAR(64) NOT NULL COMMENT '招领编码',
  title VARCHAR(128) NOT NULL COMMENT '标题',
  category VARCHAR(64) NOT NULL COMMENT '一级分类',
  sub_category VARCHAR(64) DEFAULT NULL COMMENT '二级分类',
  description TEXT COMMENT '详细描述',
  found_time DATETIME DEFAULT NULL COMMENT '拾取时间',
  found_location VARCHAR(255) NOT NULL COMMENT '拾取地点',
  location_lat DECIMAL(10,7) DEFAULT NULL COMMENT '纬度',
  location_lng DECIMAL(10,7) DEFAULT NULL COMMENT '经度',
  contact_phone VARCHAR(32) DEFAULT NULL COMMENT '联系电话',
  contact_wechat VARCHAR(64) DEFAULT NULL COMMENT '联系微信',
  storage_location VARCHAR(255) DEFAULT NULL COMMENT '保管地点',
  image_url VARCHAR(255) DEFAULT NULL COMMENT '主图地址',
  image_urls TEXT COMMENT '多图地址列表',
  ai_tags TEXT COMMENT 'AI识别标签',
  manual_tags TEXT COMMENT '人工标签',
  ocr_text TEXT COMMENT 'OCR文本',
  brand VARCHAR(64) DEFAULT NULL COMMENT '品牌',
  color VARCHAR(64) DEFAULT NULL COMMENT '颜色',
  material VARCHAR(64) DEFAULT NULL COMMENT '材质',
  pickup_method VARCHAR(64) DEFAULT NULL COMMENT '认领方式',
  urgency_level VARCHAR(16) NOT NULL DEFAULT 'normal' COMMENT '紧急程度：low/normal/high',
  status VARCHAR(32) NOT NULL DEFAULT 'pending_claim' COMMENT '状态：pending_claim/claimed/closed',
  review_status VARCHAR(32) NOT NULL DEFAULT 'pending' COMMENT '审核状态：pending/approved/rejected',
  publisher_id BIGINT NOT NULL COMMENT '发布人ID',
  publisher_name VARCHAR(64) DEFAULT NULL COMMENT '发布人姓名',
  published_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  withdrawn TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否撤回',
  withdrawn_at DATETIME DEFAULT NULL COMMENT '撤回时间',
  closed_at DATETIME DEFAULT NULL COMMENT '关闭时间',
  extra_json TEXT COMMENT '扩展JSON文本',
  PRIMARY KEY (id),
  UNIQUE KEY uk_found_code (found_code),
  KEY idx_found_review_status (review_status, status),
  KEY idx_found_category_status (category, status),
  KEY idx_found_location_time (found_location, found_time),
  KEY idx_found_publisher_time (publisher_id, published_at),
  CONSTRAINT fk_found_user FOREIGN KEY (publisher_id) REFERENCES tb_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招领表';
CREATE TABLE tb_claim_application (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '认领申请主键',
  claim_no VARCHAR(64) NOT NULL COMMENT '认领申请编号',
  application_type VARCHAR(32) NOT NULL COMMENT '申请类型：lost_claim_found/found_claim_lost',
  lost_item_id BIGINT DEFAULT NULL COMMENT '关联失物ID',
  found_item_id BIGINT DEFAULT NULL COMMENT '关联招领ID',
  target_title VARCHAR(128) NOT NULL COMMENT '目标物品标题',
  applicant_user_id BIGINT DEFAULT NULL COMMENT '申请人用户ID',
  applicant_name VARCHAR(64) NOT NULL COMMENT '申请人姓名',
  applicant_student_id VARCHAR(64) DEFAULT NULL COMMENT '申请人学号',
  applicant_phone VARCHAR(32) DEFAULT NULL COMMENT '申请人电话',
  evidence_text TEXT COMMENT '文字证据',
  evidence_images TEXT COMMENT '图片证据地址',
  match_score INT NOT NULL DEFAULT 0 COMMENT '匹配分值',
  reason_summary VARCHAR(1000) DEFAULT NULL COMMENT '匹配理由摘要',
  status VARCHAR(16) NOT NULL DEFAULT 'pending' COMMENT '状态：pending/approved/rejected',
  reviewer_id BIGINT DEFAULT NULL COMMENT '审核人ID',
  review_note VARCHAR(1000) DEFAULT NULL COMMENT '审核说明',
  reviewed_at DATETIME DEFAULT NULL COMMENT '审核时间',
  submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_claim_no (claim_no),
  KEY idx_claim_lost_status (lost_item_id, status),
  KEY idx_claim_found_status (found_item_id, status),
  KEY idx_claim_applicant_status (applicant_user_id, status),
  KEY idx_claim_submitted_at (submitted_at),
  CONSTRAINT fk_claim_applicant FOREIGN KEY (applicant_user_id) REFERENCES tb_user (id),
  CONSTRAINT fk_claim_reviewer FOREIGN KEY (reviewer_id) REFERENCES tb_user (id),
  CONSTRAINT fk_claim_lost_item FOREIGN KEY (lost_item_id) REFERENCES tb_lost_item (id),
  CONSTRAINT fk_claim_found_item FOREIGN KEY (found_item_id) REFERENCES tb_found_item (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='认领申请表';

CREATE TABLE tb_notification_record (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知主键',
  user_id BIGINT NOT NULL COMMENT '接收用户ID',
  biz_type VARCHAR(32) NOT NULL COMMENT '业务类型：claim/system/notice',
  biz_id BIGINT DEFAULT NULL COMMENT '业务主键ID',
  channel VARCHAR(32) NOT NULL DEFAULT 'site' COMMENT '通知通道：site/sms/wechat',
  title VARCHAR(128) NOT NULL COMMENT '通知标题',
  content TEXT NOT NULL COMMENT '通知内容',
  is_read TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  read_at DATETIME DEFAULT NULL COMMENT '已读时间',
  send_status VARCHAR(16) NOT NULL DEFAULT 'pending' COMMENT '发送状态：pending/sent/failed',
  error_message VARCHAR(512) DEFAULT NULL COMMENT '失败信息',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY idx_notification_user_read_time (user_id, is_read, created_at),
  KEY idx_notification_biz (biz_type, biz_id),
  CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES tb_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知记录表';

CREATE TABLE tb_notice (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '公告主键',
  title VARCHAR(128) NOT NULL COMMENT '公告标题',
  content TEXT NOT NULL COMMENT '公告内容',
  notice_type VARCHAR(32) NOT NULL DEFAULT 'system' COMMENT '公告类型',
  scope VARCHAR(32) NOT NULL DEFAULT 'all' COMMENT '作用范围',
  priority INT NOT NULL DEFAULT 1 COMMENT '优先级',
  status VARCHAR(16) NOT NULL DEFAULT 'draft' COMMENT '状态：draft/published/expired',
  publish_at DATETIME DEFAULT NULL COMMENT '发布时间',
  expire_at DATETIME DEFAULT NULL COMMENT '失效时间',
  created_by BIGINT DEFAULT NULL COMMENT '创建人ID',
  updated_by BIGINT DEFAULT NULL COMMENT '更新人ID',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_notice_status_time (status, publish_at),
  KEY idx_notice_scope_priority (scope, priority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统公告表';
CREATE TABLE tb_hotspot (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '热点主键',
  name VARCHAR(128) NOT NULL COMMENT '热点名称',
  point_type VARCHAR(16) NOT NULL COMMENT '点位类型：teaching/facility/dorm',
  x INT NOT NULL COMMENT '地图横坐标',
  y INT NOT NULL COMMENT '地图纵坐标',
  weight INT NOT NULL DEFAULT 0 COMMENT '热点权重',
  pickups INT NOT NULL DEFAULT 0 COMMENT '关联拾取次数',
  peak_time VARCHAR(32) NOT NULL COMMENT '高峰时段',
  source_date DATE DEFAULT NULL COMMENT '统计日期',
  PRIMARY KEY (id),
  KEY idx_hotspot_date_type (source_date, point_type),
  KEY idx_hotspot_weight (weight)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='校园热点图点位表';

SET FOREIGN_KEY_CHECKS = 1;

-- 可选：初始化管理员账号（若使用 BCrypt，请将 password_hash 替换为真实哈希）
-- INSERT INTO tb_user
-- (username, password_hash, phone, email, student_id, real_name, nickname, gender, college, major, grade, role, status, credit_score)
-- VALUES
-- ('admin', '123456', '13800138000', 'admin@csuft.edu.cn', 'admin', '系统管理员', '系统管理员', 'unknown', '信息中心', '系统运维', 'N/A', 'admin', 'active', 120);
