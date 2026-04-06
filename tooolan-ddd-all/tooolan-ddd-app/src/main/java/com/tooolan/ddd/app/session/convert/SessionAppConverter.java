package com.tooolan.ddd.app.session.convert;

import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.app.session.response.LoginVo;
import com.tooolan.ddd.domain.session.model.UserBean;
import com.tooolan.ddd.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 会话应用层转换器
 * 负责登录状态相关的对象转换
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Mapper(componentModel = "spring")
public interface SessionAppConverter {

    /**
     * 将领域模型转换为登录结果视图对象
     *
     * @param token 登录令牌
     * @param user  用户领域模型
     * @return 登录结果视图对象
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.nickName", target = "nickname")
    LoginVo toLoginVo(String token, User user);

    /**
     * 将领域模型转换为登录状态视图对象
     *
     * @param user  用户领域模型
     * @param token 登录令牌
     * @return 登录状态视图对象
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.nickName", target = "nickname")
    @Mapping(target = "loggedIn", constant = "true")
    LoginStatusVo toStatusVo(User user, String token);

    /**
     * 将用户上下文转换为登录状态视图对象
     *
     * @param userBean 用户上下文
     * @return 登录状态视图对象
     */
    @Mapping(target = "loggedIn", constant = "true")
    @Mapping(source = "userBean.userId", target = "userId")
    @Mapping(source = "userBean.username", target = "username")
    @Mapping(source = "userBean.nickname", target = "nickname")
    @Mapping(target = "token", expression = "java(com.tooolan.ddd.domain.common.context.ContextHolder.getToken())")
    LoginStatusVo toStatusVo(UserBean userBean);

    /**
     * 创建未登录状态的视图对象
     *
     * @return 未登录状态视图对象
     */
    default LoginStatusVo toNotLoggedInVo() {
        LoginStatusVo vo = new LoginStatusVo();
        vo.setLoggedIn(false);
        return vo;
    }

}
