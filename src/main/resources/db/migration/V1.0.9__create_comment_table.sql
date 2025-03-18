-- 创建评论表
CREATE TABLE IF NOT EXISTS `comment` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `product_id` bigint(20) NOT NULL COMMENT '商品ID',
    `order_id` bigint(20) NOT NULL COMMENT '订单ID',
    `content` varchar(500) NOT NULL COMMENT '评论内容',
    `rating` tinyint(4) NOT NULL COMMENT '评分：1-5',
    `images` text COMMENT '图片，多个图片URL以逗号分隔',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-待审核，1-已通过，2-已拒绝',
    `reply_id` bigint(20) DEFAULT NULL COMMENT '回复者ID',
    `reply_content` varchar(500) DEFAULT NULL COMMENT '回复内容',
    `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表'; 