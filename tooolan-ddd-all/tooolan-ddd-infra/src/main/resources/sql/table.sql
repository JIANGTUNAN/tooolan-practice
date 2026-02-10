-- =============================================
-- DDD三层组织结构表设计（极简版）
-- 用户 -> 小组 -> 部门
-- 兼容 PostgreSQL 和 MySQL
-- =============================================

-- =============================================
-- 1. 部门表 (Department)
-- =============================================
CREATE TABLE department
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 主键，自增
    name       VARCHAR(100) NOT NULL,              -- 部门名称
    code       VARCHAR(50)  NOT NULL UNIQUE,       -- 部门编码
    parent_id  BIGINT,                             -- 父部门ID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 更新时间
);

COMMENT
ON TABLE department IS '部门表';
COMMENT
ON COLUMN department.id IS '主键ID';
COMMENT
ON COLUMN department.name IS '部门名称';
COMMENT
ON COLUMN department.code IS '部门编码，唯一';
COMMENT
ON COLUMN department.parent_id IS '父部门ID，支持层级';
COMMENT
ON COLUMN department.created_at IS '创建时间';
COMMENT
ON COLUMN department.updated_at IS '更新时间';

-- 部门表添加审计字段
ALTER TABLE department ADD COLUMN created_by VARCHAR(50) COMMENT '创建人';
ALTER TABLE department ADD COLUMN updated_by VARCHAR(50) COMMENT '更新人';
ALTER TABLE department ADD COLUMN deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）';
ALTER TABLE department ADD COLUMN remark VARCHAR(500) COMMENT '备注信息';


-- =============================================
-- 2. 小组表 (Team Group)
-- =============================================
CREATE TABLE team_group
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 主键，自增
    dept_id    BIGINT       NOT NULL,              -- 所属部门ID
    name       VARCHAR(100) NOT NULL,              -- 小组名称
    code       VARCHAR(50)  NOT NULL,              -- 小组编码
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- 更新时间
    FOREIGN KEY (dept_id) REFERENCES department (id)
);

COMMENT
ON TABLE team_group IS '小组表';
COMMENT
ON COLUMN team_group.id IS '主键ID';
COMMENT
ON COLUMN team_group.dept_id IS '所属部门ID';
COMMENT
ON COLUMN team_group.name IS '小组名称';
COMMENT
ON COLUMN team_group.code IS '小组编码';
COMMENT
ON COLUMN team_group.created_at IS '创建时间';
COMMENT
ON COLUMN team_group.updated_at IS '更新时间';

-- 小组表添加审计字段
ALTER TABLE team_group ADD COLUMN created_by VARCHAR(50) COMMENT '创建人';
ALTER TABLE team_group ADD COLUMN updated_by VARCHAR(50) COMMENT '更新人';
ALTER TABLE team_group ADD COLUMN deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）';
ALTER TABLE team_group ADD COLUMN remark VARCHAR(500) COMMENT '备注信息';


-- =============================================
-- 3. 用户表 (User)
-- =============================================
CREATE TABLE app_user
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 主键，自增
    group_id   BIGINT      NOT NULL,               -- 所属小组ID
    username   VARCHAR(50) NOT NULL UNIQUE,        -- 用户名
    real_name  VARCHAR(50) NOT NULL,               -- 真实姓名
    email      VARCHAR(100),                       -- 邮箱
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- 更新时间
    FOREIGN KEY (group_id) REFERENCES team_group (id)
);

COMMENT
ON TABLE app_user IS '用户表';
COMMENT
ON COLUMN app_user.id IS '主键ID';
COMMENT
ON COLUMN app_user.group_id IS '所属小组ID';
COMMENT
ON COLUMN app_user.username IS '用户名，唯一';
COMMENT
ON COLUMN app_user.real_name IS '真实姓名';
COMMENT
ON COLUMN app_user.email IS '邮箱';
COMMENT
ON COLUMN app_user.created_at IS '创建时间';
COMMENT
ON COLUMN app_user.updated_at IS '更新时间';

-- 用户表添加审计字段
ALTER TABLE app_user ADD COLUMN created_by VARCHAR(50) COMMENT '创建人';
ALTER TABLE app_user ADD COLUMN updated_by VARCHAR(50) COMMENT '更新人';
ALTER TABLE app_user ADD COLUMN deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）';
ALTER TABLE app_user ADD COLUMN remark VARCHAR(500) COMMENT '备注信息';
