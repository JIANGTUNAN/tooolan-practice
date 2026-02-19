package com.tooolan.ddd.domain.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统操作日志 领域模型
 * 纯数据模型，不含业务方法
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Data
@EqualsAndHashCode
public class Log {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 业务模块（user/team/dept/session）
     */
    private String module;

    /**
     * 操作类型（create/update/delete/login/logout）
     */
    private String action;

    /**
     * 目标对象类型
     */
    private String targetType;

    /**
     * 目标对象ID
     */
    private String targetId;

    /**
     * 目标对象名称
     */
    private String targetName;

    /**
     * 操作详情（JSON格式，记录变更前后对比）
     */
    private String content;

    /**
     * 操作人ID
     */
    private Integer operatorId;

    /**
     * 操作人账户
     */
    private String operatorName;

    /**
     * 操作人IP地址
     */
    private String operatorIp;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

}
