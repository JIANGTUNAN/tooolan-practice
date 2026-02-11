package com.tooolan.ddd.domain.team.service;

import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.model.TeamCode;
import com.tooolan.ddd.domain.team.repository.TeamRepository;

/**
 * 小组 领域服务
 * 封装涉及多个聚合根或复杂业务逻辑的小组相关操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public class TeamDomainService {

    private final TeamRepository teamRepository;

    public TeamDomainService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * 校验小组编码唯一性
     *
     * @param teamCode      小组编码
     * @param excludeTeamId 排除的小组ID（更新时使用）
     * @throws BusinessRuleException 小组编码已存在时抛出
     */
    public void validateTeamCodeUnique(TeamCode teamCode, TeamId excludeTeamId) {
        if (!teamRepository.isTeamCodeUnique(teamCode, excludeTeamId)) {
            throw new BusinessRuleException("小组编码已存在");
        }
    }

    /**
     * 校验小组是否可以转移到指定部门
     *
     * @param teamId  小组ID
     * @param deptId  目标部门ID
     * @throws NotFoundException       小组不存在时抛出
     * @throws BusinessRuleException    业务规则校验失败时抛出
     */
    public void validateTeamTransfer(TeamId teamId, DeptId deptId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("小组", teamId));

        // 如果目标部门与当前部门相同，无需转移
        if (deptId.equals(team.getDeptId())) {
            throw new BusinessRuleException("小组已在目标部门中");
        }

        // 可以添加更多业务规则校验
    }

    /**
     * 检查小组编码是否存在
     *
     * @param teamCode 小组编码
     * @return 是否存在
     */
    public boolean checkTeamCodeExists(TeamCode teamCode) {
        return teamRepository.existsByTeamCode(teamCode);
    }

    /**
     * 统计部门下的小组数量
     *
     * @param deptId 部门ID
     * @return 小组数量
     */
    public long countTeamsByDept(DeptId deptId) {
        return teamRepository.countByDept(deptId);
    }
}
