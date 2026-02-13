package com.tooolan.ddd.infra.persistence.user.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;
import com.tooolan.ddd.infra.persistence.user.converter.UserConverter;
import com.tooolan.ddd.infra.persistence.user.entity.SysUserEntity;
import com.tooolan.ddd.infra.persistence.user.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户 仓储实现
 * 实现用户领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
public class UserRepositoryImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements UserRepository {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息，不存在时返回空
     */
    @Override
    public Optional<User> getUser(Integer userId) {
        return super.getOptById(userId)
                .map(UserConverter::toDomain);
    }

    /**
     * 分页查询用户信息
     * 支持按用户账户、昵称、邮箱、备注进行模糊查询
     * 支持按创建时间范围进行筛选
     *
     * @param pageUserParam 分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageQueryResult<User> pageUser(PageUserParam pageUserParam) {
        IPage<User> page = super.lambdaQuery()
                .like(StrUtil.isNotBlank(pageUserParam.getUsername()), SysUserEntity::getUserName, pageUserParam.getUsername())
                .like(StrUtil.isNotBlank(pageUserParam.getNickName()), SysUserEntity::getNickName, pageUserParam.getNickName())
                .like(StrUtil.isNotBlank(pageUserParam.getEmail()), SysUserEntity::getEmail, pageUserParam.getEmail())
                .like(StrUtil.isNotBlank(pageUserParam.getRemark()), SysUserEntity::getRemark, pageUserParam.getRemark())
                .ge(ObjUtil.isNotNull(pageUserParam.getCreatedAtStart()), SysUserEntity::getCreatedAt, pageUserParam.getCreatedAtStart())
                .le(ObjUtil.isNotNull(pageUserParam.getCreatedAtEnd()), SysUserEntity::getCreatedAt, pageUserParam.getCreatedAtEnd())
                .page(PageDTO.of(pageUserParam.getPageNum(), pageUserParam.getPageSize()))
                .convert(UserConverter::toDomain);

        PageQueryResult<User> pageQueryResult = new PageQueryResult<>();
        BeanUtil.copyProperties(page, pageQueryResult);
        return pageQueryResult;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息，不存在时返回空
     */
    @Override
    public Optional<User> getUserByUsername(String username) {
        return super.lambdaQuery()
                .eq(SysUserEntity::getUserName, username)
                .oneOpt()
                .map(UserConverter::toDomain);
    }

    /**
     * 保存用户（新增）
     * 保存成功后会将生成的 ID 回填到 user 对象中
     *
     * @param user 用户领域模型
     * @return 是否保存成功
     */
    @Override
    public boolean save(User user) {
        SysUserEntity entity = UserConverter.toEntity(user);
        boolean saved = super.save(entity);
        if (saved) {
            user.setId(entity.getUserId());
        }
        return saved;
    }

}
