/*
 Navicat Premium Dump SQL

 Source Server         : 【r4s】环境数据库
 Source Server Type    : MySQL
 Source Server Version : 80408 (8.4.8)
 Source Host           : ddns.tooolan.com:53306
 Source Schema         : practice-ddd

 Target Server Type    : MySQL
 Target Server Version : 80408 (8.4.8)
 File Encoding         : 65001

 Date: 11/02/2026 08:39:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
  `dept_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门编码，唯一',
  `parent_id` int NULL DEFAULT NULL COMMENT '父部门ID，支持层级',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注信息',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`dept_id`) USING BTREE,
  UNIQUE INDEX `uk_sys_dept_code`(`dept_code` ASC) USING BTREE,
  INDEX `idx_sys_dept_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_sys_dept_deleted`(`deleted` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统部门信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_team
-- ----------------------------
DROP TABLE IF EXISTS `sys_team`;
CREATE TABLE `sys_team`  (
  `team_id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dept_id` int NOT NULL COMMENT '所属部门ID',
  `team_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小组名称',
  `team_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小组编码',
  `status`      tinyint NOT NULL DEFAULT 0 COMMENT '小组状态（0:正常,1:停用,2:满员）',
  `max_members` int     NOT NULL DEFAULT 0 COMMENT '小组人数上限（0表示不限制）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注信息',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`team_id`) USING BTREE,
  UNIQUE INDEX `uk_sys_team_code`(`team_code` ASC) USING BTREE,
  INDEX `idx_sys_team_dept_id`(`dept_id` ASC) USING BTREE,
  INDEX `idx_sys_team_deleted` (`deleted` ASC) USING BTREE,
  INDEX `idx_sys_team_status` (`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统小组信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `team_id` int NOT NULL COMMENT '所属小组ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账户',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注信息',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:正常,1:已删除）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_sys_user_username`(`user_name` ASC) USING BTREE,
  INDEX `idx_sys_user_team_id`(`team_id` ASC) USING BTREE,
  INDEX `idx_sys_user_email`(`email` ASC) USING BTREE,
  INDEX `idx_sys_user_deleted`(`deleted` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `log_id`        bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `module`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '业务模块（user/team/dept/session）',
    `action`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '操作类型（create/update/delete/login/logout）',
    `target_type`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '目标对象类型',
    `target_id`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '目标对象ID',
    `target_name`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '目标对象名称',
    `content`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '操作详情（JSON格式，记录变更前后对比）',
    `operator_id`   int                                                           NULL     DEFAULT NULL COMMENT '操作人ID',
    `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '操作人账户',
    `operator_ip`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '操作人IP地址',
    `created_at`    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`log_id`) USING BTREE,
    INDEX `idx_sys_log_module` (`module` ASC) USING BTREE,
    INDEX `idx_sys_log_action` (`action` ASC) USING BTREE,
    INDEX `idx_sys_log_operator_id` (`operator_id` ASC) USING BTREE,
    INDEX `idx_sys_log_created_at` (`created_at` ASC) USING BTREE,
    INDEX `idx_sys_log_target` (`target_type` ASC, `target_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统操作日志'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
