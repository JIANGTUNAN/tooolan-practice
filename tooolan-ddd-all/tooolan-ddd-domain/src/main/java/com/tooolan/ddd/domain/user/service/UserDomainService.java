package com.tooolan.ddd.domain.user.service;

import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 用户 领域服务（原子服务）
 * 提供用户相关的核心业务规则校验和原子化操作
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@DomainService
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;


    /**
     * 保存用户
     * 保存成功后会将生成的 ID 回填到 user 对象中
     *
     * @param user 用户领域模型
     * @param team 用户领域模型
     * @throws BusinessRuleException 用户名已存在时抛出
     * @throws BusinessRuleException 保存失败时抛出
     */
    public void saveUser(User user, Team team) throws BusinessRuleException {
        // 校验用户名唯一性
        Optional<User> existingUser = userRepository.getUserByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new BusinessRuleException("用户名已存在");
        }

        // TODO 小组状态校验（待 Team 添加 status 字段）
        // if (user.getTeamId() != null) {
        //     Optional<Team> teamOpt = teamRepository.getTeam(user.getTeamId());
        //     if (teamOpt.isPresent() && !teamOpt.get().isAvailable()) {
        //         throw new BusinessRuleException("小组不可用，无法添加用户");
        //     }
        // }

        // TODO 小组容量校验（待 Team 添加 maxUsers 字段）
        // if (user.getTeamId() != null) {
        //     long currentCount = userRepository.countByTeamId(user.getTeamId());
        //     Team team = teamRepository.getTeam(user.getTeamId())
        //         .orElseThrow(() -> new BusinessRuleException("指定的小组不存在"));
        //     if (currentCount >= team.getMaxUsers()) {
        //         throw new BusinessRuleException("小组已满员");
        //     }
        // }

        // 保存用户，失败时抛出异常触发事务回滚
        boolean saved = userRepository.save(user);
        if (!saved) {
            throw new BusinessRuleException("保存用户失败");
        }
    }

}
