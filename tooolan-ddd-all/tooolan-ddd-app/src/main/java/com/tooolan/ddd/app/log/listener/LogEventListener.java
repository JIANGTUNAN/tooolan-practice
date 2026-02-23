package com.tooolan.ddd.app.log.listener;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.LogRepository;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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

    private final LogRepository logRepository;


    /**
     * 监听用户登录事件
     *
     * @param event 用户登录事件
     */
    @Async("systemTaskExecutor")
    @EventListener
    public void handleUserLoginEvent(UserLoginEvent event) {
        try {
            User user = event.getUser();

            // 构建日志领域模型
            Log logModel = new Log();
            logModel.setModule("session");
            logModel.setAction("login");
            logModel.setTargetType(user.getClass().getTypeName());
            logModel.setTargetId(user.getId().toString());
            logModel.setTargetName(user.getUsername());
            logModel.setOperatorId(user.getId());
            logModel.setOperatorName(user.getUsername());
            // 从上下文获取客户端 IP
            logModel.setOperatorIp(ContextHolder.getClientIp());

            // 保存日志
            boolean saved = logRepository.save(logModel);
            if (saved) {
                log.info("用户登录日志记录成功: userId={}, username={}, ip={}, time={}",
                        user.getId(), user.getUsername(), logModel.getOperatorIp(), LocalDateTimeUtil.formatNormal(logModel.getCreatedAt()));
            } else {
                log.warn("用户登录日志记录失败: userId={}, username={}", user.getId(), user.getUsername());
            }
        } catch (Exception e) {
            // 日志记录失败不应影响主业务，记录错误日志即可
            log.error("处理用户登录事件失败", e);
        }
    }

}
