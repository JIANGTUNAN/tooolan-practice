package com.tooolan.ddd.api.dept.dto;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * 部门响应
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class DeptResponse {

    /**
     * 部门ID
     */
    Integer deptId;

    /**
     * 部门名称
     */
    String deptName;

    /**
     * 部门编码
     */
    String deptCode;

    /**
     * 父部门ID
     */
    Integer parentId;

    /**
     * 备注
     */
    String remark;

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
