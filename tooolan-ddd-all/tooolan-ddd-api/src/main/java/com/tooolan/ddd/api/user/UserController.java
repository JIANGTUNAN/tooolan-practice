package com.tooolan.ddd.api.user;

import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.user.request.PageUserDTO;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.app.user.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户信息管理 控制器
 * 提供用户相关的 REST API，包括用户的增删改查和转移操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/users")
public class UserController {

    private final UserApplicationService userApplicationService;


    /**
     * 根据ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/get/{userId}")
    public ResultVo<UserVo> get(@PathVariable Integer userId) {
        return userApplicationService.getUserById(userId)
                .map(ResultVo::success)
                .orElse(ResultVo.error(20404, "用户不存在"));
    }

    /**
     * 分页查询用户信息
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public ResultVo<PageVo<UserVo>> page(@Validated PageUserDTO dto) {
        PageVo<UserVo> pageVo = userApplicationService.pageUser(dto);
        return ResultVo.success(pageVo);
    }

}
