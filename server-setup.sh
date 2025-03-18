#!/bin/bash

# 更新软件包
echo "更新软件包..."
apt-get update
apt-get upgrade -y

# 安装必要的软件
echo "安装必要的软件..."
apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    git

# 安装Docker
echo "安装Docker..."
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh
usermod -aG docker $USER

# 安装Docker Compose
echo "安装Docker Compose..."
curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# 创建应用目录
echo "创建应用目录..."
mkdir -p /app/drone-home
cd /app/drone-home

# 克隆项目代码（如果需要）
echo "克隆项目代码..."
git clone https://github.com/niuren111/UVAbackend.git .

# 设置防火墙
echo "设置防火墙..."
ufw allow 22
ufw allow 80
ufw allow 443
ufw allow 8080
ufw --force enable

echo "服务器配置完成！"
echo "现在你可以使用 deploy-server.sh 脚本来部署应用。" 