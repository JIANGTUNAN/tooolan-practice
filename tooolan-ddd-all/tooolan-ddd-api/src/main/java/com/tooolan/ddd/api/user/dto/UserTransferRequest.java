package com.tooolan.ddd.api.user.dto;

import com.tooolan.ddd.app.user.bo.UserTransferBO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户转移请求 DTO
 * 继承 UserTransferBO 并通过重写 getter 方法添加字段校验
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTransferRequest extends UserTransferBO {

    /**
     * 获取目标小组ID，带非空校验
     *
     * @return 目标小组ID
     */
    @Override
    @NotNull(message = "目标小组不能为空")
    public Integer getTargetTeamId() {
        return super.getTargetTeamId();
    }
}
