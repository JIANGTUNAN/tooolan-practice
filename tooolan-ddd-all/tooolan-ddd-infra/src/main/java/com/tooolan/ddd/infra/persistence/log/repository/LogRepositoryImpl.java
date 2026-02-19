package com.tooolan.ddd.infra.persistence.log.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.infra.persistence.log.converter.LogConverter;
import com.tooolan.ddd.infra.persistence.log.entity.SysLogEntity;
import com.tooolan.ddd.infra.persistence.log.mapper.SysLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 系统操作日志 仓储实现
 * 实现日志领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Repository
public class LogRepositoryImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements LogRepository {

    /**
     * 保存日志
     * 保存成功后会将生成的 ID 回填到 log 对象中
     *
     * @param log 日志领域模型
     * @return 是否保存成功
     */
    @Override
    public boolean save(Log log) {
        SysLogEntity entity = LogConverter.toEntity(log);
        boolean saved = super.save(entity);
        if (saved) {
            log.setId(entity.getLogId());
            log.setCreatedAt(entity.getCreatedAt());
        }
        return saved;
    }

}
