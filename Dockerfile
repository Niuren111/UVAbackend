# 使用 OpenJDK 11 作为基础镜像
FROM openjdk:11-jdk

# 安装 Node.js
RUN apt-get update && \
    apt-get install -y curl && \
    curl -sL https://deb.nodesource.com/setup_16.x | bash - && \
    apt-get install -y nodejs && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置和项目文件
COPY pom.xml .
COPY src ./src

# 复制前端项目文件
COPY package.json .
COPY package-lock.json .
COPY tsconfig.json .
COPY tsconfig.node.json .
COPY index.html .
COPY vite.config.js* ./
COPY public ./public
COPY .env* ./

# 安装依赖并构建项目
RUN npm install
RUN npm run build

# 使用 Maven 构建项目
RUN ./mvnw clean package -DskipTests

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "target/*.jar"] 