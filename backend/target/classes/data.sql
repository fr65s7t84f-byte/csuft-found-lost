MERGE INTO tb_user KEY(id) VALUES
(1, 'admin', '123456', '13800138000', 'admin@csuft.edu.cn', 'admin', '系统管理员', '系统管理员', '', 'unknown', '信息中心', '系统运维', 'N/A', 'admin', 'active', 120, 0, 0, 0, CURRENT_TIMESTAMP, '127.0.0.1', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'xq', '123456', '18173323763', 'xq@csuft.edu.cn', '20223590', '肖强', '肖强', '', 'male', '计算机与数学学院', '软件工程', '2022', 'user', 'active', 168, 22, 16, 47, CURRENT_TIMESTAMP, '127.0.0.1', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'wang', '123456', '13900001234', 'wang@csuft.edu.cn', '20224567', '王同学', '王同学', '', 'female', '风景园林学院', '风景园林', '2022', 'user', 'frozen', 92, 11, 4, 3, CURRENT_TIMESTAMP, '127.0.0.1', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'li', '123456', '13900005678', 'li@csuft.edu.cn', '20229876', '李四', '李四', '', 'male', '林学院', '林学', '2022', 'user', 'active', 136, 14, 10, 19, CURRENT_TIMESTAMP, '127.0.0.1', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO tb_lost_item KEY(id) VALUES
(1, 'L2026040001', '蓝色帆布书包', '书包', '双肩包', '包内有实验资料和U盘', DATEADD('HOUR', -8, CURRENT_TIMESTAMP), '图书馆', NULL, NULL, '18173323763', '', 'https://dummyimage.com/120x120/e8eef5/3b5368.png&text=LOST-1001', '', '书包,蓝色,帆布,U盘', '书包,蓝色', 'CSUFT', 'unknown', '蓝色', '帆布', 0, 'high', 'pending_claim', 'pending', 2, '肖强', DATEADD('HOUR', -6, CURRENT_TIMESTAMP), FALSE, NULL, NULL, ''),
(2, 'L2026040002', '黑色圆框眼镜', '眼镜', '圆框', '黑色框眼镜', DATEADD('HOUR', -12, CURRENT_TIMESTAMP), '教学楼A区', NULL, NULL, '13900001234', '', 'https://dummyimage.com/120x120/e8eef5/3b5368.png&text=LOST-1002', '', '眼镜,黑框,圆形', '眼镜,黑色', '', 'unknown', '黑色', '树脂', 0, 'normal', 'pending_claim', 'approved', 3, '王同学', DATEADD('HOUR', -10, CURRENT_TIMESTAMP), FALSE, NULL, NULL, '');

MERGE INTO tb_found_item KEY(id) VALUES
(1, 'F2026040001', '银色保温杯', '生活用品', '杯具', '图书馆一楼拾到保温杯', DATEADD('HOUR', -7, CURRENT_TIMESTAMP), '图书馆', NULL, NULL, '18173323763', '', '图书馆服务台', 'https://dummyimage.com/120x120/e8eef5/3b5368.png&text=FOUND-2001', '', '保温杯,银色', '保温杯', '', 'unknown', '银色', '金属', '线下认领', 'normal', 'pending_claim', 'approved', 2, '肖强', DATEADD('HOUR', -5, CURRENT_TIMESTAMP), FALSE, NULL, NULL, ''),
(2, 'F2026040002', '黑色雨伞', '生活用品', '雨具', '食堂二楼靠窗座位拾到雨伞', DATEADD('DAY', -1, CURRENT_TIMESTAMP), '食堂二楼', NULL, NULL, '18173323763', '', '食堂失物点', 'https://dummyimage.com/120x120/e8eef5/3b5368.png&text=FOUND-2003', '', '雨伞,黑色', '雨伞,黑色', '', 'unknown', '黑色', '涤纶', '线下认领', 'normal', 'pending_claim', 'pending', 2, '肖强', DATEADD('DAY', -1, CURRENT_TIMESTAMP), FALSE, NULL, NULL, '');

MERGE INTO tb_claim_application KEY(id) VALUES
(1, 'C2026040001', 'found_claim_lost', 1, 1, '蓝色帆布书包', 4, '张三', '20221234', '13912341234', '包里有实验报告和银色U盘', '', 89, '类别一致、地点接近、标签重合', 'pending', NULL, '', NULL, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP),
(2, 'C2026040002', 'lost_claim_found', NULL, 2, '黑色雨伞', 4, '李四', '20229876', '13900005678', '雨伞柄有白色划痕', '', 78, '标题和标签匹配', 'approved', 1, '信息核验通过', DATEADD('HOUR', -12, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP);

MERGE INTO tb_notice KEY(id) VALUES
(1, '开学季防丢提醒', '请妥善保管个人贵重物品。', 'safety', 'all', 2, 'published', DATEADD('DAY', -1, CURRENT_TIMESTAMP), NULL, 1, 1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
(2, '系统升级通知', '周日 2:00-4:00 升级维护。', 'system', 'all', 1, 'draft', NULL, NULL, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO tb_hotspot KEY(id) VALUES
(1, '求是楼', 'teaching', 38, 37, 95, 29, '11:40-13:10', CURRENT_DATE),
(2, '崇德楼', 'teaching', 45, 40, 88, 24, '13:30-15:00', CURRENT_DATE),
(3, '致知楼', 'teaching', 53, 34, 72, 18, '16:50-18:00', CURRENT_DATE),
(4, '新图书馆', 'facility', 61, 44, 84, 22, '20:00-21:40', CURRENT_DATE),
(5, '大学生活动中心', 'facility', 66, 56, 63, 14, '18:00-20:00', CURRENT_DATE),
(6, '体艺馆', 'facility', 73, 48, 67, 15, '17:00-19:00', CURRENT_DATE),
(7, '体育场', 'facility', 78, 60, 58, 11, '19:00-21:00', CURRENT_DATE),
(8, '学生宿舍北区', 'dorm', 31, 60, 76, 20, '22:00-23:00', CURRENT_DATE),
(9, '学生宿舍南区', 'dorm', 25, 70, 69, 17, '21:30-23:00', CURRENT_DATE),
(10, '第一食堂', 'facility', 46, 57, 90, 26, '11:30-13:00', CURRENT_DATE);
MERGE INTO tb_notification_record KEY(id) VALUES
(1, 2, 'system', NULL, 'site', '欢迎使用系统', '欢迎使用校园智能失物招领系统，后续认领审核和状态变化会在这里通知。', FALSE, NULL, 'sent', '', CURRENT_TIMESTAMP),
(2, 4, 'claim', 2, 'site', '认领审核结果', '您提交的黑色雨伞认领申请已审核通过，请联系发布人线下交接。', FALSE, NULL, 'sent', '', CURRENT_TIMESTAMP);