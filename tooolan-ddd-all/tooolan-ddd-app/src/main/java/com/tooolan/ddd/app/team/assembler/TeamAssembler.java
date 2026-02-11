package com.tooolan.ddd.app.team.assembler;

import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.team.bo.TeamCreateBO;
import com.tooolan.ddd.app.team.vo.TeamVo;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.pagination.Page;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.model.TeamCode;
import org.springframework.stereotype.Component;

/**
 * 小组DTO组装器
 * 负责小组领域对象与BO/VO之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Component
public class TeamAssembler {

    /**
     * 业务对象转领域对象
     *
     * @param bo 小组创建业务对象
     * @return 小组领域对象
     */
    public Team toDomain(TeamCreateBO bo) {
        DeptId deptId = DeptId.of(bo.getDeptId());
        TeamCode teamCode = TeamCode.of(bo.getTeamCode());

        return Team.create(deptId, bo.getTeamName(), teamCode);
    }

    /**
     * 领域对象转视图对象
     *
     * @param team      小组领域对象
     * @param userCount 用户数量（统计信息）
     * @return 小组视图对象
     */
    public TeamVo toVo(Team team, Long userCount) {
        if (team == null) {
            return null;
        }

        return new TeamVo(
                team.getId() != null ? team.getId().getValue() : null,
                team.getTeamName(),
                team.getTeamCode() != null ? team.getTeamCode().getValue() : null,
                team.getDeptId() != null ? team.getDeptId().getValue() : null,
                null, // deptName - 需要从仓储获取
                userCount,
                team.getRemark(),
                null, // createdBy - 从审计字段获取
                null, // createdAt - 从审计字段获取
                null, // updatedBy - 从审计字段获取
                null  // updatedAt - 从审计字段获取
        );
    }

    /**
     * 领域对象转视图对象（不包含统计信息）
     *
     * @param team 小组领域对象
     * @return 小组视图对象
     */
    public TeamVo toVo(Team team) {
        return toVo(team, null);
    }

    /**
     * 分页结果转换
     *
     * @param page 领域分页结果
     * @return 分页视图对象
     */
    public PageVo<TeamVo> toPageVo(Page<Team> page) {
        return PageVo.of(
                page.getRecords().stream()
                        .map(this::toVo)
                        .toList(),
                page.getTotal(),
                page.getPageNum(),
                page.getPageSize()
        );
    }
}
