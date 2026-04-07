package com.tooolan.ddd.infra.db.persistence.log.converter;

import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.infra.db.persistence.log.entity.SysLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 日志基础设施层转换器
 * 负责领域模型 <-> 数据库实体的转换
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Mapper(componentModel = "spring")
public interface LogInfraConverter {
    /**
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    @Mapping(source = "logId", target = "id")
    Log toDomain(SysLogEntity entity);
    /**
     * 将领域模型转换为数据库实体
     *
     * @param log 领域模型
     * @return 数据库实体
     */
    @Mapping(source = "id", target = "logId")
    SysLogEntity toEntity(Log log);
}
