package com.tooolan.ddd.infra.persistence.user.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.identifier.UserId;
import com.tooolan.ddd.domain.user.model.Email;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.model.Username;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.infra.persistence.user.converter.UserConverter;
import com.tooolan.ddd.infra.persistence.user.entity.SysUserEntity;
import com.tooolan.ddd.infra.persistence.user.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户 仓储实现
 * 实现用户领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SysUserMapper userMapper;
    private final UserConverter userConverter;

    @Override
    public void save(User user) {
        SysUserEntity entity = userConverter.toEntity(user);

        if (user.isNew()) {
            // 新增：插入数据
            userMapper.insert(entity);
            // 回设ID到领域对象
            if (entity.getUserId() != null) {
                user.assignId(UserId.of(entity.getUserId()));
            }
        } else {
            // 更新：更新数据
            userMapper.updateById(entity);
        }
    }

    @Override
    public void remove(UserId userId) {
        if (userId != null) {
            userMapper.deleteById(userId.getValue());
        }
    }

    @Override
    public Optional<User> findById(UserId userId) {
        if (userId == null) {
            return Optional.empty();
        }
        SysUserEntity entity = userMapper.selectById(userId.getValue());
        return Optional.ofNullable(userConverter.toDomain(entity));
    }

    @Override
    public List<User> findAll() {
        List<SysUserEntity> entities = userMapper.selectList(null);
        return entities.stream()
                .map(userConverter::toDomain)
                .toList();
    }

    @Override
    public boolean existsByUsername(Username username) {
        if (username == null) {
            return false;
        }
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getUserName, username.getValue());
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        if (username == null) {
            return Optional.empty();
        }
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getUserName, username.getValue());
        SysUserEntity entity = userMapper.selectOne(wrapper);
        return Optional.ofNullable(userConverter.toDomain(entity));
    }

    @Override
    public List<User> findByTeam(TeamId teamId) {
        if (teamId == null) {
            return List.of();
        }
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getTeamId, teamId.getValue());
        List<SysUserEntity> entities = userMapper.selectList(wrapper);
        return entities.stream()
                .map(userConverter::toDomain)
                .toList();
    }

    @Override
    public boolean isUsernameUnique(Username username, UserId excludeUserId) {
        if (username == null) {
            return false;
        }
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getUserName, username.getValue());
        if (excludeUserId != null) {
            wrapper.ne(SysUserEntity::getUserId, excludeUserId.getValue());
        }
        return userMapper.selectCount(wrapper) == 0;
    }

    @Override
    public long countByTeam(TeamId teamId) {
        if (teamId == null) {
            return 0;
        }
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getTeamId, teamId.getValue());
        return userMapper.selectCount(wrapper);
    }
}
