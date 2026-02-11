package com.tooolan.ddd.domain.dept.model;

import com.tooolan.ddd.domain.common.AggregateRoot;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门 聚合根
 * 封装部门的核心业务逻辑，支持层级结构
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Dept extends AggregateRoot<DeptId> {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private DeptCode deptCode;

    /**
     * 父部门ID，支持层级结构
     */
    private DeptId parentId;

    /**
     * 创建部门
     *
     * @param deptName 部门名称
     * @param deptCode 部门编码
     * @param parentId 父部门ID
     * @return 部门聚合根
     */
    public static Dept create(String deptName, DeptCode deptCode, DeptId parentId) {
        Dept dept = new Dept();
        dept.deptName = deptName;
        dept.deptCode = deptCode;
        dept.parentId = parentId;
        return dept;
    }

    /**
     * 从持久化数据恢复部门对象
     *
     * @param deptId   部门ID
     * @param deptName 部门名称
     * @param deptCode 部门编码
     * @param parentId 父部门ID
     * @param remark   备注
     * @return 部门聚合根
     */
    public static Dept restore(DeptId deptId, String deptName, DeptCode deptCode,
                               DeptId parentId, String remark) {
        Dept dept = new Dept();
        dept.id = deptId;
        dept.deptName = deptName;
        dept.deptCode = deptCode;
        dept.parentId = parentId;
        dept.remark = remark;
        return dept;
    }

    /**
     * 更新部门基本信息
     *
     * @param deptName 部门名称
     * @param deptCode 部门编码
     */
    public void updateInfo(String deptName, DeptCode deptCode) {
        this.deptName = deptName;
        this.deptCode = deptCode;
    }

    /**
     * 移动部门到新父部门
     *
     * @param newParentId 新父部门ID
     */
    public void moveTo(DeptId newParentId) {
        // 不能将部门移动到自身下
        if (newParentId != null && this.id != null && this.id.equals(newParentId)) {
            throw new BusinessRuleException("不能将部门移动到自身下");
        }
        this.parentId = newParentId;
    }

    /**
     * 判断是否为根部门（无父部门）
     *
     * @return 是否为根部门
     */
    public boolean isRoot() {
        return parentId == null;
    }

    /**
     * 判断是否为指定部门的子部门
     *
     * @param parentId 父部门ID
     * @return 是否为子部门
     */
    public boolean isChildOf(DeptId parentId) {
        return parentId != null && parentId.equals(this.parentId);
    }

    @Override
    public void validate() {
        if (deptCode == null) {
            throw new BusinessRuleException("部门编码不能为空");
        }
        if (deptName == null || deptName.trim().isEmpty()) {
            throw new BusinessRuleException("部门名称不能为空");
        }
    }

    /**
     * 分配ID
     * 此方法仅供基础设施层在持久化后回设ID使用
     *
     * @param deptId 部门ID
     */
    public void assignId(DeptId deptId) {
        this.id = deptId;
    }
}
