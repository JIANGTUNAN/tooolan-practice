package com.tooolan.ddd.app.log.listener;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.HttpContext;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.format.DateTimeFormatter;

/**
 * 日志事件监听器
 * 监听领域事件并记录系统操作日志
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogEventListener {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LogRepository logRepository;

    /**
     * 监听用户登录事件
     * 使用 AFTER_COMMIT 确保只有登录事务提交成功后才记录日志
     *
     * @param event 用户登录事件
     */
    @Async("systemTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserLoginEvent(UserLoginEvent event) {
        try {
            User user = event.getUser();

            // 构建日志领域模型
            Log logModel = new Log();
            logModel.setModule("session");
            logModel.setAction("login");
            logModel.setTargetType("User");
            logModel.setTargetId(user.getId().toString());
            logModel.setTargetName(user.getUsername());
            logModel.setOperatorId(user.getId());
            logModel.setOperatorName(user.getUsername());

            // 从 HttpContext 获取客户端 IP
            HttpContext httpContext = ContextHolder.getHttpContext();
            if (ObjUtil.isNotNull(httpContext) && ObjUtil.isNotNull(httpContext.getRequest())) {
                String clientIp = JakartaServletUtil.getClientIP(httpContext.getRequest());
                logModel.setOperatorIp(clientIp);
            }

            // 保存日志
            boolean saved = logRepository.save(logModel);
            if (saved) {
                log.info("用户登录日志记录成功: userId={}, username={}, ip={}, time={}",
                        user.getId(), user.getUsername(), logModel.getOperatorIp(), DATE_FORMATTER.format(logModel.getCreatedAt()));
            } else {
                log.warn("用户登录日志记录失败: userId={}, username={}", user.getId(), user.getUsername());
            }
        } catch (Exception e) {
            // 日志记录失败不应影响主业务，记录错误日志即可
            log.error("处理用户登录事件失败", e);
        }
    }

}
