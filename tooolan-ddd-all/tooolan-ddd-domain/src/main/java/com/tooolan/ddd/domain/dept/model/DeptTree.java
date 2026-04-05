package com.tooolan.ddd.domain.dept.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门树节点
 * 继承 Dept 并添加子节点列表，用于构建部门树形结构
 *
 * @author tooolan
 * @since 2026年4月4日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends Dept {

    /**
     * 子部门列表
     */
    private List<DeptTree> children;

}
