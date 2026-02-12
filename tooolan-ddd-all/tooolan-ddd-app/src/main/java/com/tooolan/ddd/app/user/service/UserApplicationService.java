package com.tooolan.ddd.app.user.service;

import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户应用服务
 * 提供用户相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

}
