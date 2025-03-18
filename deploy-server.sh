#!/bin/bash

# 停止并删除现有容器
echo "停止并删除现有容器..."
docker-compose down

# 拉取最新代码
echo "拉取最新代码..."
git pull

# 构建并启动容器
echo "构建并启动容器..."
docker-compose up -d --build

# 等待应用启动
echo "等待应用启动..."
sleep 30

# 检查应用状态
echo "检查应用状态..."
docker-compose ps

echo "部署完成！" 