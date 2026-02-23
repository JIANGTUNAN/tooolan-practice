package com.tooolan.ddd.app.log.request;

import com.tooolan.ddd.app.common.response.PageQueryBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 分页查询日志信息 业务类
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageLogBo extends PageQueryBo {

    /**
     * 业务模块（精确查询）
     */
    private String opModule;

    /**
     * 操作类型（精确查询）
     */
    private String opType;

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
