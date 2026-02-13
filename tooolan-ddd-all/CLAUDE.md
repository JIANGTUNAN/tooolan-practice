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

**重要**：父项目 `/mnt/d/project/tooolan-practice/pom.xml` 统一管理所有依赖版本，包括：
- Spring Boot 3.5.8
- MyBatis Plus 3.5.15
- Hutool 5.8.43
- Lombok 1.18.42
- MySQL Connector 8.0.33

子模块无需指定版本号，直接引用依赖即可。

## DDD 分层架构

项目遵循严格的 DDD 四层架构（不含启动层），模块依赖关系：

```
tooolan-ddd-start（启动层）
    ↓
tooolan-ddd-api（门面层）+ tooolan-ddd-infra（基础设施层）
    ↓
tooolan-ddd-app（应用层）
    ↓
tooolan-ddd-domain（领域层）
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

- **start** 依赖 api 和 infra（通过依赖传递引入其他层）
- **infra** 依赖 domain（实现仓储接口）
- **app** 依赖 domain（编排领域服务）
- **domain** 不依赖任何业务层（纯粹的核心业务逻辑）
- **api** 可被所有层引用（DTO 定义）

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

| 类 | 路径 | 作用 |
|---|------|------|
| `BaseEntity` | `infra/common/entity/` | 所有实体基类，提供审计字段 |
| `MyMetaObjectHandler` | `infra/common/config/` | 自动填充 createdBy/createdAt/updatedBy/updatedAt |
| `MyBatisPlusConfig` | `infra/common/config/` | 分页插件配置，Mapper 扫描 |
| `RequestContext` | `infra/common/context/` | 请求上下文，存储当前用户信息 |

## 配置文件说明

### 环境配置

- **application.yml**：主配置文件，定义通用配置（端口 8081，激活 dev 环境）
- **application-dev.yml**：开发环境配置（端口 8080，DEBUG 日志级别）
- **logback-spring.xml**：日志配置，支持彩色输出和文件滚动

### 数据库配置

开发环境数据库连接：
- **地址**：`ddns.tooolan.com:53306`
- **数据库**：`practice-ddd`
- **字符集**：utf8mb4

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

## 应用入口

- **启动类**：`tooolan-ddd-start/src/main/java/com/tooolan/ddd/DddApplication.java`
- **主方法**：`SpringApplication.run(DddApplication.class, args)`
- **包扫描**：`@SpringBootApplication` 自动扫描 `com.tooolan.ddd` 及子包
