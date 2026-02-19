package com.tooolan.ddd.infra.persistence.log.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志 实体类
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Data
@TableName(value = "sys_log")
public class SysLogEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 业务模块（user/team/dept/session）
     */
    @TableField(value = "`module`")
    private String module;

    /**
     * 操作类型（create/update/delete/login/logout）
     */
    @TableField(value = "`action`")
    private String action;

    /**
     * 目标对象类型
     */
    @TableField(value = "target_type")
    private String targetType;

    /**
     * 目标对象ID
     */
    @TableField(value = "target_id")
    private String targetId;

    /**
     * 目标对象名称
     */
    @TableField(value = "target_name")
    private String targetName;

    /**
     * 操作详情（JSON格式，记录变更前后对比）
     */
    @TableField(value = "content")
    private String content;

    /**
     * 操作人ID
     */
    @TableField(value = "operator_id")
    private Integer operatorId;

    /**
     * 操作人账户
     */
    @TableField(value = "operator_name")
    private String operatorName;

    /**
     * 操作人IP地址
     */
    @TableField(value = "operator_ip")
    private String operatorIp;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

}
