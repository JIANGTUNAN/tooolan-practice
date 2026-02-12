package com.tooolan.ddd.app.dept.service;

import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.domain.dept.service.DeptDomainService;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 部门应用服务
 * 提供部门相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class DeptApplicationService {

    private final DeptRepository deptRepository;
    private final TeamRepository teamRepository;
    private final DeptDomainService deptDomainService;

}
