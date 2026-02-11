package com.tooolan.ddd.api.dept.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门树节点
 * 支持children嵌套，用于构建部门树形结构
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class DeptTreeNode {

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
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 子部门列表
     */
    private List<DeptTreeNode> children;
}
