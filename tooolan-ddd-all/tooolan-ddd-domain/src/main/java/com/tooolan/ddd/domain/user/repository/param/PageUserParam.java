package com.tooolan.ddd.domain.user.repository.param;

import com.tooolan.ddd.domain.common.result.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 分页查询用户信息 查询条件
 *
 * @author tooolan
 * @since 2026年2月13日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageUserParam extends PageRequest {

    /**
     * 用户账户（模糊查询）
     */
    private String username;

    /**
     * 用户昵称（模糊查询）
     */
    private String nickName;

    /**
     * 邮箱（模糊查询）
     */
    private String email;

    /**
     * 备注信息（模糊查询）
     */
    private String remark;

    /**
     * 创建时间-开始（范围查询）
     */
    private LocalDateTime createdAtStart;

    /**
     * 创建时间-结束（范围查询）
     */
    private LocalDateTime createdAtEnd;

}
