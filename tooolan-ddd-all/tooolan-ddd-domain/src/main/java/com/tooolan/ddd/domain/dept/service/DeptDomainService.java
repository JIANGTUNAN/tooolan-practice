package com.tooolan.ddd.domain.dept.service;

import com.tooolan.ddd.domain.common.annotation.DomainService;
import lombok.RequiredArgsConstructor;

/**
 * 部门 领域服务（原子服务）
 * 暂时为空，后续有不可拆分的业务逻辑时再添加
 * 例如：删除部门前的校验、层级关系校验等
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@DomainService
@RequiredArgsConstructor
public class DeptDomainService {
    // 暂时为空，后续添加原子化的业务逻辑
    // 例如：
    // - validateDeptDeletion(Integer deptId) // 校验是否可删除（检查子部门、小组等）
    // - buildDeptTree(List<Dept> depts)      // 构建部门树
}
