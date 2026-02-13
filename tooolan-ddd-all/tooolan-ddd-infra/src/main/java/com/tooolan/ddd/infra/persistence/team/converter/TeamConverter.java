package com.tooolan.ddd.infra.persistence.team.converter;

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
        return team;
    }

}
