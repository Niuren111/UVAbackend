-- 创建低空载人表
CREATE TABLE IF NOT EXISTS `low_altitude_passenger` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title` varchar(100) NOT NULL COMMENT '标题',
    `description` text COMMENT '描述',
    `departure_location` varchar(255) NOT NULL COMMENT '出发地点',
    `destination_location` varchar(255) NOT NULL COMMENT '目的地点',
    `route_info` text COMMENT '路线信息',
    `passenger_capacity` int(11) NOT NULL COMMENT '载客容量',
    `price` decimal(10,2) NOT NULL COMMENT '价格',
    `vehicle_info` text NOT NULL COMMENT '载具信息',
    `pilot_info` text NOT NULL COMMENT '驾驶员信息',
    `safety_measures` text NOT NULL COMMENT '安全措施',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-待审核，1-已通过，2-已拒绝，3-已关闭',
    `user_id` bigint(20) NOT NULL COMMENT '创建者ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='低空载人表'; 