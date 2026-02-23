package com.tooolan.ddd.app.log.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志视图对象
 * 用于返回日志数据，包含日志完整信息
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@Data
public class LogVo {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 业务模块（user/team/dept/session）
     */
    private String opModule;

    /**
     * 操作类型（create/update/delete/login/logout）
     */
    private String opType;

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
