package com.tooolan.ddd.domain.log.repository;

import com.tooolan.ddd.domain.log.model.Log;

/**
 * 系统操作日志 仓储接口
 * 定义日志持久化操作契约，由基础设施层实现
 *
 * @author tooolan
 * @since 2026年2月19日
 */
public interface LogRepository {

    /**
     * 保存日志
     * 保存成功后会将生成的 ID 回填到 log 对象中
     *
     * @param log 日志领域模型
     * @return 是否保存成功
     */
    boolean save(Log log);

}
