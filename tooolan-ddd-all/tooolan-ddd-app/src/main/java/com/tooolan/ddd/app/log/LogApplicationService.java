package com.tooolan.ddd.app.log;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.log.convert.LogConvert;
import com.tooolan.ddd.app.log.request.PageLogBo;
import com.tooolan.ddd.app.log.response.LogVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.log.constant.LogOpModule;
import com.tooolan.ddd.domain.log.constant.LogOpType;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;
import com.tooolan.ddd.domain.log.service.LogDomainService;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public Optional<LogVo> getById(Long logId) {
        Optional<Log> log = logRepository.getById(logId);
        return log.map(LogConvert::toVo);
    }

    /**
     * 分页查询日志信息
     *
     * @param bo 查询条件
     * @return 分页结果
     */
    public PageVo<LogVo> page(PageLogBo bo) {
        PageLogParam pageLogParam = LogConvert.toParam(bo);
        PageQueryResult<Log> pageQueryResult = logRepository.page(pageLogParam);
        return LogConvert.toPageVo(pageQueryResult);
    }

    /**
     * 记录用户创建日志
     *
     * @param user         创建的用户
     * @param businessData 业务数据（SaveUserBo）
     */
    public void onUserCreated(User user, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.CREATE, user);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户更新日志
     *
     * @param user         更新后的用户
     * @param businessData 业务数据（UpdateUserBo）
     */
    public void onUserUpdated(User user, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.UPDATE, user);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户删除日志
     *
     * @param userIds      被删除的用户ID列表
     * @param businessData 业务数据（DeleteUserBo）
     */
    public void onUserDeleted(List<Integer> userIds, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.DELETE, null);
        logModel.setTargetId(userIds.toString());
        logModel.setTargetName("批量删除用户");
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户登录日志
     *
     * @param user         登录的用户
     * @param businessData 业务数据（LoginBo）
     */
    public void onUserLogin(User user, Object businessData) {
        Log logModel = this.buildLog(LogOpModule.SESSION, LogOpType.LOGIN, user);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录用户密码修改日志
     * 不记录请求体数据，因为只包含敏感密码字段
     *
     * @param user 修改密码的用户
     */
    public void onUserPasswordChanged(User user) {
        Log logModel = this.buildLog(LogOpModule.USER, LogOpType.UPDATE, user);
        // 不设置 content，因为请求体只包含敏感密码字段
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录小组创建日志
     *
     * @param team         创建的小组
     * @param businessData 业务数据（SaveTeamBo）
     */
    public void onTeamCreated(Team team, Object businessData) {
        Log logModel = this.buildTeamLog(LogOpModule.TEAM, LogOpType.CREATE, team);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录小组更新日志
     *
     * @param team         更新后的小组
     * @param businessData 业务数据（UpdateTeamBo）
     */
    public void onTeamUpdated(Team team, Object businessData) {
        Log logModel = this.buildTeamLog(LogOpModule.TEAM, LogOpType.UPDATE, team);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录小组删除日志
     *
     * @param teamIds      被删除的小组ID列表
     * @param businessData 业务数据（DeleteTeamBo）
     */
    public void onTeamDeleted(List<Integer> teamIds, Object businessData) {
        Log logModel = this.buildTeamLog(LogOpModule.TEAM, LogOpType.DELETE, null);
        logModel.setTargetId(teamIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        logModel.setTargetName("批量删除小组");
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录部门创建日志
     *
     * @param dept         创建的部门
     * @param businessData 业务数据（SaveDeptBo）
     */
    public void onDeptCreated(Dept dept, Object businessData) {
        Log logModel = this.buildDeptLog(LogOpModule.DEPT, LogOpType.CREATE, dept);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录部门更新日志
     *
     * @param dept         更新后的部门
     * @param businessData 业务数据（UpdateDeptBo）
     */
    public void onDeptUpdated(Dept dept, Object businessData) {
        Log logModel = this.buildDeptLog(LogOpModule.DEPT, LogOpType.UPDATE, dept);
        logModel.setContent(this.toLogContent(businessData));
        logDomainService.saveLog(logModel);
    }

    /**
     * 记录部门删除日志
     *
     * @param deptIds 被删除的部门ID列表
     */
    public void onDeptDeleted(List<Integer> deptIds) {
        Log logModel = this.buildDeptLog(LogOpModule.DEPT, LogOpType.DELETE, null);
        logModel.setTargetId(deptIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        logModel.setTargetName("批量删除部门");
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
     * 构建小组日志模型
     *
     * @param opModule 操作模块
     * @param opType   操作类型
     * @param team     目标小组（可为 null）
     * @return 日志领域模型
     */
    private Log buildTeamLog(String opModule, String opType, Team team) {
        Log logModel = new Log();
        logModel.setOpModule(opModule);
        logModel.setOpType(opType);
        if (team != null) {
            logModel.setTargetType(team.getClass().getTypeName());
            logModel.setTargetId(team.getId().toString());
            logModel.setTargetName(team.getTeamName());
        }
        logModel.setOperatorId(ContextHolder.getUserId());
        logModel.setOperatorName(ContextHolder.getUsername());
        logModel.setOperatorIp(ContextHolder.getClientIp());
        return logModel;
    }

    /**
     * 构建部门日志模型
     *
     * @param opModule 操作模块
     * @param opType   操作类型
     * @param dept     目标部门（可为 null）
     * @return 日志领域模型
     */
    private Log buildDeptLog(String opModule, String opType, Dept dept) {
        Log logModel = new Log();
        logModel.setOpModule(opModule);
        logModel.setOpType(opType);
        if (dept != null) {
            logModel.setTargetType(dept.getClass().getTypeName());
            logModel.setTargetId(dept.getId().toString());
            logModel.setTargetName(dept.getDeptName());
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
