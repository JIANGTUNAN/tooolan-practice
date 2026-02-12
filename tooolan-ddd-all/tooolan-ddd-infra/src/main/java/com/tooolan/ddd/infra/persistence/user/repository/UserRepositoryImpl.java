package com.tooolan.ddd.infra.persistence.user.repository;

import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.infra.persistence.user.converter.UserConverter;
import com.tooolan.ddd.infra.persistence.user.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 用户 仓储实现
 * 实现用户领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SysUserMapper userMapper;
    private final UserConverter userConverter;

}
