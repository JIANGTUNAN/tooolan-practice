package com.tooolan.ddd.domain.dept.repository;

import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.repository.param.PageDeptParam;

import java.util.Collection;
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
     * 根据部门ID查询部门信息
     *
     * @param deptId 部门ID
     * @return 部门信息，不存在时返回空
     */
    Optional<Dept> getById(Integer deptId);

    /**
     * 根据部门编码查询部门信息
     *
     * @param deptCode 部门编码
     * @return 部门信息，不存在时返回空
     */
    Optional<Dept> getByCode(String deptCode);

    /**
     * 根据部门编码判断部门是否存在
     *
     * @param deptCode 部门编码
     * @return 是否存在
     */
    boolean existByCode(String deptCode);

    /**
     * 分页查询部门信息
     *
     * @param param 分页查询参数
     * @return 分页查询结果
     */
    PageQueryResult<Dept> page(PageDeptParam param);

    /**
     * 查询所有部门（用于构建树）
     *
     * @return 部门列表
     */
    List<Dept> listAll();

    /**
     * 判断部门是否存在
     *
     * @param deptId 部门ID
     * @return 是否存在
     */
    boolean existById(Integer deptId);

    /**
     * 批量判断部门是否全部存在
     *
     * @param deptIds 部门ID列表
     * @return 所有部门都存在返回 true，否则返回 false
     */
    boolean existByIds(List<Integer> deptIds);

    /**
     * 保存部门
     * 保存成功后会回填部门ID
     *
     * @param dept 部门领域模型
     * @return 是否保存成功
     */
    boolean save(Dept dept);

    /**
     * 根据部门ID更新部门信息
     * 仅更新非 null 字段，部分更新策略
     *
     * @param dept 部门领域模型
     * @return 是否更新成功
     */
    boolean updateById(Dept dept);

    /**
     * 批量逻辑删除部门
     *
     * @param deptIds 部门ID列表
     * @return 删除的记录数
     */
    int deleteByIds(List<Integer> deptIds);

    /**
     * 检查是否存在子部门
     *
     * @param parentId 父部门ID
     * @return 是否存在子部门
     */
    boolean existsByParentId(Integer parentId);

    /**
     * 根据父ID查询子部门数量
     *
     * @param parentIds 父部门ID列表
     * @return 子部门数量
     */
    long countByParentIds(List<Integer> parentIds);

    /**
     * 根据部门ID集合查询部门列表
     *
     * @param deptIds 部门ID集合
     * @return 部门列表
     */
    List<Dept> queryByIds(Collection<Integer> deptIds);

}
