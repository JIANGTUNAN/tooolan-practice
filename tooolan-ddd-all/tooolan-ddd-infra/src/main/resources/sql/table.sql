-- =============================================
-- DDD三层组织结构表设计（MySQL 8.5）
-- 用户 -> 小组 -> 部门
-- =============================================

-- =============================================
-- 1. 系统部门表
-- =============================================
DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        VARCHAR(100) NOT NULL COMMENT '部门名称',
    `code`        VARCHAR(50) NOT NULL COMMENT '部门编码，唯一',
    `parent_id`   BIGINT DEFAULT NULL COMMENT '父部门ID，支持层级',
    `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`  VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `updated_by`  VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `deleted`     TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）',
    `remark`      VARCHAR(500) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_dept_code` (`code`),
    KEY `idx_sys_dept_parent_id` (`parent_id`),
    KEY `idx_sys_dept_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统部门信息表';


-- =============================================
-- 2. 系统小组表
-- =============================================
DROP TABLE IF EXISTS `sys_team`;

CREATE TABLE `sys_team` (
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `dept_id`     BIGINT NOT NULL COMMENT '所属部门ID',
    `name`        VARCHAR(100) NOT NULL COMMENT '小组名称',
    `code`        VARCHAR(50) NOT NULL COMMENT '小组编码',
    `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`  VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `updated_by`  VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `deleted`     TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）',
    `remark`      VARCHAR(500) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_team_code` (`code`),
    KEY `idx_sys_team_dept_id` (`dept_id`),
    KEY `idx_sys_team_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统小组信息表';


-- =============================================
-- 3. 系统用户表
-- =============================================
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `team_id`     BIGINT NOT NULL COMMENT '所属小组ID',
    `username`    VARCHAR(50) NOT NULL COMMENT '用户名，唯一',
    `real_name`   VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `email`       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`  VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `updated_by`  VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `deleted`     TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）',
    `remark`      VARCHAR(500) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_user_username` (`username`),
    KEY `idx_sys_user_team_id` (`team_id`),
    KEY `idx_sys_user_email` (`email`),
    KEY `idx_sys_user_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户信息表';
