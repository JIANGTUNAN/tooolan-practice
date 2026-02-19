package com.tooolan.ddd.app.session.convert;

import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.app.session.response.LoginVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.UserBean;
import com.tooolan.ddd.domain.user.model.User;

/**
 * 会话转换器
 * 负责跨层对象转换
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public class SessionConvert {

    /**
     * 转换为登录结果 VO
     *
     * @param token 令牌
     * @param user  用户领域模型
     * @return 登录结果 VO
     */
    public static LoginVo toLoginVo(String token, User user) {
        if (user == null) {
            return null;
        }
        LoginVo vo = new LoginVo();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickName());
        return vo;
    }

    /**
     * 转换为登录状态 VO（已登录状态）
     *
     * @param user  用户领域模型
     * @param token 身份令牌
     * @return 登录状态 VO
     */
    public static LoginStatusVo toStatusVo(User user, String token) {
        if (user == null) {
            return toNotLoggedInVo();
        }
        LoginStatusVo vo = new LoginStatusVo();
        vo.setLoggedIn(true);
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickName());
        vo.setToken(token);
        return vo;
    }

    /**
     * 从用户上下文转换为登录状态 VO（无需查询数据库）
     *
     * @param userBean 用户上下文
     * @return 登录状态 VO
     */
    public static LoginStatusVo toStatusVo(UserBean userBean) {
        LoginStatusVo vo = new LoginStatusVo();
        vo.setLoggedIn(true);
        vo.setUserId(userBean.getUserId());
        vo.setUsername(userBean.getUsername());
        vo.setNickname(userBean.getNickname());
        vo.setToken(ContextHolder.getToken());
        return vo;
    }

    /**
     * 创建未登录状态的 VO
     *
     * @return 未登录状态的 VO
     */
    public static LoginStatusVo toNotLoggedInVo() {
        LoginStatusVo vo = new LoginStatusVo();
        vo.setLoggedIn(false);
        return vo;
    }

}
