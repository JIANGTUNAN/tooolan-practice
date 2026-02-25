package com.tooolan.ddd.domain.team.service;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
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

    /**
     * 保存小组
     * 包含编码唯一性校验
     *
     * @param team 小组领域模型
     * @throws BusinessRuleException 小组编码已存在或保存失败时抛出
     */
    public void saveTeam(Team team) throws BusinessRuleException {
        // 1. 校验小组编码唯一性
        teamRepository.getTeamByCode(team.getTeamCode())
                .ifPresent(t -> {
                    throw new BusinessRuleException(TeamErrorCode.CODE_EXISTS);
                });
        // 2. 保存小组
        boolean saved = teamRepository.save(team);
        if (BooleanUtil.isFalse(saved)) {
            throw new BusinessRuleException(TeamErrorCode.SAVE_FAILED);
        }
    }

}
