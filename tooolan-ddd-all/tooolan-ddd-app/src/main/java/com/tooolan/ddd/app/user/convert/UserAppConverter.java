package com.tooolan.ddd.app.user.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.user.request.PageUserBo;
import com.tooolan.ddd.app.user.request.SaveUserBo;
import com.tooolan.ddd.app.user.request.UpdateUserBo;
import com.tooolan.ddd.app.user.response.UserVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 用户应用层转换器
 * 负责业务类 ↔ 领域模型 ↔ 视图对象的转换
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Mapper(componentModel = "spring")
public interface UserAppConverter {

    /**
     * 将领域模型转换为视图对象
     *
     * @param user 领域模型
     * @return 视图对象
     */
    @Mapping(target = "teamName", ignore = true)
    @Mapping(source = "id", target = "userId")
    UserVo toVo(User user);

    /**
     * 将领域模型列表转换为视图对象列表
     *
     * @param users 领域模型列表
     * @return 视图对象列表
     */
    List<UserVo> toVoList(List<User> users);

    /**
     * 将保存用户业务类转换为领域模型
     *
     * @param bo 保存用户业务类
     * @return 领域模型
     */
    @Mapping(target = "id", ignore = true)
    User toSaveDomain(SaveUserBo bo);

    /**
     * 将更新用户业务类转换为领域模型
     *
     * @param bo 更新用户业务类
     * @return 领域模型
     */
    @Mapping(source = "userId", target = "id")
    User toUpdateDomain(UpdateUserBo bo);

    /**
     * 将分页用户业务类转换为查询参数类
     *
     * @param bo 分页用户业务类
     * @return 查询参数类
     */
    PageUserParam toParam(PageUserBo bo);

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    default PageVo<UserVo> toPageVo(PageQueryResult<User> result) {
        return PageVo.of(result, this::toVo);
    }

}
