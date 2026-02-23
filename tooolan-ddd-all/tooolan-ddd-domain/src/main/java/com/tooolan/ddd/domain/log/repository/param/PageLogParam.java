package com.tooolan.ddd.domain.log.repository.param;

import com.tooolan.ddd.domain.common.result.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 分页查询日志信息 查询条件
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageLogParam extends PageRequest {

    /**
     * 业务模块（精确查询）
     */
    private String module;

    /**
     * 操作类型（精确查询）
     */
    private String action;

    /**
     * 目标对象类型（精确查询）
     */
    private String targetType;

    /**
     * 操作人账户（模糊查询）
     */
    private String operatorName;

    /**
     * 创建时间-开始（范围查询）
     */
    private LocalDateTime createdAtStart;

    /**
     * 创建时间-结束（范围查询）
     */
    private LocalDateTime createdAtEnd;

}
