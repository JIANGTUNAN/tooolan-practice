package com.tooolan.ddd.app.user.request;

import lombok.Data;

import java.util.List;

/**
 * 批量删除用户 BO
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Data
public class DeleteUserBo {

    /**
     * 用户ID列表
     */
    private List<Integer> userIds;

}
