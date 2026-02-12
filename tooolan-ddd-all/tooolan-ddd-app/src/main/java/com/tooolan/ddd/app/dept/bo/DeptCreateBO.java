package com.tooolan.ddd.app.dept.bo;

import lombok.Data;

/**
 * 部门创建业务对象
 * 定义创建部门所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class DeptCreateBO {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 父部门ID
     */
    private Integer parentId;

    /**
     * 备注
     */
    private String remark;

}
