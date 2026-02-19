package com.tooolan.ddd.infra.persistence.log.converter;

import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.infra.persistence.log.entity.SysLogEntity;

/**
 * 系统操作日志 转换器
 * 负责日志领域对象与数据库实体之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月19日
 */
public class LogConverter {

    /**
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    public static Log toDomain(SysLogEntity entity) {
        if (entity == null) {
            return null;
        }
        Log log = new Log();
        log.setId(entity.getLogId());
        log.setModule(entity.getModule());
        log.setAction(entity.getAction());
        log.setTargetType(entity.getTargetType());
        log.setTargetId(entity.getTargetId());
        log.setTargetName(entity.getTargetName());
        log.setContent(entity.getContent());
        log.setOperatorId(entity.getOperatorId());
        log.setOperatorName(entity.getOperatorName());
        log.setOperatorIp(entity.getOperatorIp());
        log.setCreatedAt(entity.getCreatedAt());
        return log;
    }

    /**
     * 将领域模型转换为数据库实体
     *
     * @param log 领域模型
     * @return 数据库实体
     */
    public static SysLogEntity toEntity(Log log) {
        if (log == null) {
            return null;
        }
        SysLogEntity entity = new SysLogEntity();
        entity.setLogId(log.getId());
        entity.setModule(log.getModule());
        entity.setAction(log.getAction());
        entity.setTargetType(log.getTargetType());
        entity.setTargetId(log.getTargetId());
        entity.setTargetName(log.getTargetName());
        entity.setContent(log.getContent());
        entity.setOperatorId(log.getOperatorId());
        entity.setOperatorName(log.getOperatorName());
        entity.setOperatorIp(log.getOperatorIp());
        entity.setCreatedAt(log.getCreatedAt());
        return entity;
    }

}
