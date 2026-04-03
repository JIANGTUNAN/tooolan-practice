package com.tooolan.ddd.app.dept.response;

import lombok.Data;

/**
 * 部门视图对象
 * 用于返回部门数据，包含部门基本信息和关联的父部门、子部门信息
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
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

}
