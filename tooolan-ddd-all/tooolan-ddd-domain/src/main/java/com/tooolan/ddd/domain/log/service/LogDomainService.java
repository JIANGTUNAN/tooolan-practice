package com.tooolan.ddd.domain.log.service;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.log.constant.LogErrorCode;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;

/**
 * 系统操作日志 领域服务
 * 提供日志相关的核心业务操作
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@DomainService
@RequiredArgsConstructor
public class LogDomainService {

    private final LogRepository logRepository;


    /**
     * 保存日志
     *
     * @param log 日志领域模型
     * @throws BusinessRuleException 保存失败时抛出
     */
    public void saveLog(Log log) {
        boolean saved = logRepository.save(log);
        if (BooleanUtil.isFalse(saved)) {
            throw new BusinessRuleException(LogErrorCode.SAVE_FAILED);
        }
    }

}
