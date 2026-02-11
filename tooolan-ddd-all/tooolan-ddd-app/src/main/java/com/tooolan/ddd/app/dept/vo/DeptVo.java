package com.tooolan.ddd.app.dept.vo;

import lombok.Value;

/**
 * 部门视图对象
 * 用于返回部门数据，包含部门基本信息和父部门信息
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class DeptVo {

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
     * 父部门名称
     */
    String parentName;

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
    String createdAt;

    /**
     * 更新人
     */
    String updatedBy;

    /**
     * 更新时间
     */
    String updatedAt;
}
