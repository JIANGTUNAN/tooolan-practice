# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

这是一个 Java 编程练习与学习笔记的集合项目,使用 Maven 多模块管理。项目包含两个主要模块:

- **tooolan-ddd-all**: DDD(领域驱动设计)练习模块,基于用户-小组-部门三层业务结构
- **tooolan-webhook-all**: Webhook 相关服务集合

## 技术栈

- **Java**: 21
- **Spring Boot**: 3.5.7
- **构建工具**: Maven
- **数据库**: 支持 PostgreSQL 和 MySQL (DDL 兼容两种数据库)
- **工具库**: Hutool 5.8.34, Lombok, MyBatis/MyBatis-Plus

## 构建命令

```bash
# 构建整个项目
mvn clean install

# 构建特定模块
mvn clean install -pl tooolan-webhook-all/webhook-ddns

# 跳过测试构建
mvn clean package -DskipTests

# 打包单个可执行 JAR (webhook-ddns)
cd tooolan-webhook-all/webhook-ddns
mvn clean package -DskipTests
# 生成的 JAR 位于 target/ 目录
```

## DDD 模块架构

`tooolan-ddd-all` 采用标准四层 DDD 架构:

1. **tooolan-ddd-api** (门面层)
   - API 接口定义
   - DTO 对象
   - 请求响应对象
   - 依赖 app 和 infra 层

2. **tooolan-ddd-app** (应用层)
   - 应用服务
   - 业务编排
   - DTO 转换
   - 依赖 domain 层

3. **tooolan-ddd-domain** (领域层)
   - 实体 (Entity)
   - 值对象 (Value Object)
   - 领域服务 (Domain Service)
   - 仓储接口 (Repository Interface)
   - **零依赖** - 纯业务逻辑

4. **tooolan-ddd-infra** (基础设施层)
   - 仓储实现
   - 数据访问
   - 外部服务集成
   - 数据库脚本位于 `src/main/resources/sql/`

**依赖关系**: api → app → domain ← infra

### 业务领域模型

三层组织结构: 用户 (app_user) → 小组 (team_group) → 部门 (department)

- department: 部门表,支持层级结构 (parent_id)
- team_group: 小组表,归属于部门
- app_user: 用户表,归属于小组

数据库表定义: `tooolan-ddd-all/tooolan-ddd-infra/src/main/resources/sql/table.sql`

## Webhook-DDNS 服务

`tooolan-webhook-all/webhook-ddns` 是一个基于 Uptime Kuma 的 DDNS 动态域名解析服务。

### 工作原理

1. 接收 Uptime Kuma 的 Webhook 通知
2. 根据服务状态自动在主力 IP 和容灾 IP 之间切换
3. 提供当前服务 IP 查询接口

### 核心组件

- `WebhookController`: 接收 Webhook 请求 (`/api/webhook/uptime-kuma`)
- `WebhookService`: 处理服务状态变更和 IP 切换逻辑
- `IpContainer`: 管理 IP 状态和切换
- `DdnsConfig`: 配置主力 IP 和容灾 IP

### 运行服务

```bash
# 本地运行 (需要设置环境变量)
export PRIMARY_IP=1.2.3.4
export BACKUP_IP=5.6.7.8
cd tooolan-webhook-all/webhook-ddns
mvn spring-boot:run

# Docker 运行
cd tooolan-webhook-all/webhook-ddns/docker
docker-compose up -d
```

### 配置

服务配置位于 `src/main/resources/application.yml`:

- `ddns.primary-ip`: 主力 IP (通过环境变量 PRIMARY_IP 配置)
- `ddns.backup-ip`: 容灾 IP (通过环境变量 BACKUP_IP 配置)
- 服务端口: 8080

### API 端点

- `POST /api/webhook/uptime-kuma`: 接收 Uptime Kuma Webhook
- 健康检查: `GET /api/ip/status` (用于 Docker HEALTHCHECK)

### Docker 部署

Docker 配置位于 `docker/` 目录:
- `Dockerfile`: 多阶段构建,生成最小化镜像
- `docker-compose.yml`: 服务编排配置

构建镜像:
```bash
cd tooolan-webhook-all/webhook-ddns/docker
docker-compose build
```

## 开发注意事项

- 所有模块统一使用 Java 21
- DDD 模块的 domain 层保持零依赖原则
- 数据库 DDL 兼容 PostgreSQL 和 MySQL 语法
- webhook-ddns 的 Dockerfile 使用健康检查机制
- 环境变量优先级高于配置文件中的默认值
- Spring Boot 3.x 使用 jakarta.* 包替代 javax.* 包

## WSL 环境下的构建命令

本项目在 WSL (Windows Subsystem for Linux) 环境下开发，JDK 和 Maven 安装在 Windows 上。在 WSL 中执行构建命令时，需要通过 `cmd.exe` 调用 Windows 版本的工具。

### JDK 路径

- JDK 21: `C:\Program Files\Java\jdk-21`
- Maven: `D:\Application\apache-maven-3.9.8`

### Maven 命令模板

```bash
# 编译项目
cmd.exe /c "cd /d D:\project\tooolan-practice && C:\Progra~1\Java\jdk-21\bin\java.exe -cp D:\Application\apache-maven-3.9.8\boot\plexus-classworlds-2.8.0.jar -Dmaven.home=D:\Application\apache-maven-3.9.8 -Dmaven.multiModuleProjectDirectory=D:\project\tooolan-practice -Dclassworlds.conf=D:\Application\apache-maven-3.9.8\bin\m2.conf org.codehaus.plexus.classworlds.launcher.Launcher <maven-args>"

# 示例：编译
cmd.exe /c "cd /d D:\project\tooolan-practice && C:\Progra~1\Java\jdk-21\bin\java.exe -cp D:\Application\apache-maven-3.9.8\boot\plexus-classworlds-2.8.0.jar -Dmaven.home=D:\Application\apache-maven-3.9.8 -Dmaven.multiModuleProjectDirectory=D:\project\tooolan-practice -Dclassworlds.conf=D:\Application\apache-maven-3.9.8\bin\m2.conf org.codehaus.plexus.classworlds.launcher.Launcher clean compile -U -DskipTests"

# 示例：打包
cmd.exe /c "cd /d D:\project\tooolan-practice && C:\Progra~1\Java\jdk-21\bin\java.exe -cp D:\Application\apache-maven-3.9.8\boot\plexus-classworlds-2.8.0.jar -Dmaven.home=D:\Application\apache-maven-3.9.8 -Dmaven.multiModuleProjectDirectory=D:\project\tooolan-practice -Dclassworlds.conf=D:\Application\apache-maven-3.9.8\bin\m2.conf org.codehaus.plexus.classworlds.launcher.Launcher clean package -DskipTests"
```

**重要提示**:
- 必须使用 JDK 21 直接运行 Maven，不能依赖 `mvn.cmd`（它会使用系统默认的 Java 8）
- 路径使用 8.3 短格式（`C:\Progra~1` 代替 `C:\Program Files`）以避免空格问题
