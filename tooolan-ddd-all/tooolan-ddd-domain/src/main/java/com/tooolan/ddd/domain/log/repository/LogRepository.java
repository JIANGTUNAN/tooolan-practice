package com.tooolan.ddd.domain.log.repository;

import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;

import java.util.Optional;

/**
 * 系统操作日志 仓储接口
 * 定义日志持久化操作契约，由基础设施层实现
 *
 * @author tooolan
 * @since 2026年2月19日
 */
public interface LogRepository {

    /**
     * 根据日志ID查询日志信息
     *
     * @param logId 日志ID
     * @return 日志信息，不存在时返回空
     */
    Optional<Log> getLog(Long logId);

    /**
     * 分页查询日志信息
     * 支持按业务模块、操作类型、目标对象类型进行精确查询
     * 支持按操作人账户进行模糊查询
     * 支持按创建时间范围进行筛选
     *
     * @param pageLogParam 分页查询参数
     * @return 分页查询结果，按创建时间倒序排列
     */
    PageQueryResult<Log> pageLog(PageLogParam pageLogParam);

    /**
     * 保存日志
     * 保存成功后会将生成的 ID 回填到 log 对象中
     *
     * @param log 日志领域模型
     * @return 是否保存成功
     */
    boolean save(Log log);

}
