package com.tooolan.ddd.api.session;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.session.request.LoginDTO;
import com.tooolan.ddd.app.session.SessionApplicationService;
import com.tooolan.ddd.app.session.convert.SessionAppConverter;
import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.app.session.response.LoginVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.session.model.UserBean;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户会话 控制器
 * 提供登录、登出、登录状态查询等接口
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionApplicationService sessionApplicationService;
    private final SessionAppConverter sessionAppConverter;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应，包含 token
     */
    @PostMapping("/login")
    public ResultVo<LoginVo> login(@Validated @RequestBody LoginDTO request) {
        // 1. 验证密码，获取用户信息
        User user = sessionApplicationService.authenticate(request.getUsername(), request.getPassword());

        // 2. 注册 Sa-Token
        StpUtil.login(user.getId());
        StpUtil.getSession().set(UserBean.Fields.username, user.getUsername());
        StpUtil.getSession().set(UserBean.Fields.nickname, user.getNickName());

        // 3. 初始化业务上下文（供后续事件监听等使用）
        UserBean userBean = new UserBean(user.getId(), user.getUsername(), user.getNickName());
        ContextHolder.setContext(userBean);

        // 4. 发布登录事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(UserLoginEvent.of(user, request));

        // 5. 返回结果
        LoginVo vo = sessionAppConverter.toLoginVo(StpUtil.getTokenValue(), user);
        return ResultVo.success(vo);
    }

    /**
     * 用户登出
     *
     * @return 操作结果
     */
    @SaCheckLogin
    @PostMapping("/logout")
    public ResultVo<Void> logout() {
        StpUtil.logout();
        return ResultVo.success();
    }

    /**
     * 获取登录状态
     *
     * @return 登录状态响应
     */
    @GetMapping("/status")
    public ResultVo<LoginStatusVo> getStatus() {
        LoginStatusVo vo = sessionApplicationService.getStatus();
        return ResultVo.success(vo);
    }

}
