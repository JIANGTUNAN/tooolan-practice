package com.tooolan.ddd.app.log.service;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
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

import java.util.*;

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
     * 敏感字段集合（这些字段在日志中需要脱敏或排除）
     */
    private static final Set<String> SENSITIVE_FIELDS = Set.of(
            "password"
    );


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
     * @param user         创建的用户
     * @param businessData 业务数据（SaveUserBo）
     */
    public void logUserCreated(User user, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.CREATE, user);
        logModel.setContent(toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户更新日志
     *
     * @param user         更新后的用户
     * @param businessData 业务数据（UpdateUserBo）
     */
    public void logUserUpdated(User user, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.UPDATE, user);
        logModel.setContent(toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户删除日志
     *
     * @param userIds      被删除的用户ID列表
     * @param businessData 业务数据（DeleteUserBo）
     */
    public void logUserDeleted(List<Integer> userIds, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.DELETE, null);
        logModel.setTargetId(userIds.toString());
        logModel.setTargetName("批量删除用户");
        logModel.setContent(toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户登录日志
     *
     * @param user         登录的用户
     * @param businessData 业务数据（LoginBo）
     */
    public void logUserLogin(User user, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.SESSION, LogOpType.LOGIN, user);
        logModel.setContent(toLogContent(businessData));
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

    /**
     * 将业务数据转换为日志内容 JSON 字符串
     *
     * @param businessData 业务数据对象
     * @return JSON 字符串，businessData 为 null 时返回 null
     */
    private String toLogContent(Object businessData) {
        if (businessData == null) {
            return null;
        }

        // 将对象转换为 Map（过滤 null 值）
        JSONConfig config = JSONConfig.create().setIgnoreNullValue(true);
        Map<?, ?> dataMap = JSONUtil.toBean(JSONUtil.toJsonStr(businessData, config), Map.class);

        // 移除敏感字段
        Map<String, Object> filteredMap = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : dataMap.entrySet()) {
            String key = String.valueOf(entry.getKey());
            if (!SENSITIVE_FIELDS.contains(key)) {
                filteredMap.put(key, entry.getValue());
            }
        }

        // 如果过滤后为空，返回 null
        if (filteredMap.isEmpty()) {
            return null;
        }

        return JSONUtil.toJsonStr(filteredMap, config);
    }

}
