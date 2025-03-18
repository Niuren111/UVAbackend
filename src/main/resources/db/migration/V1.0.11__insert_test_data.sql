-- 插入测试用户数据
INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `avatar`, `role`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES
(1, 'admin', '$2a$10$X/XuYW8CKQzp.0yM9aFl3.ZGRBUoHy3ZIJdFjkFJ7fwA.xMqY0oCe', 'admin@dronehome.com', '13800000000', 'https://example.com/avatar/admin.jpg', 'ROLE_ADMIN', 1, NOW(), NOW(), 0),
(2, 'user1', '$2a$10$X/XuYW8CKQzp.0yM9aFl3.ZGRBUoHy3ZIJdFjkFJ7fwA.xMqY0oCe', 'user1@dronehome.com', '13800000001', 'https://example.com/avatar/user1.jpg', 'ROLE_USER', 1, NOW(), NOW(), 0),
(3, 'user2', '$2a$10$X/XuYW8CKQzp.0yM9aFl3.ZGRBUoHy3ZIJdFjkFJ7fwA.xMqY0oCe', 'user2@dronehome.com', '13800000002', 'https://example.com/avatar/user2.jpg', 'ROLE_USER', 1, NOW(), NOW(), 0),
(4, 'seller1', '$2a$10$X/XuYW8CKQzp.0yM9aFl3.ZGRBUoHy3ZIJdFjkFJ7fwA.xMqY0oCe', 'seller1@dronehome.com', '13800000003', 'https://example.com/avatar/seller1.jpg', 'ROLE_SELLER', 1, NOW(), NOW(), 0);

-- 插入测试商品数据
INSERT INTO `product` (`id`, `name`, `description`, `category_id`, `category_name`, `brand_id`, `brand_name`, `price`, `original_price`, `main_image`, `images`, `detail`, `specs`, `stock`, `sales`, `rating`, `comment_count`, `status`, `seller_id`, `seller_name`, `create_time`, `update_time`, `is_deleted`) VALUES
(1, '大疆 Mavic 3 Pro 无人机', '大疆最新旗舰级无人机，搭载哈苏相机', 1, '消费级无人机', 1, '大疆', 13999.00, 14999.00, 'https://example.com/images/mavic3pro.jpg', 'https://example.com/images/mavic3pro_1.jpg,https://example.com/images/mavic3pro_2.jpg,https://example.com/images/mavic3pro_3.jpg', '<p>大疆 Mavic 3 Pro 是一款旗舰级无人机，搭载哈苏相机，支持4K高清视频拍摄，续航时间长达46分钟。</p>', '{"重量":"895g","最大飞行时间":"46分钟","最大飞行距离":"30km","相机":"哈苏相机，4/3 CMOS"}', 100, 50, 4.8, 10, 1, 4, 'seller1', NOW(), NOW(), 0),
(2, '大疆 Mini 3 Pro 无人机', '轻量便携的高性能无人机', 1, '消费级无人机', 1, '大疆', 4999.00, 5499.00, 'https://example.com/images/mini3pro.jpg', 'https://example.com/images/mini3pro_1.jpg,https://example.com/images/mini3pro_2.jpg', '<p>大疆 Mini 3 Pro 是一款轻量便携的高性能无人机，重量仅249g，支持4K高清视频拍摄，续航时间长达34分钟。</p>', '{"重量":"249g","最大飞行时间":"34分钟","最大飞行距离":"18km","相机":"1/1.3英寸CMOS"}', 200, 100, 4.7, 15, 1, 4, 'seller1', NOW(), NOW(), 0),
(3, '大疆 FPV 穿越机', '沉浸式飞行体验的FPV无人机', 2, 'FPV穿越机', 1, '大疆', 7999.00, 8499.00, 'https://example.com/images/fpv.jpg', 'https://example.com/images/fpv_1.jpg,https://example.com/images/fpv_2.jpg', '<p>大疆 FPV 穿越机提供沉浸式飞行体验，支持4K高清视频拍摄，最高速度可达140km/h。</p>', '{"重量":"795g","最大飞行时间":"20分钟","最高速度":"140km/h","相机":"1/2.3英寸CMOS"}', 50, 20, 4.6, 8, 1, 4, 'seller1', NOW(), NOW(), 0);

-- 插入测试订单数据
INSERT INTO `order` (`id`, `order_no`, `user_id`, `product_id`, `product_name`, `product_image`, `quantity`, `price`, `total_amount`, `status`, `payment_time`, `delivery_time`, `completion_time`, `create_time`, `update_time`, `is_deleted`) VALUES
(1, '202403210001', 2, 1, '大疆 Mavic 3 Pro 无人机', 'https://example.com/images/mavic3pro.jpg', 1, 13999.00, 13999.00, 3, '2024-03-21 10:00:00', '2024-03-22 10:00:00', '2024-03-25 10:00:00', '2024-03-21 09:00:00', '2024-03-25 10:00:00', 0),
(2, '202403220001', 3, 2, '大疆 Mini 3 Pro 无人机', 'https://example.com/images/mini3pro.jpg', 1, 4999.00, 4999.00, 3, '2024-03-22 10:00:00', '2024-03-23 10:00:00', '2024-03-26 10:00:00', '2024-03-22 09:00:00', '2024-03-26 10:00:00', 0),
(3, '202403230001', 2, 3, '大疆 FPV 穿越机', 'https://example.com/images/fpv.jpg', 1, 7999.00, 7999.00, 3, '2024-03-23 10:00:00', '2024-03-24 10:00:00', '2024-03-27 10:00:00', '2024-03-23 09:00:00', '2024-03-27 10:00:00', 0);

-- 插入测试评论数据
INSERT INTO `comment` (`id`, `user_id`, `product_id`, `order_id`, `content`, `rating`, `images`, `status`, `reply_id`, `reply_content`, `reply_time`, `create_time`, `update_time`, `is_deleted`) VALUES
(1, 2, 1, 1, '无人机质量非常好，拍摄效果出色，续航也很长，非常满意！', 5, 'https://example.com/comment/image1.jpg,https://example.com/comment/image2.jpg', 1, 1, '感谢您的支持和好评，我们会继续努力提供更好的产品和服务！', '2024-03-26 10:00:00', '2024-03-25 15:00:00', '2024-03-26 10:00:00', 0),
(2, 3, 2, 2, '小巧便携，携带方便，拍摄效果也很不错，性价比高！', 4, 'https://example.com/comment/image3.jpg', 1, NULL, NULL, NULL, '2024-03-26 16:00:00', '2024-03-26 16:00:00', 0),
(3, 2, 3, 3, 'FPV飞行体验太棒了，沉浸感很强，操控性也很好，就是电池续航时间有点短。', 4, NULL, 1, 1, '感谢您的反馈，我们会在下一代产品中改进电池续航问题。', '2024-03-28 10:00:00', '2024-03-27 17:00:00', '2024-03-28 10:00:00', 0); 