package com.tooolan.ddd.app.user.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.user.request.PageUserBo;
import com.tooolan.ddd.app.user.request.SaveUserBo;
import com.tooolan.ddd.app.user.request.UpdateUserBo;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;

/**
 * 用户转换器
 * 负责跨层对象转换
 *
 * @author tooolan
 * @since 2026年2月12日
 */
public class UserConvert {

    /**
     * 将 SaveUserBo 转换为 User 领域模型
     *
     * @param bo 保存用户 BO
     * @return 用户领域模型
     */
    public static User toDomain(SaveUserBo bo) {
        if (bo == null) {
            return null;
        }
        User user = new User();
        user.setUsername(bo.getUsername());
        user.setNickName(bo.getNickName());
        user.setPassword(bo.getPassword());
        user.setEmail(bo.getEmail());
        user.setTeamId(bo.getTeamId());
        user.setRemark(bo.getRemark());
        return user;
    }

    /**
     * 将 UpdateUserBo 转换为 User 领域模型（用于更新）
     * 直接设置所有字段，MyBatis Plus updateById 只更新非 null 字段
     * - null = 不更新该字段
     * - "" (空字符串) = 清空该字段
     *
     * @param bo 更新用户 BO
     * @return 用户领域模型
     */
    public static User toUpdateDomain(UpdateUserBo bo) {
        if (bo == null) {
            return null;
        }
        User user = new User();
        user.setId(bo.getUserId());
        user.setNickName(bo.getNickName());
        user.setEmail(bo.getEmail());
        user.setTeamId(bo.getTeamId());
        user.setRemark(bo.getRemark());
        return user;
    }

    /**
     * 将领域模型转换为视图对象
     * teamName 暂时设置为 null
     *
     * @param user 领域模型
     * @return 视图对象
     */
    public static UserVo toVo(User user) {
        if (user == null) {
            return null;
        }
        UserVo vo = new UserVo();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickName(user.getNickName());
        vo.setEmail(user.getEmail());
        vo.setTeamId(user.getTeamId());
        vo.setTeamName(null);
        vo.setRemark(user.getRemark());
        return vo;
    }

    /**
     * 将 BO 转换为 Param
     *
     * @param bo BO 对象
     * @return Param 对象
     * @throws IllegalStateException 当分页参数为 null 时抛出
     */
    public static PageUserParam toParam(PageUserBo bo) {
        if (bo == null) {
            return null;
        }
        PageUserParam param = new PageUserParam();
        param.setPageNum(bo.getPageNum());
        param.setPageSize(bo.getPageSize());
        param.setUsername(bo.getUsername());
        param.setNickName(bo.getNickName());
        param.setEmail(bo.getEmail());
        param.setRemark(bo.getRemark());
        param.setCreatedAtStart(bo.getCreatedAtStart());
        param.setCreatedAtEnd(bo.getCreatedAtEnd());
        return param;
    }

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    public static PageVo<UserVo> toPageVo(PageQueryResult<User> result) {
        return PageVo.of(result, UserConvert::toVo);
    }

}
