package com.tooolan.ddd.app.team.service;

import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.team.service.TeamDomainService;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 小组应用服务
 * 提供小组相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class TeamApplicationService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamDomainService teamDomainService;

}
