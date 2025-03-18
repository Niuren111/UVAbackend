#!/bin/bash

# 设置服务器信息
SERVER_IP="47.242.73.182"
SERVER_USER="root"
SSH_KEY="D:/Allin"
SERVER_DIR="/app/drone-home"

# 确保服务器目录存在
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_IP" "mkdir -p $SERVER_DIR"

# 上传部署文件
scp -i "$SSH_KEY" Dockerfile docker-compose.yml deploy-server.sh server-setup.sh "$SERVER_USER@$SERVER_IP:$SERVER_DIR/"

# 上传Nginx配置
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_IP" "mkdir -p $SERVER_DIR/nginx/conf.d"
scp -i "$SSH_KEY" nginx/conf.d/default.conf "$SERVER_USER@$SERVER_IP:$SERVER_DIR/nginx/conf.d/"

# 设置脚本执行权限
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_IP" "chmod +x $SERVER_DIR/*.sh"

echo "部署文件已上传到服务器 $SERVER_IP:$SERVER_DIR"
echo "你现在可以SSH连接到服务器并执行以下命令："
echo "cd $SERVER_DIR"
echo "sudo ./server-setup.sh" 