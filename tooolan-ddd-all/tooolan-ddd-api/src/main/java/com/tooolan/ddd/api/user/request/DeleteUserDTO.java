package com.tooolan.ddd.api.user.request;

import com.tooolan.ddd.app.user.request.DeleteUserBo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 批量删除用户 DTO
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteUserDTO extends DeleteUserBo {

    @Override
    @NotEmpty(message = "用户ID列表不能为空")
    public List<Integer> getUserIds() {
        return super.getUserIds();
    }

}
