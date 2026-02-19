package com.tooolan.ddd.api.session;

import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.session.request.LoginDTO;
import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.app.session.response.LoginVo;
import com.tooolan.ddd.app.session.service.SessionApplicationService;
import com.tooolan.ddd.domain.common.exception.DomainException;
import com.tooolan.ddd.domain.session.service.RsaKeyProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private final RsaKeyProvider rsaKeyProvider;


    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应，包含 token
     */
    @PostMapping("/login")
    public ResultVo<LoginVo> login(@Validated @RequestBody LoginDTO request) {
        LoginVo vo = sessionApplicationService.login(request);
        return ResultVo.success(vo);
    }

    /**
     * 用户登出
     *
     * @return 操作结果
     */
    @PostMapping("/logout")
    public ResultVo<Void> logout() {
        sessionApplicationService.logout();
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

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     * @throws DomainException 未登录时抛出
     */
    @GetMapping("/me")
    public ResultVo<LoginStatusVo> getCurrentUser() {
        LoginStatusVo vo = sessionApplicationService.getCurrentUser();
        return ResultVo.success(vo);
    }

    /**
     * 获取 RSA 公钥
     * 前端使用此公钥加密密码
     *
     * @return 公钥响应
     */
    @GetMapping("/public-key")
    public ResultVo<Map<String, String>> getPublicKey() {
        return ResultVo.success(Map.of("publicKey", rsaKeyProvider.getPublicKey()));
    }

}
