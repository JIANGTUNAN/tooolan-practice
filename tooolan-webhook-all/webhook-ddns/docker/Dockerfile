# 多阶段构建 - 构建阶段
FROM maven:3.9-eclipse-temurin-11 AS builder

WORKDIR /build

# 复制整个项目
COPY . .

# 创建输出目录
RUN mkdir -p /app

# 只构建webhook-ddns模块
RUN cd /build/tooolan-webhook-all/webhook-ddns && \
    mvn clean package -DskipTests && \
    cp target/*.jar /app/application.jar

# 运行阶段 - 使用支持ARM的镜像
FROM eclipse-temurin:11-jre

WORKDIR /app

# 添加非root用户
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# 复制构建好的jar文件
COPY --from=builder /app/application.jar .

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD curl -f http://localhost:8080/api/ip/status || exit 1

# 启动应用
ENTRYPOINT ["java", "-jar", "application.jar"]
