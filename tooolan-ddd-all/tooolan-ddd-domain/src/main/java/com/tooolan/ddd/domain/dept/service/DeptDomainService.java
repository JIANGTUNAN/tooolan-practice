package com.tooolan.ddd.domain.dept.service;

import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptCode;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;

/**
 * 部门 领域服务
 * 封装涉及多个聚合根或复杂业务逻辑的部门相关操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public class DeptDomainService {

    private final DeptRepository deptRepository;

    public DeptDomainService(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    /**
     * 校验部门编码唯一性
     *
     * @param deptCode      部门编码
     * @param excludeDeptId 排除的部门ID（更新时使用）
     * @throws BusinessRuleException 部门编码已存在时抛出
     */
    public void validateDeptCodeUnique(DeptCode deptCode, DeptId excludeDeptId) {
        if (!deptRepository.isDeptCodeUnique(deptCode, excludeDeptId)) {
            throw new BusinessRuleException("部门编码已存在");
        }
    }

    /**
     * 校验部门是否可以移动到指定父部门
     *
     * @param deptId     部门ID
     * @param newParentId 新父部门ID
     * @throws NotFoundException       部门不存在时抛出
     * @throws BusinessRuleException    业务规则校验失败时抛出
     */
    public void validateDeptMove(DeptId deptId, DeptId newParentId) {
        Dept dept = deptRepository.findById(deptId)
                .orElseThrow(() -> new NotFoundException("部门", deptId));

        // 不能将部门移动到自身下
        if (deptId.equals(newParentId)) {
            throw new BusinessRuleException("不能将部门移动到自身下");
        }

        // 检查是否形成循环依赖（如果新父部门是当前部门的子孙部门）
        if (newParentId != null && isDescendant(deptId, newParentId)) {
            throw new BusinessRuleException("不能将部门移动到其子部门下");
        }
    }

    /**
     * 校验部门是否可以删除
     *
     * @param deptId 部门ID
     * @throws NotFoundException       部门不存在时抛出
     * @throws BusinessRuleException    部门有子部门或关联小组时抛出
     */
    public void validateDeptDeletion(DeptId deptId) {
        if (!deptRepository.findById(deptId).isPresent()) {
            throw new NotFoundException("部门", deptId);
        }

        if (deptRepository.hasChildren(deptId)) {
            throw new BusinessRuleException("部门存在子部门，无法删除");
        }

        // 这里可以添加更多校验，如：检查部门下是否有小组等
    }

    /**
     * 检查部门编码是否存在
     *
     * @param deptCode 部门编码
     * @return 是否存在
     */
    public boolean checkDeptCodeExists(DeptCode deptCode) {
        return deptRepository.existsByDeptCode(deptCode);
    }

    /**
     * 统计部门的子部门数量
     *
     * @param parentId 父部门ID
     * @return 子部门数量
     */
    public long countChildren(DeptId parentId) {
        return deptRepository.countChildren(parentId);
    }

    /**
     * 判断一个部门是否是另一个部门的子孙部门
     *
     * @param ancestorId   祖先部门ID
     * @param descendantId 子孙部门ID
     * @return 是否为子孙部门
     */
    private boolean isDescendant(DeptId ancestorId, DeptId descendantId) {
        Dept current = deptRepository.findById(descendantId).orElse(null);
        while (current != null && current.getParentId() != null) {
            if (current.getParentId().equals(ancestorId)) {
                return true;
            }
            current = deptRepository.findById(current.getParentId()).orElse(null);
        }
        return false;
    }
}
