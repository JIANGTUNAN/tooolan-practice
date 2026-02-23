package com.tooolan.ddd.infra.persistence.log.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;
import com.tooolan.ddd.infra.persistence.log.converter.LogConverter;
import com.tooolan.ddd.infra.persistence.log.entity.SysLogEntity;
import com.tooolan.ddd.infra.persistence.log.mapper.SysLogMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
     * 根据日志ID查询日志信息
     *
     * @param logId 日志ID
     * @return 日志信息，不存在时返回空
     */
    @Override
    public Optional<Log> getLog(Long logId) {
        return super.getOptById(logId)
                .map(LogConverter::toDomain);
    }

    /**
     * 分页查询日志信息
     *
     * @param pageLogParam 分页查询参数
     * @return 分页查询结果，按创建时间倒序排列
     */
    @Override
    public PageQueryResult<Log> pageLog(PageLogParam pageLogParam) {
        IPage<Log> page = super.lambdaQuery()
                .eq(StrUtil.isNotBlank(pageLogParam.getModule()), SysLogEntity::getModule, pageLogParam.getModule())
                .eq(StrUtil.isNotBlank(pageLogParam.getAction()), SysLogEntity::getAction, pageLogParam.getAction())
                .eq(StrUtil.isNotBlank(pageLogParam.getTargetType()), SysLogEntity::getTargetType, pageLogParam.getTargetType())
                .like(StrUtil.isNotBlank(pageLogParam.getOperatorName()), SysLogEntity::getOperatorName, pageLogParam.getOperatorName())
                .ge(ObjUtil.isNotNull(pageLogParam.getCreatedAtStart()), SysLogEntity::getCreatedAt, pageLogParam.getCreatedAtStart())
                .le(ObjUtil.isNotNull(pageLogParam.getCreatedAtEnd()), SysLogEntity::getCreatedAt, pageLogParam.getCreatedAtEnd())
                .orderByDesc(SysLogEntity::getCreatedAt)
                .page(PageDTO.of(pageLogParam.getPageNum(), pageLogParam.getPageSize()))
                .convert(LogConverter::toDomain);

        PageQueryResult<Log> pageQueryResult = new PageQueryResult<>();
        BeanUtil.copyProperties(page, pageQueryResult);
        return pageQueryResult;
    }

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
