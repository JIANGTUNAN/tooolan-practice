package com.tooolan.ddd.api.team.request;

import com.tooolan.ddd.app.team.request.SaveTeamBo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 保存小组 DTO
 * 用于 API 层接收小组保存请求，包含参数校验
 *
 * @author tooolan
 * @since 2026年2月24日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaveTeamDTO extends SaveTeamBo {

    @Override
    @NotBlank(message = "小组名称不能为空")
    @Size(max = 100, message = "小组名称长度不能超过100个字符")
    public String getTeamName() {
        return super.getTeamName();
    }

    @Override
    @NotBlank(message = "小组编码不能为空")
    @Size(max = 50, message = "小组编码长度不能超过50个字符")
    public String getTeamCode() {
        return super.getTeamCode();
    }

}
