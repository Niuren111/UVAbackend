# 无人机行业平台部署指南

本文档提供了关于如何在服务器上部署无人机行业平台的详细说明。

## 服务器要求

- 操作系统：Ubuntu 20.04 LTS 或更高版本
- 内存：至少 2GB RAM
- 存储：至少 20GB 可用空间
- 网络：可公网访问，开放80端口和443端口

## 部署步骤

### 1. 准备工作

确保你有以下信息：
- 服务器IP地址
- SSH访问凭据（用户名和密码，或SSH密钥）

### 2. 连接到服务器

使用SSH连接到服务器：

```bash
ssh -i /path/to/your/private/key username@server_ip
```

### 3. 服务器环境配置

将`server-setup.sh`脚本上传到服务器，并执行以下命令设置环境：

```bash
chmod +x server-setup.sh
sudo ./server-setup.sh
```

此脚本将自动安装以下软件：
- Docker
- Docker Compose
- Git
- 其他必要的依赖项

### 4. 部署应用

将项目代码克隆或上传到服务器，然后执行以下命令部署应用：

```bash
cd /app/drone-home
chmod +x deploy-server.sh
./deploy-server.sh
```

此脚本将：
- 拉取最新的代码
- 构建和启动Docker容器
- 设置Nginx反向代理

### 5. 验证部署

部署完成后，使用以下URL访问应用：

- 前端网站：`http://your_server_ip`
- 管理后台：`http://your_server_ip/admin`
- API接口：`http://your_server_ip/api`

## 容器说明

本项目使用Docker Compose部署，包含以下容器：

1. **app**: 运行Java后端应用
2. **db**: MySQL数据库
3. **nginx**: 静态文件服务和反向代理

## 常见问题

### 应用无法启动

检查Docker容器日志：

```bash
docker-compose logs app
```

### 数据库连接问题

检查数据库容器状态：

```bash
docker-compose ps db
docker-compose logs db
```

### 更新应用

要更新应用，只需拉取最新代码并重新运行部署脚本：

```bash
cd /app/drone-home
git pull
./deploy-server.sh
```

## 备份与恢复

### 数据库备份

```bash
docker exec -it drone-home_db_1 mysqldump -u root -p drone_home > backup.sql
```

### 数据库恢复

```bash
cat backup.sql | docker exec -i drone-home_db_1 mysql -u root -p drone_home
``` 