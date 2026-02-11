package com.tooolan.ddd.app.dept.bo;

import lombok.Data;

/**
 * 部门更新业务对象
 * 定义更新部门所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class DeptUpdateBO {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 备注
     */
    private String remark;
}
