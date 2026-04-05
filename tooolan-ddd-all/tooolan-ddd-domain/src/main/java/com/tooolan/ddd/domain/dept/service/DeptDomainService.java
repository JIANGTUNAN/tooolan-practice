package com.tooolan.ddd.domain.dept.service;

import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.dept.constant.DeptErrorCode;
import com.tooolan.ddd.domain.dept.exception.DeptException;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 部门 领域服务（原子服务）
 * 提供部门相关的原子化业务逻辑
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@DomainService
@RequiredArgsConstructor
public class DeptDomainService {

    private final DeptRepository deptRepository;

    /**
     * 保存部门（含编码唯一性校验）
     *
     * @param dept 部门领域模型
     * @throws DeptException 部门编码已存在或保存失败时抛出
     */
    public void saveDept(Dept dept) {
        // 校验部门编码唯一性
        if (deptRepository.existByCode(dept.getDeptCode())) {
            throw new DeptException(DeptErrorCode.CODE_EXISTS);
        }
        deptRepository.save(dept);
    }

    /**
     * 更新部门
     *
     * @param existingDept 现有部门
     * @param updatedDept  更新后的部门
     * @throws DeptException 部门编码重复时抛出
     */
    public void updateDept(Dept existingDept, Dept updatedDept) {
        // 校验：不能将自己设为父部门
        if (updatedDept.getParentId() != null && updatedDept.getParentId().equals(existingDept.getDeptId())) {
            throw new DeptException(DeptErrorCode.CANNOT_SET_SELF_AS_PARENT);
        }

        // 如果更新了部门编码，校验新编码是否重复
        if (updatedDept.getDeptCode() != null && !updatedDept.getDeptCode().equals(existingDept.getDeptCode())) {
            if (deptRepository.existByCode(updatedDept.getDeptCode())) {
                throw new DeptException(DeptErrorCode.CODE_EXISTS);
            }
        }
        deptRepository.updateById(updatedDept);
    }

    /**
     * 批量删除部门（含子部门和小组校验）
     *
     * @param deptIds   部门ID列表
     * @param teamCount 关联的小组数量（由 App 层传入）
     * @throws DeptException 存在子部门或关联小组时抛出
     */
    public void deleteDepts(List<Integer> deptIds, long teamCount) {
        // 1. 检查是否有子部门
        long childCount = deptRepository.countByParentIds(deptIds);
        if (childCount > 0) {
            throw new DeptException(DeptErrorCode.HAS_CHILD_DEPT);
        }

        // 2. 检查是否有小组
        if (teamCount > 0) {
            throw new DeptException(DeptErrorCode.HAS_TEAM);
        }

        // 3. 执行删除
        int deletedCount = deptRepository.deleteByIds(deptIds);
        if (deletedCount != deptIds.size()) {
            throw new DeptException(DeptErrorCode.DELETE_FAILED);
        }
    }
}
