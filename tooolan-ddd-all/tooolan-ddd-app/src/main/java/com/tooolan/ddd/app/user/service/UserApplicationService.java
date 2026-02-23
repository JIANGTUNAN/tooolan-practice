package com.tooolan.ddd.app.user.service;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.user.convert.UserConvert;
import com.tooolan.ddd.app.user.request.DeleteUserBo;
import com.tooolan.ddd.app.user.request.PageUserBo;
import com.tooolan.ddd.app.user.request.SaveUserBo;
import com.tooolan.ddd.app.user.request.UpdateUserBo;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.domain.common.constant.FieldClearValues;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.session.service.PasswordEncryptor;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.user.event.UserCreatedEvent;
import com.tooolan.ddd.domain.user.event.UserDeletedEvent;
import com.tooolan.ddd.domain.user.event.UserUpdatedEvent;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;
import com.tooolan.ddd.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordEncryptor passwordEncryptor;
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
     * @throws NotFoundException     指定的小组不存在时抛出
     * @throws BusinessRuleException 用户名已存在或保存失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUser(SaveUserBo bo) throws BusinessRuleException {
        // 转换为领域模型
        User user = UserConvert.toDomain(bo);

        // 处理密码：RSA 解密 + BCrypt 哈希
        String sha256Password = passwordEncryptor.decryptPassword(bo.getPassword());
        String encodedPassword = passwordEncryptor.encodePassword(sha256Password);
        user.setPassword(encodedPassword);

        Team team = null;
        // 应用层校验：如果指定了小组，校验小组是否存在
        if (ObjUtil.isNotNull(bo.getTeamId())) {
            team = teamRepository.getTeam(bo.getTeamId())
                    .orElseThrow(() -> new NotFoundException("指定的小组不存在"));
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
     * @throws NotFoundException     用户不存在或目标小组不存在时抛出
     * @throws BusinessRuleException 用户名被修改、小组不可用或小组已满员时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUser(UpdateUserBo bo) throws BusinessRuleException {
        // 1. 查询现有用户
        User existingUser = userRepository.getUser(bo.getUserId())
                .orElseThrow(() -> new NotFoundException("用户不存在"));
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
                    .orElseThrow(() -> new NotFoundException("指定的小组不存在"));
        }

        // 5. 调用领域服务
        userDomainService.updateUser(existingUser, updatedUser, newTeam);

        // 6. 发布用户更新事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(UserUpdatedEvent.of(updatedUser, bo));
    }

    /**
     * 批量删除用户
     *
     * @param bo 删除用户 BO
     * @throws BusinessRuleException 包含管理员ID、用户不存在或删除失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUsers(DeleteUserBo bo) throws BusinessRuleException {
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
