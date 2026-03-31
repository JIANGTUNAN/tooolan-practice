# 异常码设计规范

## Context

当前项目使用枚举名（如 `NOT_FOUND`、`USERNAME_EXISTS`）作为错误码，缺乏结构化信息。前端无法从错误码判断错误来源和类型，后端日志排查也不够直观。需要设计一套结构化异常码体系，同时服务于前端错误处理逻辑和后端日志追踪。

## 错误码格式

**四段式**: `{来源}-{模块}-{HTTP状态码}-{序号}`

**示例**: `1-U-404-001` = 本地DDD、用户模块、404语义、第1号错误

### 第1段：来源

| 值 | 含义 |
|---|---|
| `1` | 本地 DDD 代码 |
| `2` | 远程服务调用 |
| `3` | 第三方服务（预留） |

### 第2段：模块

| 值 | 模块 | 对应包 |
|---|---|---|
| `C` | 通用（Common） | `domain/common` |
| `U` | 用户（User） | `domain/user` |
| `T` | 小组（Team） | `domain/team` |
| `D` | 部门（Dept） | `domain/dept` |
| `S` | 会话（Session） | `domain/session` |
| `L` | 日志（Log） | `domain/log` |

### 第3段：HTTP 状态码

与 `ResponseCode` 对齐：`400`、`401`、`404`、`422`、`500`

注意：HTTP 响应始终返回 200，此段仅用于语义标识，由 `GlobalExceptionHandler` 映射到对应的 `ResponseCode`。

### 第4段：序号

`001`~`999`，每个 `来源-模块-HTTP` 组合独立编号。

## ErrorCode 接口改造

```java
public interface ErrorCode {
    String getCode();       // 如 "1-U-404-001"
    String getMessage();    // 如 "用户不存在"

    default int getHttpStatus() {
        return Integer.parseInt(getCode().split("-")[2]);
    }

    default String getSource() {
        return getCode().split("-")[0];
    }

    default String getModule() {
        return getCode().split("-")[1];
    }
}
```

HTTP 状态码从 code 字符串自动解析，保持单一数据源。

## 各模块错误码枚举

### CommonErrorCode

```java
public enum CommonErrorCode implements ErrorCode {
    PARAM_VALIDATION_FAILED("1-C-400-001", "参数校验失败"),
    PARAM_CONSTRAINT_VIOLATION("1-C-422-001", "参数约束违反"),
    ILLEGAL_ARGUMENT("1-C-400-002", "参数错误"),
    ILLEGAL_STATE("1-C-500-001", "系统繁忙，请稍后再试"),
    SYSTEM_ERROR("1-C-500-002", "系统错误，请联系管理员");
}
```

### UserErrorCode

```java
public enum UserErrorCode implements ErrorCode {
    USERNAME_EXISTS("1-U-400-001", "用户名已存在"),
    USERNAME_IMMUTABLE("1-U-400-002", "用户名不可修改"),
    NOT_FOUND("1-U-404-001", "用户不存在"),
    CANNOT_DELETE_ADMIN("1-U-400-003", "不能删除管理员"),
    OLD_PASSWORD_MISMATCH("1-U-400-004", "原密码错误"),
    PASSWORD_SAME_AS_OLD("1-U-400-005", "新旧密码相同"),
    SAVE_FAILED("1-U-500-001", "保存用户失败，请联系管理员"),
    UPDATE_FAILED("1-U-500-002", "更新用户失败，请联系管理员"),
    DELETE_FAILED("1-U-500-003", "删除用户失败，请联系管理员"),
    PASSWORD_CHANGE_FAILED("1-U-500-004", "修改密码失败，请联系管理员");
}
```

### SessionErrorCode

```java
public enum SessionErrorCode implements ErrorCode {
    LOGIN_FAILED("1-S-401-001", "登录失败"),
    NOT_LOGIN("1-S-401-002", "用户未登录");
}
```

### TeamErrorCode

```java
public enum TeamErrorCode implements ErrorCode {
    UNAVAILABLE("1-T-400-001", "小组不可用"),
    FULL("1-T-400-002", "小组已满员"),
    NOT_FOUND("1-T-404-001", "小组不存在"),
    CODE_EXISTS("1-T-400-003", "小组编码已存在"),
    SAVE_FAILED("1-T-500-001", "保存小组失败，请联系管理员");
}
```

### DeptErrorCode

```java
public enum DeptErrorCode implements ErrorCode {
    NOT_FOUND("1-D-404-001", "部门不存在"),
    CODE_EXISTS("1-D-400-001", "部门编码已存在");
}
```

### LogErrorCode

```java
public enum LogErrorCode implements ErrorCode {
    NOT_FOUND("1-L-404-001", "日志不存在"),
    SAVE_FAILED("1-L-500-001", "日志保存失败，请联系管理员");
}
```

## GlobalExceptionHandler 适配

`BaseException` 处理逻辑中，根据 `errorCode.getHttpStatus()` 映射到 `ResponseCode`：

| httpStatus 段 | ResponseCode |
|---|---|
| `400` | `20400` |
| `401` | `20401` |
| `404` | `20404` |
| `422` | `20422` |
| `500` | `20500` |

## ResultVo 适配

增加 `errorCode` 字段，响应示例：

```json
{
  "code": 20404,
  "message": "用户不存在",
  "errorCode": "1-U-404-001",
  "data": null
}
```

前端使用 `code` 做通用逻辑判断，`errorCode` 做精确错误处理。

## 涉及修改的文件

| 文件 | 改动 |
|---|---|
| `domain/common/exception/ErrorCode.java` | 接口增加 `getHttpStatus()`、`getSource()`、`getModule()` 默认方法 |
| `domain/common/constant/CommonErrorCode.java` | 枚举值改为结构化错误码 |
| `domain/user/constant/UserErrorCode.java` | 枚举值改为结构化错误码 |
| `domain/session/constant/SessionErrorCode.java` | 枚举值改为结构化错误码 |
| `domain/team/constant/TeamErrorCode.java` | 枚举值改为结构化错误码 |
| `domain/dept/constant/DeptErrorCode.java` | 枚举值改为结构化错误码 |
| `domain/log/constant/LogErrorCode.java` | 枚举值改为结构化错误码 |
| `domain/common/exception/BaseException.java` | 无需改动（已通过 ErrorCode 接口获取 code） |
| `api/common/err/GlobalExceptionHandler.java` | 根据 httpStatus 映射 ResponseCode，返回 errorCode |
| `api/common/vo/ResultVo.java` | 增加 errorCode 字段 |

## 验证方式

1. `cmd.exe /c "mvn clean install"` 构建通过
2. 检查所有 `throw new XxxException(XxxErrorCode.XXX)` 调用点，确保新枚举值正确替换
3. 启动应用，测试登录、查询不存在的用户等场景，验证响应中 `errorCode` 格式正确
