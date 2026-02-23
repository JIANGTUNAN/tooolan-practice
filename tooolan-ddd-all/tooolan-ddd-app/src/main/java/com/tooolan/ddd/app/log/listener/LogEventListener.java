package com.tooolan.ddd.app.log.listener;

import com.tooolan.ddd.app.log.service.LogApplicationService;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.user.event.UserCreatedEvent;
import com.tooolan.ddd.domain.user.event.UserDeletedEvent;
import com.tooolan.ddd.domain.user.event.UserUpdatedEvent;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * 日志事件监听器
 * 监听领域事件并记录系统操作日志
 * <p>
 * 采用轻薄监听器模式，仅负责事件桥接，日志构建逻辑委托给 LogApplicationService
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final LogApplicationService logApplicationService;


    /**
     * 监听用户登录事件
     *
     * @param event 用户登录事件
     */
    @EventListener
    @Async("systemTaskExecutor")
    public void handleUserLoginEvent(UserLoginEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.logUserLogin(user);
            log.info("用户登录日志记录成功: userId={}, username={}, operator={}, ip={}",
                    user.getId(), user.getUsername(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理用户登录事件失败", e);
        }
    }

    /**
     * 监听用户创建事件
     *
     * @param event 用户创建事件
     */
    @Async("systemTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.logUserCreated(user);
            log.info("用户创建日志记录成功: userId={}, username={}, operator={}, ip={}",
                    user.getId(), user.getUsername(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理用户创建事件失败", e);
        }
    }

    /**
     * 监听用户更新事件
     *
     * @param event 用户更新事件
     */
    @Async("systemTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserUpdatedEvent(UserUpdatedEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.logUserUpdated(user);
            log.info("用户更新日志记录成功: userId={}, username={}, operator={}, ip={}",
                    user.getId(), user.getUsername(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理用户更新事件失败", e);
        }
    }

    /**
     * 监听用户删除事件
     *
     * @param event 用户删除事件
     */
    @Async("systemTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        try {
            List<Integer> userIds = event.getUserIds();
            logApplicationService.logUserDeleted(userIds);
            log.info("用户删除日志记录成功: userIds={}, operator={}, ip={}",
                    userIds, ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理用户删除事件失败", e);
        }
    }

}
