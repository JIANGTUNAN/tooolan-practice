package com.tooolan.ddd.infra.persistence.team.converter;

import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.model.TeamCode;
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
     * 实体转领域对象
     *
     * @param entity 数据库实体
     * @return 小组领域对象
     */
    public Team toDomain(SysTeamEntity entity) {
        if (entity == null) {
            return null;
        }

        TeamId teamId = entity.getTeamId() != null
                ? TeamId.of(entity.getTeamId())
                : null;

        DeptId deptId = entity.getDeptId() != null
                ? DeptId.of(entity.getDeptId())
                : null;

        TeamCode teamCode = entity.getTeamCode() != null
                ? TeamCode.of(entity.getTeamCode())
                : null;

        return Team.restore(
                teamId,
                deptId,
                entity.getTeamName(),
                teamCode,
                entity.getRemark()
        );
    }

    /**
     * 领域对象转实体
     *
     * @param team 小组领域对象
     * @return 数据库实体
     */
    public SysTeamEntity toEntity(Team team) {
        if (team == null) {
            return null;
        }

        SysTeamEntity entity = new SysTeamEntity();

        // 如果领域对象有ID，则设置到实体
        if (team.getId() != null) {
            entity.setTeamId(team.getId().getValue());
        }

        // 设置部门ID
        if (team.getDeptId() != null) {
            entity.setDeptId(team.getDeptId().getValue());
        }

        // 设置小组编码
        if (team.getTeamCode() != null) {
            entity.setTeamCode(team.getTeamCode().getValue());
        }

        entity.setTeamName(team.getTeamName());
        entity.setRemark(team.getRemark());

        return entity;
    }

    /**
     * 更新实体
     * 将领域对象的值更新到已有实体中
     *
     * @param entity 数据库实体
     * @param team   小组领域对象
     */
    public void updateEntity(SysTeamEntity entity, Team team) {
        if (entity == null || team == null) {
            return;
        }

        // 更新部门ID
        if (team.getDeptId() != null) {
            entity.setDeptId(team.getDeptId().getValue());
        }

        // 更新小组编码
        if (team.getTeamCode() != null) {
            entity.setTeamCode(team.getTeamCode().getValue());
        }

        entity.setTeamName(team.getTeamName());
        entity.setRemark(team.getRemark());
    }
}
