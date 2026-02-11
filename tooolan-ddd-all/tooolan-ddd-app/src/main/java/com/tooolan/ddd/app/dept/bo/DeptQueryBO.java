package com.tooolan.ddd.app.dept.bo;

import lombok.Data;

/**
 * 部门查询业务对象
 * 定义查询部门列表所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class DeptQueryBO {

    /**
     * 部门名称（模糊查询）
     */
    private String deptName;

    /**
     * 部门编码（模糊查询）
     */
    private String deptCode;

    /**
     * 父部门ID（null 表示查询根部门）
     */
    private Integer parentId;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
