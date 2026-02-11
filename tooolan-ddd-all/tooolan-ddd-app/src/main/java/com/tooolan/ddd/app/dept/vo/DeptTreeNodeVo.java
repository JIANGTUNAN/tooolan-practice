package com.tooolan.ddd.app.dept.vo;

import lombok.Data;

import java.util.List;

/**
 * 部门树节点视图对象
 * 用于构建部门的树形结构，包含子部门列表
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class DeptTreeNodeVo {

    /**
     * 部门ID
     */
    private Integer deptId;

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
     * 子部门列表
     */
    private List<DeptTreeNodeVo> children;
}
