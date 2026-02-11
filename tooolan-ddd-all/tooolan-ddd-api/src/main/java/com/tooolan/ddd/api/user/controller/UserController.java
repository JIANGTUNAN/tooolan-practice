package com.tooolan.ddd.api.user.controller;

import com.tooolan.ddd.api.common.ResultVO;
import com.tooolan.ddd.api.user.dto.*;
import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.user.service.UserApplicationService;
import com.tooolan.ddd.app.user.vo.UserVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 * 提供用户相关的 REST API，包括用户的增删改查和转移操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService userApplicationService;

    /**
     * 创建用户
     *
     * @param request 用户创建请求
     * @return 创建成功的用户信息
     */
    @PostMapping
    public ResultVO<UserVo> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserVo vo = userApplicationService.createUser(request);
        return ResultVO.success("用户创建成功", vo);
    }

    /**
     * 根据ID查询用户详情
     *
     * @param userId 用户ID
     * @return 用户详细信息
     */
    @GetMapping("/{userId}")
    public ResultVO<UserVo> getUserById(@PathVariable Integer userId) {
        UserVo vo = userApplicationService.getUserById(userId);
        return ResultVO.success(vo);
    }

    /**
     * 分页查询用户列表
     *
     * @param request 查询请求条件
     * @return 分页用户列表
     */
    @GetMapping
    public ResultVO<PageVo<UserVo>> getUserPage(UserQueryRequest request) {
        PageVo<UserVo> vo = userApplicationService.getUserPage(request);
        return ResultVO.success(vo);
    }

    /**
     * 更新用户信息
     *
     * @param userId  用户ID
     * @param request 更新请求
     * @return 更新后的用户信息
     */
    @PutMapping("/{userId}")
    public ResultVO<UserVo> updateUser(
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody UserUpdateRequest request) {
        UserVo vo = userApplicationService.updateUser(userId, request);
        return ResultVO.success("用户更新成功", vo);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{userId}")
    public ResultVO<Void> deleteUser(@PathVariable("userId") Integer userId) {
        userApplicationService.deleteUser(userId);
        return ResultVO.success("用户删除成功", null);
    }

    /**
     * 用户转移小组
     *
     * @param userId  用户ID
     * @param request 转移请求
     * @return 操作结果
     */
    @PostMapping("/{userId}/transfer")
    public ResultVO<Void> transferUser(
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody UserTransferRequest request) {
        userApplicationService.transferUser(userId, request);
        return ResultVO.success("用户转移成功", null);
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    public ResultVO<Void> batchDeleteUsers(@RequestBody List<Integer> userIds) {
        userApplicationService.batchDeleteUsers(userIds);
        return ResultVO.success("批量删除成功", null);
    }

    /**
     * 查询小组下的用户
     *
     * @param teamId 小组ID
     * @return 用户列表
     */
    @GetMapping("/team/{teamId}")
    public ResultVO<List<UserVo>> getUsersByTeam(@PathVariable("teamId") Integer teamId) {
        List<UserVo> users = userApplicationService.getUsersByTeam(teamId);
        return ResultVO.success(users);
    }
}
