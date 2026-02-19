package com.tooolan.ddd.domain.user.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.common.constant.MiscConstants;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.user.constant.UserErrorCode;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    private final TeamRepository teamRepository;


    /**
     * 保存用户
     * 保存成功后会将生成的 ID 回填到 user 对象中
     *
     * @param user 用户领域模型
     * @param team 用户所属小组
     * @throws BusinessRuleException 用户名已存在时抛出
     * @throws BusinessRuleException 小组不可用时抛出
     * @throws BusinessRuleException 小组已满员时抛出
     * @throws BusinessRuleException 保存失败时抛出
     */
    public void saveUser(User user, Team team) throws BusinessRuleException {
        // 校验用户名唯一性
        userRepository.getUserByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new BusinessRuleException(UserErrorCode.USERNAME_EXISTS);
                });

        // 校验小组状态
        if (ObjUtil.isNotNull(team) && !team.isAvailable()) {
            throw new BusinessRuleException(TeamErrorCode.UNAVAILABLE);
        }

        // 校验小组容量
        if (ObjUtil.isNotNull(team) && team.hasMemberLimit()) {
            long currentCount = userRepository.countByTeamId(user.getTeamId());
            if (currentCount >= team.getMaxMembers()) {
                throw new BusinessRuleException(TeamErrorCode.FULL);
            }
        }

        // 保存用户，失败时抛出异常触发事务回滚
        boolean saved = userRepository.save(user);
        if (BooleanUtil.isFalse(saved)) {
            throw new BusinessRuleException(UserErrorCode.SAVE_FAILED);
        }
    }

    /**
     * 更新用户
     * 支持部分字段更新和小组转移
     *
     * @param existingUser 现有用户信息
     * @param updatedUser  更新后的用户信息
     * @param newTeam      新小组（仅在小组转移时传入，否则为 null）
     * @throws BusinessRuleException 用户名被修改时抛出
     * @throws BusinessRuleException 目标小组不可用时抛出
     * @throws BusinessRuleException 目标小组已满员时抛出
     * @throws BusinessRuleException 更新失败时抛出
     */
    public void updateUser(User existingUser, User updatedUser, Team newTeam) throws BusinessRuleException {
        // 1. 校验用户名不可变性
        if (ObjUtil.notEqual(existingUser.getUsername(), updatedUser.getUsername())) {
            throw new BusinessRuleException(UserErrorCode.USERNAME_IMMUTABLE);
        }

        // 2. 如果修改了小组，校验新小组
        Integer oldTeamId = existingUser.getTeamId();
        Integer newTeamId = updatedUser.getTeamId();
        boolean teamChanged = ObjUtil.notEqual(oldTeamId, newTeamId);

        if (teamChanged && ObjUtil.isNotNull(newTeam)) {
            if (BooleanUtil.isFalse(newTeam.isAvailable())) {
                throw new BusinessRuleException(TeamErrorCode.UNAVAILABLE);
            }
            if (BooleanUtil.isTrue(newTeam.hasMemberLimit())) {
                long currentCount = userRepository.countByTeamId(newTeamId);
                if (currentCount >= newTeam.getMaxMembers()) {
                    throw new BusinessRuleException(TeamErrorCode.FULL);
                }
            }
        }

        // 3. 执行更新，失败时抛出异常触发事务回滚
        boolean updated = userRepository.updateById(updatedUser);
        if (BooleanUtil.isFalse(updated)) {
            throw new BusinessRuleException(UserErrorCode.UPDATE_FAILED);
        }
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID列表
     * @throws BusinessRuleException 包含管理员ID时抛出
     * @throws BusinessRuleException 用户不存在时抛出
     * @throws BusinessRuleException 删除失败时抛出
     */
    public void deleteUsers(List<Integer> userIds) throws BusinessRuleException {
        // 1. 校验是否包含管理员ID
        if (CollUtil.contains(userIds, MiscConstants.ADMIN_USER_ID)) {
            throw new BusinessRuleException(UserErrorCode.CANNOT_DELETE_ADMIN);
        }

        // 2. 校验用户是否存在
        long existCount = userRepository.countByIds(userIds);
        if (existCount != userIds.size()) {
            throw new BusinessRuleException(UserErrorCode.NOT_FOUND);
        }

        // 3. 执行删除
        int deletedCount = userRepository.deleteByIds(userIds);
        if (deletedCount != userIds.size()) {
            throw new BusinessRuleException(UserErrorCode.DELETE_FAILED);
        }
    }

}
