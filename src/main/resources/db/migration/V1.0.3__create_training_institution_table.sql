-- 创建培训机构表
CREATE TABLE IF NOT EXISTS `training_institution` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` varchar(100) NOT NULL COMMENT '机构名称',
    `description` text COMMENT '机构描述',
    `address` varchar(255) NOT NULL COMMENT '机构地址',
    `contact` varchar(50) NOT NULL COMMENT '联系人',
    `license` varchar(255) NOT NULL COMMENT '营业执照',
    `qualification` varchar(255) NOT NULL COMMENT '资质证书',
    `price` decimal(10,2) NOT NULL COMMENT '培训价格',
    `course_info` text COMMENT '课程信息',
    `teacher_info` text COMMENT '教师信息',
    `equipment_info` text COMMENT '设备信息',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-待审核，1-已通过，2-已拒绝，3-已关闭',
    `user_id` bigint(20) NOT NULL COMMENT '创建者ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='培训机构表'; 