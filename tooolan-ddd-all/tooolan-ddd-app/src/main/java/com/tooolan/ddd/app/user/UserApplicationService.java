package com.tooolan.ddd.app.user;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.app.common.response.OptionVo;
import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.user.convert.UserConvert;
import com.tooolan.ddd.app.user.request.*;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.domain.common.constant.FieldClearValues;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.session.exception.SessionException;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.exception.TeamException;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.user.constant.UserErrorCode;
import com.tooolan.ddd.domain.user.event.UserCreatedEvent;
import com.tooolan.ddd.domain.user.event.UserDeletedEvent;
import com.tooolan.ddd.domain.user.event.UserPasswordChangedEvent;
import com.tooolan.ddd.domain.user.event.UserUpdatedEvent;
import com.tooolan.ddd.domain.user.exception.UserException;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;
import com.tooolan.ddd.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户信息 应用服务
 * 提供用户相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserDomainService userDomainService;
    private final ApplicationEventPublisher eventPublisher;


    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户视图对象
     */
    public Optional<UserVo> getUserById(Integer userId) {
        Optional<User> user = userRepository.getUser(userId);
        return user.map(UserConvert::toVo);
    }

    /**
     * 获取用户选项列表
     * 用于下拉框选择，支持按昵称模糊查询
     *
     * @param nickName 昵称（可选，模糊匹配）
     * @return 用户选项列表
     */
    public OptionVo<Integer> getUserOptions(String nickName) {
        List<User> users = userRepository.listUserOptions(nickName);
        return OptionVo.from(users, User::getId, User::getNickName);
    }

    /**
     * 分页查询用户信息
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    public PageVo<UserVo> pageUser(PageUserBo dto) {
        PageUserParam pageUserParam = UserConvert.toParam(dto);
        PageQueryResult<User> pageQueryResult = userRepository.pageUser(pageUserParam);
        return UserConvert.toPageVo(pageQueryResult);
    }

    /**
     * 保存用户
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 保存用户 BO
     * @throws TeamException    指定的小组不存在时抛出
     * @throws UserException    用户名已存在或保存失败时抛出
     * @throws SessionException 密码解密失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUser(SaveUserBo bo) throws SessionException {
        // 转换为领域模型
        User user = UserConvert.toDomain(bo);
        Team team = null;
        // 应用层校验：如果指定了小组，校验小组是否存在
        if (ObjUtil.isNotNull(bo.getTeamId())) {
            team = teamRepository.getTeam(bo.getTeamId())
                    .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        }
        // 调用领域服务保存用户（主键会通过引用回填）
        userDomainService.saveUser(user, team);
        // 发布用户创建事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(UserCreatedEvent.of(user, bo));
    }

    /**
     * 更新用户
     * 支持部分字段更新和字段清空功能
     *
     * @param bo 更新用户 BO
     * @throws UserException 用户不存在或用户名被修改时抛出
     * @throws TeamException 目标小组不存在、不可用或已满员时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUser(UpdateUserBo bo) {
        // 1. 查询现有用户
        User existingUser = userRepository.getUser(bo.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
        // 2. 转换为领域模型（传入现有用户实现部分更新）
        User updatedUser = UserConvert.toUpdateDomain(bo, existingUser);
        // 3. 处理字段清空
        this.processClearFields(updatedUser);

        // 4. 如果修改了小组，校验小组存在性
        Team newTeam = null;
        Integer oldTeamId = existingUser.getTeamId();
        Integer newTeamId = updatedUser.getTeamId();
        boolean teamChanged = ObjUtil.notEqual(oldTeamId, newTeamId);

        if (teamChanged && newTeamId != null) {
            newTeam = teamRepository.getTeam(newTeamId)
                    .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        }

        // 5. 调用领域服务
        userDomainService.updateUser(existingUser, updatedUser, newTeam);

        // 6. 发布用户更新事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(UserUpdatedEvent.of(updatedUser, bo));
    }

    /**
     * 修改用户密码
     * 包含用户存在性校验、密码验证和事件发布
     *
     * @param bo 修改密码 BO
     * @throws UserException    用户不存在、原密码错误、新旧密码相同或修改失败时抛出
     * @throws SessionException 密码解密失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void changePassword(ChangePasswordBo bo) throws SessionException {
        // 1. 查询用户
        User user = userRepository.getUser(bo.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));

        // 2. 调用领域服务修改密码（包含原密码验证、新旧密码相同校验）
        userDomainService.changePassword(user, bo.getOldPassword(), bo.getNewPassword());

        // 3. 发布用户密码修改事件（不携带业务数据，因为只包含敏感密码字段）
        eventPublisher.publishEvent(UserPasswordChangedEvent.of(user));
    }

    /**
     * 批量删除用户
     *
     * @param bo 删除用户 BO
     * @throws UserException 包含管理员ID、用户不存在或删除失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUsers(DeleteUserBo bo) {
        // 调用领域服务执行删除
        userDomainService.deleteUsers(bo.getUserIds());
        // 发布用户删除事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(UserDeletedEvent.of(bo.getUserIds(), bo));
    }

    /**
     * 处理字段清空逻辑
     * 将约定值转换为 null
     *
     * @param user 用户领域模型
     */
    private void processClearFields(User user) {
        if (ObjUtil.isNotNull(user.getEmail())) {
            user.setEmail(FieldClearValues.processField(user.getEmail()));
        }
        if (ObjUtil.isNotNull(user.getTeamId())) {
            user.setTeamId(FieldClearValues.processField(user.getTeamId()));
        }
        if (ObjUtil.isNotNull(user.getRemark())) {
            user.setRemark(FieldClearValues.processField(user.getRemark()));
        }
    }

}
