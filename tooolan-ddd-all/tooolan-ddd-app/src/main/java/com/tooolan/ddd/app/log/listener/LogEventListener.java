package com.tooolan.ddd.app.log.listener;

import com.tooolan.ddd.app.log.LogApplicationService;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.dept.event.DeptCreatedEvent;
import com.tooolan.ddd.domain.dept.event.DeptDeletedEvent;
import com.tooolan.ddd.domain.dept.event.DeptUpdatedEvent;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.team.event.TeamCreatedEvent;
import com.tooolan.ddd.domain.team.event.TeamDeletedEvent;
import com.tooolan.ddd.domain.team.event.TeamUpdatedEvent;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.user.event.UserCreatedEvent;
import com.tooolan.ddd.domain.user.event.UserDeletedEvent;
import com.tooolan.ddd.domain.user.event.UserPasswordChangedEvent;
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
    @Async("taskExecutor")
    public void handleUserLoginEvent(UserLoginEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.onUserLogin(user, event.getBusinessData());
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
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.onUserCreated(user, event.getBusinessData());
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
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserUpdatedEvent(UserUpdatedEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.onUserUpdated(user, event.getBusinessData());
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
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        try {
            List<Integer> userIds = event.getUserIds();
            logApplicationService.onUserDeleted(userIds, event.getBusinessData());
            log.info("用户删除日志记录成功: userIds={}, operator={}, ip={}",
                    userIds, ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理用户删除事件失败", e);
        }
    }

    /**
     * 监听用户密码修改事件
     *
     * @param event 用户密码修改事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserPasswordChangedEvent(UserPasswordChangedEvent event) {
        try {
            User user = event.getUser();
            logApplicationService.onUserPasswordChanged(user);
            log.info("用户密码修改日志记录成功: userId={}, username={}, operator={}, ip={}",
                    user.getId(), user.getUsername(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理用户密码修改事件失败", e);
        }
    }

    /**
     * 监听小组创建事件
     *
     * @param event 小组创建事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTeamCreatedEvent(TeamCreatedEvent event) {
        try {
            Team team = event.getTeam();
            logApplicationService.onTeamCreated(team, event.getBusinessData());
            log.info("小组创建日志记录成功: teamId={}, teamName={}, operator={}, ip={}",
                    team.getTeamId(), team.getTeamName(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理小组创建事件失败", e);
        }
    }

    /**
     * 监听小组更新事件
     *
     * @param event 小组更新事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTeamUpdatedEvent(TeamUpdatedEvent event) {
        try {
            Team team = event.getTeam();
            logApplicationService.onTeamUpdated(team, event.getBusinessData());
            log.info("小组更新日志记录成功: teamId={}, teamName={}, operator={}, ip={}",
                    team.getTeamId(), team.getTeamName(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理小组更新事件失败", e);
        }
    }

    /**
     * 监听小组删除事件
     *
     * @param event 小组删除事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTeamDeletedEvent(TeamDeletedEvent event) {
        try {
            List<Integer> teamIds = event.getTeamIds();
            logApplicationService.onTeamDeleted(teamIds, event.getBusinessData());
            log.info("小组删除日志记录成功: teamIds={}, operator={}, ip={}",
                    teamIds, ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理小组删除事件失败", e);
        }
    }

    /**
     * 监听部门创建事件
     *
     * @param event 部门创建事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeptCreatedEvent(DeptCreatedEvent event) {
        try {
            Dept dept = event.getDept();
            logApplicationService.onDeptCreated(dept, event.getBusinessData());
            log.info("部门创建日志记录成功: deptId={}, deptName={}, operator={}, ip={}",
                    dept.getDeptId(), dept.getDeptName(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理部门创建事件失败", e);
        }
    }

    /**
     * 监听部门更新事件
     *
     * @param event 部门更新事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeptUpdatedEvent(DeptUpdatedEvent event) {
        try {
            Dept dept = event.getDept();
            logApplicationService.onDeptUpdated(dept, event.getBusinessData());
            log.info("部门更新日志记录成功: deptId={}, deptName={}, operator={}, ip={}",
                    dept.getDeptId(), dept.getDeptName(),
                    ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理部门更新事件失败", e);
        }
    }

    /**
     * 监听部门删除事件
     *
     * @param event 部门删除事件
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeptDeletedEvent(DeptDeletedEvent event) {
        try {
            List<Integer> deptIds = event.getDeptIds();
            logApplicationService.onDeptDeleted(deptIds);
            log.info("部门删除日志记录成功: deptIds={}, operator={}, ip={}",
                    deptIds, ContextHolder.getUsername(), ContextHolder.getClientIp());
        } catch (Exception e) {
            log.error("处理部门删除事件失败", e);
        }
    }

}
