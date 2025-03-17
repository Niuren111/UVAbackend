-- 创建数据库
CREATE DATABASE IF NOT EXISTS drone_home DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE drone_home;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    status TINYINT DEFAULT 0 COMMENT '状态：0-正常，1-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    UNIQUE KEY uk_username (username)
) COMMENT '用户表';

-- 职位表
CREATE TABLE IF NOT EXISTS job (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    title VARCHAR(100) NOT NULL COMMENT '职位标题',
    company_name VARCHAR(100) NOT NULL COMMENT '公司名称',
    salary VARCHAR(50) COMMENT '薪资',
    location VARCHAR(100) COMMENT '工作地点',
    description TEXT COMMENT '职位描述',
    requirement TEXT COMMENT '任职要求',
    contact VARCHAR(255) COMMENT '联系方式',
    status TINYINT DEFAULT 0 COMMENT '状态：0-招聘中，1-已关闭',
    publisher_id BIGINT NOT NULL COMMENT '发布者ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    KEY idx_publisher_id (publisher_id)
) COMMENT '职位表';

-- 任务表
CREATE TABLE IF NOT EXISTS task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    title VARCHAR(100) NOT NULL COMMENT '任务标题',
    description TEXT COMMENT '任务描述',
    start_location VARCHAR(100) NOT NULL COMMENT '起始地点',
    end_location VARCHAR(100) NOT NULL COMMENT '目的地点',
    budget DECIMAL(10,2) COMMENT '预算',
    deadline DATETIME COMMENT '截止时间',
    status TINYINT DEFAULT 0 COMMENT '状态：0-招标中，1-已接单，2-已完成，3-已取消',
    publisher_id BIGINT NOT NULL COMMENT '发布者ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    KEY idx_publisher_id (publisher_id)
) COMMENT '任务表';

-- 培训机构表
CREATE TABLE IF NOT EXISTS training_institution (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '机构名称',
    description TEXT COMMENT '机构描述',
    address VARCHAR(255) COMMENT '地址',
    contact VARCHAR(255) COMMENT '联系方式',
    website VARCHAR(255) COMMENT '网站',
    status TINYINT DEFAULT 0 COMMENT '状态：0-正常，1-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除'
) COMMENT '培训机构表';

-- 低空客运表
CREATE TABLE IF NOT EXISTS low_altitude_passenger (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    route_name VARCHAR(100) NOT NULL COMMENT '航线名称',
    start_location VARCHAR(100) NOT NULL COMMENT '起始地点',
    end_location VARCHAR(100) NOT NULL COMMENT '目的地点',
    price DECIMAL(10,2) COMMENT '票价',
    schedule VARCHAR(255) COMMENT '班次',
    status TINYINT DEFAULT 0 COMMENT '状态：0-正常，1-停运',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除'
) COMMENT '低空客运表';

-- 商城商品表
CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    stock INT NOT NULL COMMENT '库存',
    category VARCHAR(50) COMMENT '分类',
    image VARCHAR(255) COMMENT '图片',
    status TINYINT DEFAULT 0 COMMENT '状态：0-上架，1-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除'
) COMMENT '商城商品表'; 