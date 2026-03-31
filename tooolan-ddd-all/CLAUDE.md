# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

`tooolan-ddd-all` 是一个基于领域驱动设计（DDD）架构的 Spring Boot 3 练习项目，实现了用户-小组-部门三层业务结构。

**技术栈**：Spring Boot 3.5.8 + MyBatis Plus 3.5.15 + MySQL + Java 21

## 常用命令

### 构建与运行

**重要**：由于项目在 WSL 环境中运行，构建时必须使用 `cmd.exe /c` 调用 Windows 下的 Maven，避免 WSL 环境兼容性问题。

```bash
# 完整构建（从父项目目录）
cmd.exe /c "mvn clean install"

# 仅构建当前模块
cmd.exe /c "mvn clean install"

# 运行应用（开发环境）
cmd.exe /c "mvn spring-boot:run"

# 打包应用
cmd.exe /c "mvn clean package"

# 运行打包后的 JAR
cmd.exe /c "java -jar target\tooolan-ddd-start-1.0-SNAPSHOT.jar"
```

### Maven 依赖说明

**重要**：本项目 `tooolan-ddd-all/pom.xml` 独立管理所有依赖版本（使用 BOM import 方式，不继承外部 parent），包括：
- Spring Boot 3.5.8
- MyBatis Plus 3.5.15
- Hutool 5.8.43
- Lombok 1.18.42
- MySQL Connector 8.0.33
- Sa-Token 1.39.0（权限认证）
- Spring Security Crypto 6.4.2（密码加密）
- Transmittable ThreadLocal 2.14.5（阿里 TTL，线程池上下文传递）

子模块无需指定版本号，直接引用依赖即可。

## DDD 分层架构

项目遵循严格的 DDD 四层架构（不含启动层），模块依赖关系：

```
tooolan-ddd-start（启动层）
    ↓
tooolan-ddd-api（门面层，依赖 app）
    ↓
tooolan-ddd-app（应用层，依赖 domain）
    ↓

tooolan-ddd-infra（基础设施层，依赖 domain）
    ↓
tooolan-ddd-domain（领域层，无业务层依赖）
```

### 模块职责

| 模块 | 职责 | 关键内容 |
|------|------|----------|
| **tooolan-ddd-api** | 门面层 | API 接口定义、DTO 对象、请求响应对象 |
| **tooolan-ddd-app** | 应用层 | 应用服务、业务编排、DTO 转换 |
| **tooolan-ddd-domain** | 领域层 | 实体、值对象、领域服务、仓储接口 |
| **tooolan-ddd-infra** | 基础设施层 | 仓储实现、数据访问、MyBatis Plus Mapper |
| **tooolan-ddd-start** | 启动层 | Spring Boot 应用入口，整合所有层 |

### 依赖规则

- **start** 依赖 api 和 infra
- **api** 依赖 app（Controller 直接注入 ApplicationService）
- **app** 依赖 domain（编排领域服务）
- **infra** 依赖 domain（实现仓储接口）
- **domain** 不依赖任何业务层（纯粹的核心业务逻辑）

## 代码组织规范

### 包结构规范

每个模块遵循统一的包结构约定：

```
com.tooolan.ddd.{module}
├── common/        # 通用组件（infra 模块）
│   ├── config/    # 配置类
│   ├── entity/    # 基础实体
│   ├── context/   # 上下文管理
│   └── enums/     # 枚举定义
├── persistence/   # 数据持久化（infra 模块）
│   ├── {domain}/  # 按业务域划分
│   │   ├── entity/    # 实体类
│   │   ├── mapper/    # MyBatis Mapper
│   │   └── converter/ # 转换器
└── resources/
    ├── mapper/    # MyBatis XML 映射文件
    └── sql/       # 数据库脚本
```

### 实体类命名规范

- **基础设施层实体**：`Sys{业务名}Entity`（如 `SysUserEntity`）
- **表名映射**：`sys_{业务名}`（如 `sys_user`）
- **主键字段**：`{业务名}Id`（如 `userId`）
- **业务编码**：`{业务名}Code`（如 `userCode`）

### 基础设施层核心类

| 类                     | 路径                       | 作用                                           |
|-----------------------|--------------------------|----------------------------------------------|
| `BaseEntity`          | `infra/common/entity/`   | 所有实体基类，提供审计字段                                |
| `MyMetaObjectHandler` | `infra/common/config/`   | 自动填充 createdBy/createdAt/updatedBy/updatedAt |
| `MyBatisPlusConfig`   | `infra/common/config/`   | 分页插件配置，Mapper 扫描                             |
| `SecurityContextImpl` | `infra/common/security/` | 安全上下文实现，存储当前用户信息                             |

## 配置文件说明

### 环境配置

- **application.yml**：主配置文件，定义通用配置（端口 8080，Context Path `/api`）
- **logback-spring.xml**：日志配置，支持彩色输出和文件滚动（使用 `com.tooolan.practice` 包名）

### 数据库配置

开发环境数据库连接：

- **地址**：`192.168.31.5:3306`
- **数据库**：`practice-ddd`
- **字符集**：utf8mb4

### Sa-Token 配置

- **Token 名称**：`Authorization`
- **Token 有效期**：30 天（永不过线模式）
- **并发登录**：允许多账号同端登录
- **鉴权方法**：使用 `@SaCheckLogin` 注解，忽略登录校验路径 `/session/login`

### MyBatis Plus 配置要点

- ID 生成策略：`AUTO`（数据库自增）
- 逻辑删除字段：`deleted`（true=已删除，false=未删除）
- 驼峰命名转换：已启用
- Mapper XML 位置：`classpath*:mapper/**/*Mapper.xml`

## 业务域模型

当前实现三个核心业务域，形成三层嵌套结构：

```
部门（SysDept）
  └─ 小组（SysTeam）
       └─ 用户（SysUser）
```

### 业务关系

1. **部门**：支持层级结构（`parentId` 字段），部门编码全局唯一
2. **小组**：归属于部门（`deptId`），小组编码全局唯一
3. **用户**：归属于小组（`teamId`），用户账户全局唯一

### 数据库脚本

初始化脚本位于：`tooolan-ddd-infra/src/main/resources/sql/practice-ddd.sql`

### 小组状态枚举

**关键文件**：`tooolan-ddd-domain/src/main/java/com/tooolan/ddd/domain/team/enums/TeamStatusEnum.java`

- `NORMAL(0)`：正常
- `DISABLED(1)`：停用
- `FULL(2)`：满员

### 日志操作模块

**关键文件**：`tooolan-ddd-domain/src/main/java/com/tooolan/ddd/domain/log/constant/`

**操作模块**（`LogOpModule`）：

- `USER`：用户操作
- `TEAM`：小组操作
- `DEPT`：部门操作
- `SESSION`：会话操作

**操作类型**（`LogOpType`）：

- `CREATE`：创建
- `UPDATE`：更新
- `DELETE`：删除
- `LOGIN`：登录
- `LOGOUT`：登出
- `MOVE`：移动（部门层级调整）
- `TRANSFER`：转移（用户变更小组）

## 核心架构特性

### 依赖倒置实践

**关键文件**：`tooolan-ddd-app/src/main/java/com/tooolan/ddd/app/common/config/DomainServiceConfig.java`

- 领域层使用自定义 `@DomainService` 注解，不依赖 Spring
- 应用层通过 `DomainServiceConfig` 告诉 Spring 如何扫描和注册领域服务
- 实现依赖倒置：领域层接口（如 `Repository`）定义在 domain 层，实现在 infra 层

**注解定义**：`tooolan-ddd-domain/src/main/java/com/tooolan/ddd/domain/common/annotation/DomainService.java`

### 事件驱动架构

**领域事件**：

- `UserCreatedEvent`：用户创建事件
- `UserUpdatedEvent`：用户更新事件
- `UserDeletedEvent`：用户删除事件
- `UserLoginEvent`：用户登录事件

**事件发布**：

```java
eventPublisher.publishEvent(UserCreatedEvent.of(user, bo));
```

**事件监听器**：`tooolan-ddd-app/src/main/java/com/tooolan/ddd/app/log/listener/LogEventListener.java`

- 处理领域事件，记录操作日志
- 使用 `@EventListener` 注解监听事件
- 异步执行，避免影响主流程性能

### 字段清空机制

**关键文件**：`tooolan-ddd-domain/src/main/java/com/tooolan/ddd/domain/common/constant/FieldClearValues.java`

- **清空约定值**：字符串 `"_clear"`、整数 `-1`
- **处理方法**：`FieldClearValues.processField()` 静态方法
- **应用场景**：`UserApplicationService.processClearFields()` 支持清空 `email`、`teamId`、`remark`

**使用示例**：

```java
// 前端传递 "_clear" 表示清空可选字段
FieldClearValues.processField(user::setEmail, email);
```

### 上下文管理

**关键文件**：

- `tooolan-ddd-domain/src/main/java/com/tooolan/ddd/domain/common/context/ContextHolder.java`
- `tooolan-ddd-api/src/main/java/com/tooolan/ddd/api/common/web/ContextInterceptor.java`

**核心特性**：

- 基于 `Transmittable ThreadLocal` 实现上下文传递（支持线程池、异步场景）
- **HTTP 上下文快照**：requestUri、method、clientIp、token、headers、params
- **拦截器职责**：登录校验、上下文初始化、上下文清理
- **系统上下文**：`initSystemContext()` 用于定时任务等无 HTTP 上下文场景

**上下文类型**：

- `HttpContext`：HTTP 请求上下文
- `SecurityContext`：安全上下文（当前用户信息）

### 分页空对象模式

**关键文件**：`tooolan-ddd-app/src/main/java/com/tooolan/ddd/app/common/request/PageVo.java`

- **统一方法**：`PageVo.empty()`
- **转换器中使用**：`if (result == null) return PageVo.empty();`
- **提取方法**：`PageQueryBo.createEmptyPage()`（避免重复创建空对象）

**使用示例**：

```java
PageQueryBo<UserVo> bo = PageQueryBo.createEmptyPage();
```

## 开发注意事项

1. **中文注释**：代码注释和文档使用中文编写
2. **审计字段**：所有表必须包含 `createdBy/createdAt/updatedBy/updatedAt`，由 `MyMetaObjectHandler` 自动填充
3. **逻辑删除**：所有表必须包含 `deleted` 字段，使用 `DeletedStatusEnum` 枚举
4. **Mapper 接口**：继承 `BaseMapper<Sys{Xxx}Entity>`，无需编写基础 CRUD 方法
5. **分页查询**：使用 `Page<Sys{Xxx}Entity>` 配合 `Page<T>` 对象
6. **唯一索引**：业务编码字段（如 `userCode`、`teamCode`）必须建立唯一索引

### 异常消息规范

**异常消息必须面向用户，禁止暴露技术细节**

- ❌ 错误示例：`无效的小组状态值: 9，小组ID: 123`
- ✅ 正确示例：`小组状态异常，请联系管理员`
- ❌ 错误示例：`SQL执行失败: Column 'user_name' doesn't exist`
- ✅ 正确示例：`系统繁忙，请稍后再试`

**原则**：

- 不出现数据库字段名、表名、技术术语
- 不暴露内部实现细节（如 ID、堆栈信息）
- 提供用户可理解的提示或操作建议
- 涉及数据异常时，统一使用"请联系管理员"引导

### 字段判空原则

**禁止使用默认值掩盖业务异常，应抛出异常快速失败**

**错误示例：更新用户备注，前端传 null**

```java
// ❌ 错误：掩盖了前端传参错误
user.setRemark(remark != null ? remark : "");
// 结果：前后端都认为成功，但实际数据异常被掩盖
```

**正确做法：明确检查，为 null 时抛出异常**

```java
// ✅ 正确：暴露问题，强制修复
if (remark == null) {
    throw new IllegalArgumentException("备注信息不能为空");
}
user.setRemark(remark);
```

**判断标准**：

- ❌ 不看"数据库是否 NOT NULL"（技术层面）
- ✅ 看"业务上是否应该为 null"（业务层面）
    - 必填字段：用户名、部门ID、小组ID 等 → 不应为 null
    - 可选字段：备注、邮箱、昵称等 → 可以为 null，但不能用默认值掩盖

**后果对比**：

- **掩盖异常**：前端传参错误被静默处理，产生脏数据，难以排查问题
- **抛出异常**：立即阻断请求，强制前端修复传参，保证数据质量

## 接口测试

项目运行在 `127.0.0.1:8080`，Context Path 为 `/api`。

### Mock 身份登录

当 `security.auth.mock-enabled` 开启时，可通过 `test-{userId}` 格式的 token 模拟任意用户身份：

```bash
# 以 userId=1 的用户身份请求
curl -s http://127.0.0.1:8080/api/session/status -H "Authorization: test-1"

# 以其他用户身份请求
curl -s http://127.0.0.1:8080/api/user/page -H "Authorization: test-2"

# POST 请求示例
curl -s -X POST http://127.0.0.1:8080/api/user/save \
  -H "Authorization: test-1" \
  -H "Content-Type: application/json" \
  -d '{"userName":"test","password":"xxx"}'
```

### 常用测试接口

```bash
# 登录状态查询
curl -s http://127.0.0.1:8080/api/session/status -H "Authorization: test-1"

# 用户分页查询
curl -s http://127.0.0.1:8080/api/user/page -H "Authorization: test-1"

# 小组分页查询
curl -s http://127.0.0.1:8080/api/team/page -H "Authorization: test-1"
```

## 应用入口

- **启动类**：`tooolan-ddd-start/src/main/java/com/tooolan/ddd/DddApplication.java`
- **主方法**：`SpringApplication.run(DddApplication.class, args)`
- **包扫描**：`@SpringBootApplication` 自动扫描 `com.tooolan.ddd` 及子包
