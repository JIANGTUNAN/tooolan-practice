package com.tooolan.ddd.infra.persistence.team.converter;

import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.infra.persistence.team.entity.SysTeamEntity;
import org.springframework.stereotype.Component;

/**
 * 小组转换器
 * 负责小组领域对象与数据库实体之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Component
public class TeamConverter {

    /**
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     * @throws IllegalStateException 当状态值无效时抛出
     */
    public static Team toDomain(SysTeamEntity entity) {
        if (entity == null) {
            return null;
        }
        Team team = new Team();
        team.setId(entity.getTeamId());
        team.setDeptId(entity.getDeptId());
        team.setTeamName(entity.getTeamName());
        team.setTeamCode(entity.getTeamCode());
        TeamStatusEnum teamStatusEnum = TeamStatusEnum.fromValue(entity.getStatus())
                .orElseThrow(() -> new IllegalStateException("小组状态异常，请联系管理员"));
        team.setStatus(teamStatusEnum);
        team.setMaxMembers(entity.getMaxMembers());
        return team;
    }

    /**
     * 将领域模型转换为数据库实体
     *
     * @param team 领域模型
     * @return 数据库实体
     */
    public static SysTeamEntity toEntity(Team team) {
        if (team == null) {
            return null;
        }
        SysTeamEntity entity = new SysTeamEntity();
        entity.setTeamId(team.getId());
        entity.setDeptId(team.getDeptId());
        entity.setTeamName(team.getTeamName());
        entity.setTeamCode(team.getTeamCode());
        entity.setStatus(team.getStatus().getValue());
        entity.setMaxMembers(team.getMaxMembers());
        return entity;
    }

}
