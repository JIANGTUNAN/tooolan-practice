package com.tooolan.ddd.app.user.convert;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.user.request.PageUserBo;
import com.tooolan.ddd.app.user.request.SaveUserBo;
import com.tooolan.ddd.app.user.request.UpdateUserBo;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;

import java.util.List;

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
        user.setEmail(bo.getEmail());
        user.setTeamId(bo.getTeamId());
        user.setRemark(bo.getRemark());
        return user;
    }

    /**
     * 将 UpdateUserBo 转换为 User 领域模型（用于更新）
     * 采用部分更新策略：只覆盖传入的字段，未传入的字段保持原值
     *
     * @param bo           更新用户 BO
     * @param existingUser 现有用户数据（用于合并）
     * @return 用户领域模型
     */
    public static User toUpdateDomain(UpdateUserBo bo, User existingUser) {
        if (bo == null) {
            return null;
        }
        if (existingUser == null) {
            throw new IllegalArgumentException("现有用户数据不能为空");
        }

        User user = new User();
        user.setId(bo.getUserId());
        user.setUsername(existingUser.getUsername()); // 保持原用户名

        // 部分更新：只覆盖传入的字段（非 null）
        user.setNickName(ObjUtil.defaultIfBlank(bo.getNickName(), existingUser.getNickName()));
        user.setEmail(ObjUtil.defaultIfBlank(bo.getEmail(), existingUser.getEmail()));
        user.setTeamId(ObjUtil.defaultIfNull(bo.getTeamId(), existingUser.getTeamId()));
        user.setRemark(ObjUtil.defaultIfBlank(bo.getRemark(), existingUser.getRemark()));

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
        if (result == null) {
            PageVo<UserVo> vo = new PageVo<>();
            vo.setPageNum(0);
            vo.setPageSize(0);
            vo.setPages(0);
            vo.setTotal(0);
            vo.setRecords(List.of());
            return vo;
        }
        List<UserVo> vos = result.getRecords().stream()
                .map(UserConvert::toVo)
                .toList();
        PageVo<UserVo> vo = new PageVo<>();
        vo.setPageNum(result.getPageNum());
        vo.setPageSize(result.getPageSize());
        vo.setPages(result.getPages());
        vo.setTotal(result.getTotal());
        vo.setRecords(vos);
        return vo;
    }

}
