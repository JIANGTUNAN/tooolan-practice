package com.tooolan.ddd.api.team.dto;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * 小组响应
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class TeamResponse {

    /**
     * 小组ID
     */
    Integer teamId;

    /**
     * 所属部门ID
     */
    Integer deptId;

    /**
     * 小组名称
     */
    String teamName;

    /**
     * 小组编码
     */
    String teamCode;

    /**
     * 备注
     */
    String remark;

    /**
     * 用户数量（统计信息）
     */
    Long userCount;

    /**
     * 创建人
     */
    String createdBy;

    /**
     * 创建时间
     */
    LocalDateTime createdAt;

    /**
     * 更新人
     */
    String updatedBy;

    /**
     * 更新时间
     */
    LocalDateTime updatedAt;
}
