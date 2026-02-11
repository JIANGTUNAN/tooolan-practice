package com.tooolan.ddd.domain.user.service;

import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.identifier.UserId;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.model.Username;
import com.tooolan.ddd.domain.user.repository.UserRepository;

/**
 * 用户 领域服务
 * 封装涉及多个聚合根或复杂业务逻辑的用户相关操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public class UserDomainService {

    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 校验用户名唯一性
     *
     * @param username     用户名
     * @param excludeUserId 排除的用户ID（更新时使用）
     * @throws BusinessRuleException 用户名已存在时抛出
     */
    public void validateUsernameUnique(Username username, UserId excludeUserId) {
        if (!userRepository.isUsernameUnique(username, excludeUserId)) {
            throw new BusinessRuleException("用户名已存在");
        }
    }

    /**
     * 校验用户是否可以转移到指定小组
     *
     * @param userId  用户ID
     * @param teamId  目标小组ID
     * @throws NotFoundException       用户不存在时抛出
     * @throws BusinessRuleException    业务规则校验失败时抛出
     */
    public void validateUserTransfer(UserId userId, TeamId teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("用户", userId));

        // 如果目标小组与当前小组相同，无需转移
        if (teamId.equals(user.getTeamId())) {
            throw new BusinessRuleException("用户已在目标小组中");
        }

        // 可以添加更多业务规则校验，如：小组人数限制等
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    public boolean checkUsernameExists(Username username) {
        return userRepository.existsByUsername(username);
    }
}
