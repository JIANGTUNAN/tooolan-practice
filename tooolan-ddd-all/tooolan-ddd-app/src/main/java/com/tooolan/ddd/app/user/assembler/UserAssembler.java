package com.tooolan.ddd.app.user.assembler;

import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.user.bo.UserCreateBO;
import com.tooolan.ddd.app.user.vo.UserVo;
import com.tooolan.ddd.domain.common.pagination.Page;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.user.model.Email;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.model.Username;
import org.springframework.stereotype.Component;

/**
 * 用户DTO组装器
 * 负责用户领域对象与BO/VO之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Component
public class UserAssembler {

    /**
     * 业务对象转领域对象
     *
     * @param bo 用户创建业务对象
     * @return 用户领域对象
     */
    public User toDomain(UserCreateBO bo) {
        Username username = Username.of(bo.getUsername());
        Email email = bo.getEmail() != null ? Email.of(bo.getEmail()) : null;
        TeamId teamId = TeamId.of(bo.getTeamId());

        return User.create(username, bo.getNickName(), email, teamId);
    }

    /**
     * 领域对象转视图对象
     *
     * @param user 用户领域对象
     * @return 用户视图对象
     */
    public UserVo toVo(User user) {
        if (user == null) {
            return null;
        }

        return new UserVo(
                user.getId() != null ? user.getId().getValue() : null,
                user.getUsername() != null ? user.getUsername().getValue() : null,
                user.getNickName(),
                user.getEmail() != null ? user.getEmail().getValue() : null,
                user.getTeamId() != null ? user.getTeamId().getValue() : null,
                null, // teamName - 需要从仓储获取
                user.getRemark(),
                null, // createdBy - 从审计字段获取
                null, // createdAt - 从审计字段获取
                null, // updatedBy - 从审计字段获取
                null  // updatedAt - 从审计字段获取
        );
    }

    /**
     * 分页结果转换
     *
     * @param page 领域分页结果
     * @return 分页视图对象
     */
    public PageVo<UserVo> toPageVo(Page<User> page) {
        return PageVo.of(
                page.getRecords().stream()
                        .map(this::toVo)
                        .toList(),
                page.getTotal(),
                page.getPageNum(),
                page.getPageSize()
        );
    }
}
