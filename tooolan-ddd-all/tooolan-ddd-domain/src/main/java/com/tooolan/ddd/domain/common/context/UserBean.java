package com.tooolan.ddd.domain.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * 用户上下文信息 Bean
 * 用于存储和传递当前登录用户的基本信息，是一个纯粹的数据载体，不包含任何业务逻辑。
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class UserBean {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 会话ID（Token）
     */
    private String sessionId;

}
