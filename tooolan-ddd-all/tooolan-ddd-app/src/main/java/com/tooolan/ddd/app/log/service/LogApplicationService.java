package com.tooolan.ddd.app.log.service;

import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.log.convert.LogConvert;
import com.tooolan.ddd.app.log.request.PageLogBo;
import com.tooolan.ddd.app.log.response.LogVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.log.constant.LogOpModule;
import com.tooolan.ddd.domain.log.constant.LogOpType;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;
import com.tooolan.ddd.domain.log.service.LogDomainService;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final LogDomainService logDomainService;


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

    /**
     * 记录用户创建日志
     *
     * @param user 创建的用户
     */
    public void logUserCreated(User user) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.CREATE, user);
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户更新日志
     *
     * @param user 更新后的用户
     */
    public void logUserUpdated(User user) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.UPDATE, user);
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户删除日志
     *
     * @param userIds 被删除的用户ID列表
     */
    public void logUserDeleted(List<Integer> userIds) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.DELETE, null);
        logModel.setTargetId(userIds.toString());
        logModel.setTargetName("批量删除用户");
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户登录日志
     *
     * @param user 登录的用户
     */
    public void logUserLogin(User user) {
        Log logModel = this.buildLog(LogOpModule.SESSION, LogOpType.LOGIN, user);
        logDomainService.saveLog(logModel);
    }

    /**
     * 构建日志模型
     *
     * @param opModule 操作模块
     * @param opType   操作类型
     * @param user     目标用户（可为 null）
     * @return 日志领域模型
     */
    private Log buildLog(String opModule, String opType, User user) {
        Log logModel = new Log();
        logModel.setOpModule(opModule);
        logModel.setOpType(opType);
        if (user != null) {
            logModel.setTargetType(user.getClass().getTypeName());
            logModel.setTargetId(user.getId().toString());
            logModel.setTargetName(user.getUsername());
        }
        logModel.setOperatorId(ContextHolder.getUserId());
        logModel.setOperatorName(ContextHolder.getUsername());
        logModel.setOperatorIp(ContextHolder.getClientIp());
        return logModel;
    }

}
