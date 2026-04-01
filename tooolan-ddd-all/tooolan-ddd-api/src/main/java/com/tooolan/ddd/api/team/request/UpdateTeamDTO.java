package com.tooolan.ddd.api.team.request;

import com.tooolan.ddd.app.team.request.UpdateTeamBo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新小组 DTO
 * 用于 API 层接收小组更新请求，包含参数校验
 *
 * @author tooolan
 * @since 2026年4月1日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTeamDTO extends UpdateTeamBo {

    @Override
    @NotNull(message = "小组ID不能为空")
    public Integer getTeamId() {
        return super.getTeamId();
    }

    @Override
    @Size(max = 100, message = "小组名称长度不能超过100个字符")
    public String getTeamName() {
        return super.getTeamName();
    }

}
