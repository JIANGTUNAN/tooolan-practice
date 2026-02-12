package com.tooolan.ddd.domain.dept.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门 实体
 * 纯数据模型，不含业务方法
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Data
@EqualsAndHashCode
public class Dept {

    /**
     * 部门ID
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 父部门ID，支持层级结构
     */
    private Integer parentId;

}
