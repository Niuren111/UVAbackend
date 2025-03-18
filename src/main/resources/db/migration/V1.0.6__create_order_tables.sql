-- 创建订单表
CREATE TABLE IF NOT EXISTS `order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no` varchar(32) NOT NULL COMMENT '订单编号',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消',
    `receiver_name` varchar(50) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
    `receiver_address` varchar(255) NOT NULL COMMENT '收货地址',
    `payment_method` varchar(20) DEFAULT NULL COMMENT '支付方式',
    `payment_no` varchar(50) DEFAULT NULL COMMENT '支付流水号',
    `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
    `delivery_company` varchar(50) DEFAULT NULL COMMENT '物流公司',
    `delivery_no` varchar(50) DEFAULT NULL COMMENT '物流单号',
    `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
    `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
    `remark` varchar(255) DEFAULT NULL COMMENT '订单备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 创建订单项表
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_id` bigint(20) NOT NULL COMMENT '订单ID',
    `order_no` varchar(32) NOT NULL COMMENT '订单编号',
    `product_id` bigint(20) NOT NULL COMMENT '商品ID',
    `product_name` varchar(100) NOT NULL COMMENT '商品名称',
    `product_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
    `product_price` decimal(10,2) NOT NULL COMMENT '商品单价',
    `quantity` int(11) NOT NULL COMMENT '购买数量',
    `total_price` decimal(10,2) NOT NULL COMMENT '商品总价',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_order_no` (`order_no`),
    KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表'; 