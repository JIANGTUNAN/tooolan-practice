package com.tooolan.ddd.domain.dept.repository;

import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptCode;
import com.tooolan.ddd.domain.common.identifier.DeptId;

import java.util.List;
import java.util.Optional;

/**
 * 部门 仓储接口
 * 定义部门持久化操作契约，由基础设施层实现
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public interface DeptRepository {

    /**
     * 保存部门
     *
     * @param dept 部门聚合根
     */
    void save(Dept dept);

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     */
    void remove(DeptId deptId);

    /**
     * 根据ID查询部门
     *
     * @param deptId 部门ID
     * @return 部门聚合根
     */
    Optional<Dept> findById(DeptId deptId);

    /**
     * 查询所有部门
     *
     * @return 部门列表
     */
    List<Dept> findAll();

    /**
     * 判断部门编码是否存在
     *
     * @param deptCode 部门编码
     * @return 是否存在
     */
    boolean existsByDeptCode(DeptCode deptCode);

    /**
     * 根据部门编码查询部门
     *
     * @param deptCode 部门编码
     * @return 部门聚合根
     */
    Optional<Dept> findByDeptCode(DeptCode deptCode);

    /**
     * 根据父部门ID查询子部门列表
     *
     * @param parentId 父部门ID
     * @return 子部门列表
     */
    List<Dept> findByParentId(DeptId parentId);

    /**
     * 查询所有根部门（无父部门的部门）
     *
     * @return 根部门列表
     */
    List<Dept> findRootDepts();

    /**
     * 校验部门编码唯一性（排除指定部门）
     *
     * @param deptCode      部门编码
     * @param excludeDeptId 排除的部门ID
     * @return 是否唯一
     */
    boolean isDeptCodeUnique(DeptCode deptCode, DeptId excludeDeptId);

    /**
     * 判断部门是否有子部门
     *
     * @param deptId 部门ID
     * @return 是否有子部门
     */
    boolean hasChildren(DeptId deptId);

    /**
     * 统计子部门数量
     *
     * @param parentId 父部门ID
     * @return 子部门数量
     */
    long countChildren(DeptId parentId);
}
