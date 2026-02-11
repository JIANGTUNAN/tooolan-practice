package com.tooolan.ddd.app.user.bo;

import lombok.Data;

/**
 * 用户转移业务对象
 * 定义用户转移小组所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class UserTransferBO {

    /**
     * 目标小组ID
     */
    private Integer targetTeamId;
}
