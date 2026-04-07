package com.tooolan.ddd.infra.db.persistence.team.converter;

import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.infra.db.persistence.team.entity.SysTeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 小组基础设施层转换器
 * 负责领域模型 <-> 数据库实体的转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Mapper(componentModel = "spring")
public interface TeamInfraConverter {

    /**
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    Team toDomain(SysTeamEntity entity);

    /**
     * 将领域模型转换为数据库实体
     *
     * @param team 领域模型
     * @return 数据库实体
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SysTeamEntity toEntity(Team team);

    /**
     * 将状态值转换为状态枚举
     *
     * @param value 状态值
     * @return 状态枚举
     */
    default TeamStatusEnum toStatusEnum(Integer value) {
        if (value == null) {
            return null;
        }
        return TeamStatusEnum.fromValue(value)
                .orElseThrow(() -> new IllegalStateException("小组状态异常，请联系管理员"));
    }

    /**
     * 将状态枚举转换为状态值
     *
     * @param status 状态枚举
     * @return 状态值
     */
    default Integer toStatusValue(TeamStatusEnum status) {
        return status != null ? status.getValue() : null;
    }
}
