package com.tooolan.ddd.app.log.service;

import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.log.convert.LogConvert;
import com.tooolan.ddd.app.log.request.PageLogBo;
import com.tooolan.ddd.app.log.response.LogVo;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 系统操作日志 应用服务
 * 提供日志查询相关的业务编排
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@Service
@RequiredArgsConstructor
public class LogApplicationService {

    private final LogRepository logRepository;


    /**
     * 根据日志ID获取日志信息
     *
     * @param logId 日志ID
     * @return 日志视图对象，不存在时返回空
     */
    public Optional<LogVo> getLogById(Long logId) {
        Optional<Log> log = logRepository.getLog(logId);
        return log.map(LogConvert::toVo);
    }

    /**
     * 分页查询日志信息
     *
     * @param bo 查询条件
     * @return 分页结果
     */
    public PageVo<LogVo> pageLog(PageLogBo bo) {
        PageLogParam pageLogParam = LogConvert.toParam(bo);
        PageQueryResult<Log> pageQueryResult = logRepository.pageLog(pageLogParam);
        return LogConvert.toPageVo(pageQueryResult);
    }

}
