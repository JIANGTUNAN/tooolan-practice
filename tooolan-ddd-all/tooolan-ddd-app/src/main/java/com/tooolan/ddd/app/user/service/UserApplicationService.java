package com.tooolan.ddd.app.user.service;

import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.user.convert.UserConvert;
import com.tooolan.ddd.app.user.request.PageUserBo;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;
import com.tooolan.ddd.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 系统用户信息 应用服务
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


    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户视图对象
     */
    public Optional<UserVo> getUserById(Integer userId) {
        Optional<User> user = userRepository.getUser(userId);
        return user.map(UserConvert::toVo);
    }

    /**
     * 分页查询用户信息
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    public PageVo<UserVo> pageUser(PageUserBo dto) {
        PageUserParam pageUserParam = UserConvert.toParam(dto);
        PageQueryResult<User> pageQueryResult = userRepository.pageUser(pageUserParam);
        return UserConvert.toPageVo(pageQueryResult);
    }

}
