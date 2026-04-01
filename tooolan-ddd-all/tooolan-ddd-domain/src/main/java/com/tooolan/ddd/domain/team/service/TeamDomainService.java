package com.tooolan.ddd.domain.team.service;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import com.tooolan.ddd.domain.team.exception.TeamException;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * 小组 领域服务（原子服务）
 * 提供小组相关的原子化业务逻辑
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@DomainService
@RequiredArgsConstructor
public class TeamDomainService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    /**
     * 保存小组
     * 包含编码唯一性校验
     *
     * @param team 小组领域模型
     * @throws TeamException 小组编码已存在或保存失败时抛出
     */
    public void saveTeam(Team team) {
        // 1. 校验小组编码唯一性
        teamRepository.getTeamByCode(team.getTeamCode())
                .ifPresent(t -> {
                    throw new TeamException(TeamErrorCode.CODE_EXISTS);
                });
        // 2. 保存小组
        boolean saved = teamRepository.save(team);
        if (BooleanUtil.isFalse(saved)) {
            throw new TeamException(TeamErrorCode.SAVE_FAILED);
        }
    }

    /**
     * 更新小组
     * 包含状态变更业务规则校验
     *
     * @param existingTeam 现有小组
     * @param updatedTeam  更新后的小组
     * @throws TeamException 状态变更冲突或更新失败时抛出
     */
    public void updateTeam(Team existingTeam, Team updatedTeam) {
        // 状态变更校验
        if (updatedTeam.getStatus() != null) {
            TeamStatusEnum targetStatus = updatedTeam.getStatus();
            TeamStatusEnum currentStatus = existingTeam.getStatus();

            // 不允许直接设置为满员状态
            if (targetStatus == TeamStatusEnum.FULL) {
                throw new TeamException(TeamErrorCode.STATUS_CONFLICT);
            }

            // 满员状态不允许改为正常（需要先增大人数上限）
            if (currentStatus == TeamStatusEnum.FULL && targetStatus == TeamStatusEnum.NORMAL) {
                throw new TeamException(TeamErrorCode.STATUS_CONFLICT);
            }

            // 停用需要检查小组内是否还有成员
            if (targetStatus == TeamStatusEnum.DISABLED) {
                long memberCount = userRepository.countByTeamId(existingTeam.getId());
                if (memberCount > 0) {
                    throw new TeamException(TeamErrorCode.HAS_MEMBERS);
                }
            }
        }

        // 如果修改了人数上限，需要重新计算状态
        if (updatedTeam.getMaxMembers() != null
                && !updatedTeam.getMaxMembers().equals(existingTeam.getMaxMembers())) {
            TeamStatusEnum currentStatus = existingTeam.getStatus();

            if (currentStatus == TeamStatusEnum.FULL) {
                // 满员状态下增大上限 → 自动更新为正常（除非是停用状态）
                long memberCount = userRepository.countByTeamId(existingTeam.getId());
                if (!updatedTeam.isFull((int) memberCount) && updatedTeam.getStatus() != TeamStatusEnum.DISABLED) {
                    updatedTeam.setStatus(TeamStatusEnum.NORMAL);
                }
            } else if (currentStatus == TeamStatusEnum.NORMAL) {
                // 正常状态下减小上限 → 检查是否需要变为满员
                long memberCount = userRepository.countByTeamId(existingTeam.getId());
                if (updatedTeam.isFull((int) memberCount)) {
                    updatedTeam.setStatus(TeamStatusEnum.FULL);
                }
            }
            // 停用状态下修改上限 → 保持停用状态，不做变更
        }

        // 执行更新
        boolean updated = teamRepository.updateById(updatedTeam);
        if (BooleanUtil.isFalse(updated)) {
            throw new TeamException(TeamErrorCode.UPDATE_FAILED);
        }
    }

}
