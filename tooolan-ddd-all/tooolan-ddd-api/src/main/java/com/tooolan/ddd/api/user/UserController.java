package com.tooolan.ddd.api.user;

import com.tooolan.ddd.app.user.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制器
 * 提供用户相关的 REST API，包括用户的增删改查和转移操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserApplicationService userApplicationService;

}
