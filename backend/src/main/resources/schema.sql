CREATE TABLE IF NOT EXISTS tb_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(64) NOT NULL UNIQUE,
  password_hash VARCHAR(128) NOT NULL,
  phone VARCHAR(32) UNIQUE,
  email VARCHAR(128),
  student_id VARCHAR(64),
  real_name VARCHAR(64),
  nickname VARCHAR(64) NOT NULL,
  avatar_url VARCHAR(255),
  gender VARCHAR(16),
  college VARCHAR(128),
  major VARCHAR(128),
  grade VARCHAR(32),
  role VARCHAR(16) NOT NULL,
  status VARCHAR(16) NOT NULL,
  credit_score INT NOT NULL DEFAULT 100,
  publish_count INT NOT NULL DEFAULT 0,
  success_return_count INT NOT NULL DEFAULT 0,
  continuous_active_days INT NOT NULL DEFAULT 0,
  last_login_at TIMESTAMP NULL,
  last_login_ip VARCHAR(64),
  remark VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_user_session (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  token VARCHAR(128) NOT NULL UNIQUE,
  issued_at TIMESTAMP NOT NULL,
  expired_at TIMESTAMP NOT NULL,
  last_access_at TIMESTAMP NOT NULL,
  login_ip VARCHAR(64),
  user_agent VARCHAR(255),
  status VARCHAR(16) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

CREATE TABLE IF NOT EXISTS tb_lost_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  lost_code VARCHAR(64) UNIQUE,
  title VARCHAR(128) NOT NULL,
  category VARCHAR(64) NOT NULL,
  sub_category VARCHAR(64),
  description VARCHAR(2048),
  lost_time TIMESTAMP,
  lost_location VARCHAR(255) NOT NULL,
  location_lat DECIMAL(10,7),
  location_lng DECIMAL(10,7),
  contact_phone VARCHAR(32),
  contact_wechat VARCHAR(64),
  image_url VARCHAR(255),
  image_urls VARCHAR(2000),
  ai_tags VARCHAR(2000),
  manual_tags VARCHAR(2000),
  ocr_text VARCHAR(2000),
  brand VARCHAR(64),
  color VARCHAR(64),
  material VARCHAR(64),
  reward_amount DECIMAL(10,2),
  urgency_level VARCHAR(16) NOT NULL DEFAULT 'normal',
  status VARCHAR(32) NOT NULL DEFAULT 'pending_claim',
  review_status VARCHAR(32) NOT NULL DEFAULT 'pending',
  publisher_id BIGINT NOT NULL,
  publisher_name VARCHAR(64),
  published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  withdrawn BOOLEAN NOT NULL DEFAULT FALSE,
  withdrawn_at TIMESTAMP NULL,
  closed_at TIMESTAMP NULL,
  extra_json VARCHAR(4000),
  FOREIGN KEY (publisher_id) REFERENCES tb_user(id)
);

CREATE TABLE IF NOT EXISTS tb_found_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  found_code VARCHAR(64) UNIQUE,
  title VARCHAR(128) NOT NULL,
  category VARCHAR(64) NOT NULL,
  sub_category VARCHAR(64),
  description VARCHAR(2048),
  found_time TIMESTAMP,
  found_location VARCHAR(255) NOT NULL,
  location_lat DECIMAL(10,7),
  location_lng DECIMAL(10,7),
  contact_phone VARCHAR(32),
  contact_wechat VARCHAR(64),
  storage_location VARCHAR(255),
  image_url VARCHAR(255),
  image_urls VARCHAR(2000),
  ai_tags VARCHAR(2000),
  manual_tags VARCHAR(2000),
  ocr_text VARCHAR(2000),
  brand VARCHAR(64),
  color VARCHAR(64),
  material VARCHAR(64),
  pickup_method VARCHAR(64),
  urgency_level VARCHAR(16) NOT NULL DEFAULT 'normal',
  status VARCHAR(32) NOT NULL DEFAULT 'pending_claim',
  review_status VARCHAR(32) NOT NULL DEFAULT 'pending',
  publisher_id BIGINT NOT NULL,
  publisher_name VARCHAR(64),
  published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  withdrawn BOOLEAN NOT NULL DEFAULT FALSE,
  withdrawn_at TIMESTAMP NULL,
  closed_at TIMESTAMP NULL,
  extra_json VARCHAR(4000),
  FOREIGN KEY (publisher_id) REFERENCES tb_user(id)
);

CREATE TABLE IF NOT EXISTS tb_claim_application (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  claim_no VARCHAR(64) UNIQUE,
  application_type VARCHAR(16) NOT NULL,
  lost_item_id BIGINT NULL,
  found_item_id BIGINT NULL,
  target_title VARCHAR(128) NOT NULL,
  applicant_user_id BIGINT NULL,
  applicant_name VARCHAR(64) NOT NULL,
  applicant_student_id VARCHAR(64),
  applicant_phone VARCHAR(32),
  evidence_text VARCHAR(2000),
  evidence_images VARCHAR(2000),
  match_score INT NOT NULL DEFAULT 0,
  reason_summary VARCHAR(1000),
  status VARCHAR(16) NOT NULL,
  reviewer_id BIGINT NULL,
  review_note VARCHAR(1000),
  reviewed_at TIMESTAMP NULL,
  submitted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (applicant_user_id) REFERENCES tb_user(id),
  FOREIGN KEY (reviewer_id) REFERENCES tb_user(id)
);

CREATE TABLE IF NOT EXISTS tb_notification_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  biz_type VARCHAR(32) NOT NULL,
  biz_id BIGINT NULL,
  channel VARCHAR(32) NOT NULL,
  title VARCHAR(128) NOT NULL,
  content VARCHAR(2000) NOT NULL,
  is_read BOOLEAN NOT NULL DEFAULT FALSE,
  read_at TIMESTAMP NULL,
  send_status VARCHAR(16) NOT NULL DEFAULT 'pending',
  error_message VARCHAR(512),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

CREATE TABLE IF NOT EXISTS tb_notice (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(128) NOT NULL,
  content VARCHAR(2000) NOT NULL,
  notice_type VARCHAR(32) NOT NULL DEFAULT 'system',
  scope VARCHAR(32) NOT NULL DEFAULT 'all',
  priority INT NOT NULL DEFAULT 1,
  status VARCHAR(16) NOT NULL,
  publish_at TIMESTAMP NULL,
  expire_at TIMESTAMP NULL,
  created_by BIGINT NULL,
  updated_by BIGINT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_hotspot (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  point_type VARCHAR(16) NOT NULL,
  x INT NOT NULL,
  y INT NOT NULL,
  weight INT NOT NULL,
  pickups INT NOT NULL,
  peak_time VARCHAR(32) NOT NULL,
  source_date DATE
);

CREATE INDEX IF NOT EXISTS idx_lost_status ON tb_lost_item(status);
CREATE INDEX IF NOT EXISTS idx_lost_category ON tb_lost_item(category);
CREATE INDEX IF NOT EXISTS idx_lost_location ON tb_lost_item(lost_location);
CREATE INDEX IF NOT EXISTS idx_found_status ON tb_found_item(status);
CREATE INDEX IF NOT EXISTS idx_found_category ON tb_found_item(category);
CREATE INDEX IF NOT EXISTS idx_found_location ON tb_found_item(found_location);
CREATE INDEX IF NOT EXISTS idx_claim_status ON tb_claim_application(status);
CREATE INDEX IF NOT EXISTS idx_notice_status ON tb_notice(status);
CREATE INDEX IF NOT EXISTS idx_session_user ON tb_user_session(user_id);
