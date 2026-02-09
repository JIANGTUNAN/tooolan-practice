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

## 编码规范

### Java 注释规范

本项目遵循统一的 Java 注释规范，确保代码可读性和可维护性。

**核心原则**：使用简洁的纯文本，不使用 HTML 标签（如 `<p>`、`<ul>` 等）。

#### 类注释（Class Comments）

所有公共类必须包含 Javadoc 类注释，格式如下：

```java
/**
 * 类的简短描述（一句话说明）
 * 类的详细描述，可多行，说明主要功能和职责
 *
 * @author 作者名
 * @since 创建日期（格式：YYYY年M月D日）
 */
public class ExampleClass {
    // ...
}
```

**规则**：
- 第一行：类的简短描述
- 第二行：详细描述（可多行，但不要使用 HTML 标签）
- 最后：`@author` 和 `@since` 标签
- 避免使用无意义的占位符（如 "xxxxx"）

#### 方法注释（Method Comments）

所有公共方法必须包含 Javadoc 方法注释：

```java
/**
 * 方法的简短描述（一句话说明）
 * 方法的详细描述，可多行，说明方法的作用、业务逻辑、注意事项等
 *
 * @param paramName 参数说明
 * @return 返回值说明（void 方法无需此标签）
 * @throws ExceptionType 异常说明（如无异常则省略）
 */
public ExampleResult exampleMethod(ExampleParam param) {
    // ...
}
```

**规则**：
- 第一行：方法的简短描述
- 第二行：详细描述（可多行）
- `@param`：每个参数一行，说明参数用途
- `@return`：说明返回值的含义
- `@throws`：说明可能抛出的异常及原因

#### 字段注释（Field Comments）

重要的字段应包含注释：

```java
/**
 * 字段说明
 */
private ExampleType fieldName;

// 或者对于简单字段，使用单行注释
private int timeout; // 超时时间（秒）
```

#### 代码内联注释（Inline Comments）

复杂的业务逻辑应添加注释说明：

```java
// 计算折扣后的价格
double finalPrice = originalPrice * discountRate;

// 或使用多行注释说明复杂逻辑
/*
 * 这里使用三次重试机制：
 * 1. 第一次：正常请求
 * 2. 第二次：延迟 1 秒后重试
 * 3. 第三次：延迟 3 秒后重试
 * 如果三次都失败，则抛出异常
 */
for (int i = 0; i < MAX_RETRY; i++) {
    // ...
}
```

#### 注释原则

1. **Why 而非 What**：注释应说明"为什么这样做"，而非"做了什么"
2. **保持同步**：代码修改时同步更新注释
3. **避免废话**：如 `i++; // i 加 1` 这种注释是无意义的
4. **中文优先**：注释使用中文，便于团队理解
5. **及时更新**：过时的注释比没有注释更糟糕

#### 注释示例

**好的注释**：
```java
/**
 * 日志级别颜色转换器
 * 为不同日志级别配置柔和的 ANSI 颜色，提升控制台日志可读性
 *
 * 颜色方案：
 * - ERROR: 红色 (31m)
 * - WARN: 黄色 (33m)
 * - INFO: 青色 (36m)
 * - DEBUG: 绿色 (32m)
 * - TRACE: 灰色 (90m)
 *
 * @author tooolan
 * @since 2026年2月9日
 */
public class ColorLevelConverter extends CompositeConverter<ILoggingEvent> {
    // ...
}
```

**不好的注释**：
```java
/**
 * xxxxx
 * xxxxxxxxxxx
 *
 * @author tooolan
 * @since 2026年2月9日
 */
public class BadExample {
    /**
     * xxxxx
     *
     * @param args xxx
     */
    public static void main(String[] args) {
        // ...
    }
}
```

### 日志配置规范

#### 日志级别颜色方案

本项目使用自定义的日志颜色转换器（`ColorLevelConverter`），采用柔和的 ANSI 颜色：

| 日志级别 | 颜色 | ANSI 码 | 说明 |
|---------|------|--------|------|
| ERROR   | 红色 | `[31m`  | 错误信息，醒目但不过分刺眼 |
| WARN    | 黄色 | `[33m`  | 警告信息，需要注意但不致命 |
| INFO    | 青色 | `[36m`  | 一般信息，柔和舒适 |
| DEBUG   | 绿色 | `[32m`  | 调试信息，清爽明亮 |
| TRACE   | 灰色 | `[90m`  | 追踪信息，低调显示 |

#### 日志格式

开发环境日志格式：
```
{时间戳} [{线程名}] {日志级别} [{类名}] {消息}
```

示例：
```
17:49:08.021 [main] INFO  [o.s.b.StartupInfoLogger] Starting DddApplication using Java 21.0.10
```

#### MyBatis 日志配置

**重要**：MyBatis 必须使用 SLF4J 日志框架，而非 StdOutImpl：

```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl  # ✅ 正确
    # log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # ❌ 错误
```

**原因**：
- SLF4J 与项目日志配置统一，支持自定义颜色
- StdOutImpl 直接输出到 System.out，绕过了日志框架
- SLF4J 支持日志级别控制和文件输出

#### 日志级别配置

在 `logback-spring.xml` 中配置日志级别：

```xml
<!-- Spring Boot 框架日志 -->
<logger name="org.springframework" level="INFO"/>

<!-- MyBatis Plus 框架日志 -->
<logger name="com.baomidou.mybatisplus" level="INFO"/>

<!-- MyBatis SQL 日志 -->
<logger name="org.mybatis" level="INFO"/>

<!-- 项目业务日志 -->
<logger name="com.tooolan.practice" level="DEBUG"/>
```

**日志级别选择建议**：
- **ERROR**：需要立即处理的错误
- **WARN**：潜在问题，需要关注但不影响运行
- **INFO**：重要的业务流程节点
- **DEBUG**：调试信息，生产环境关闭
- **TRACE**：详细的执行轨迹，仅开发调试使用
