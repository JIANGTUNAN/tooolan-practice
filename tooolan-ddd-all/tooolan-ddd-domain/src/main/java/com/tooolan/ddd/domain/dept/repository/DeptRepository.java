package com.tooolan.ddd.domain.dept.repository;

import com.tooolan.ddd.domain.dept.model.Dept;

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
     * 根据部门ID查询部门信息
     *
     * @param deptId 部门ID
     * @return 部门信息，不存在时返回空
     */
    Optional<Dept> getById(Integer deptId);

    /**
     * 判断部门是否存在
     *
     * @param deptId 部门ID
     * @return 是否存在
     */
    boolean existById(Integer deptId);

}
