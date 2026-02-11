package com.tooolan.ddd.app.user.service;

import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.user.bo.*;
import com.tooolan.ddd.app.user.vo.UserVo;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.identifier.UserId;
import com.tooolan.ddd.domain.common.pagination.Page;
import com.tooolan.ddd.domain.common.pagination.PageRequest;
import com.tooolan.ddd.domain.user.model.Email;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.domain.user.service.UserDomainService;
import com.tooolan.ddd.app.user.assembler.UserAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户应用服务
 * 提供用户相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final UserAssembler userAssembler;

    /**
     * 创建用户
     *
     * @param bo 用户创建业务对象
     * @return 用户视图对象
     */
    @Transactional(rollbackFor = Exception.class)
    public UserVo createUser(UserCreateBO bo) {
        // 创建用户领域对象
        User user = userAssembler.toDomain(bo);

        // 校验用户名唯一性
        userDomainService.validateUsernameUnique(user.getUsername(), null);

        // 验证业务规则
        user.validate();

        // 保存用户
        userRepository.save(user);

        return userAssembler.toVo(user);
    }

    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     * @param bo     用户更新业务对象
     * @return 用户视图对象
     */
    @Transactional(rollbackFor = Exception.class)
    public UserVo updateUser(Integer userId, UserUpdateBO bo) {
        UserId id = UserId.of(userId);

        // 查询用户
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("用户", userId));

        // 更新用户信息
        Email email = bo.getEmail() != null ? Email.of(bo.getEmail()) : user.getEmail();
        user.updateProfile(bo.getNickName(), email);

        if (bo.getRemark() != null) {
            user.setRemark(bo.getRemark());
        }

        // 验证业务规则
        user.validate();

        // 保存用户
        userRepository.save(user);

        return userAssembler.toVo(user);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer userId) {
        UserId id = UserId.of(userId);

        // 检查用户是否存在
        if (!userRepository.findById(id).isPresent()) {
            throw new NotFoundException("用户", userId);
        }

        // 删除用户
        userRepository.remove(id);
    }

    /**
     * 查询用户详情
     *
     * @param userId 用户ID
     * @return 用户视图对象
     */
    public UserVo getUserById(Integer userId) {
        UserId id = UserId.of(userId);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("用户", userId));
        return userAssembler.toVo(user);
    }

    /**
     * 分页查询用户列表
     *
     * @param bo 查询业务对象
     * @return 分页视图对象
     */
    public PageVo<UserVo> getUserPage(UserQueryBO bo) {
        // 构建分页请求
        PageRequest pageRequest = PageRequest.of(
                bo.getPageNum(),
                bo.getPageSize()
        );

        // 查询所有用户（简化实现，生产环境应在仓储层实现分页查询）
        List<User> allUsers = userRepository.findAll();

        // 筛选条件
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> {
                    boolean match = true;
                    if (bo.getUsername() != null) {
                        match = match && user.getUsername().getValue().contains(bo.getUsername());
                    }
                    if (bo.getNickName() != null) {
                        match = match && user.getNickName().contains(bo.getNickName());
                    }
                    if (bo.getEmail() != null) {
                        match = match && (user.getEmail() != null && user.getEmail().getValue().contains(bo.getEmail()));
                    }
                    if (bo.getTeamId() != null) {
                        match = match && user.getTeamId() != null && user.getTeamId().getValue().equals(bo.getTeamId());
                    }
                    return match;
                })
                .toList();

        // 内存分页（简化实现）
        int total = filteredUsers.size();
        int start = pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), total);

        List<User> pagedUsers = start < total ? filteredUsers.subList(start, end) : List.of();

        Page<User> page = Page.of(pagedUsers, total, pageRequest.getPageNum(), pageRequest.getPageSize());

        return userAssembler.toPageVo(page);
    }

    /**
     * 用户转移小组
     *
     * @param userId 用户ID
     * @param bo     转移业务对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void transferUser(Integer userId, UserTransferBO bo) {
        UserId id = UserId.of(userId);
        TeamId targetTeamId = TeamId.of(bo.getTargetTeamId());

        // 校验转移合法性
        userDomainService.validateUserTransfer(id, targetTeamId);

        // 查询用户
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("用户", userId));

        // 执行转移
        user.transferTo(targetTeamId);

        // 保存用户
        userRepository.save(user);
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteUsers(List<Integer> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }

        for (Integer userId : userIds) {
            UserId id = UserId.of(userId);
            if (userRepository.findById(id).isPresent()) {
                userRepository.remove(id);
            }
        }
    }

    /**
     * 根据小组查询用户
     *
     * @param teamId 小组ID
     * @return 用户视图对象列表
     */
    public List<UserVo> getUsersByTeam(Integer teamId) {
        TeamId id = TeamId.of(teamId);
        List<User> users = userRepository.findByTeam(id);
        return users.stream()
                .map(userAssembler::toVo)
                .toList();
    }
}
