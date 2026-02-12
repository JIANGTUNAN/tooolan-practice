package com.tooolan.ddd.infra.persistence.dept.repository;

import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.infra.persistence.dept.converter.DeptConverter;
import com.tooolan.ddd.infra.persistence.dept.mapper.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 部门 仓储实现
 * 实现部门领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
@RequiredArgsConstructor
public class DeptRepositoryImpl implements DeptRepository {

    private final SysDeptMapper deptMapper;
    private final DeptConverter deptConverter;

}
