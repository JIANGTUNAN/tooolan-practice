package com.tooolan.ddd.api.team.dto;

import com.tooolan.ddd.app.team.bo.TeamCreateBO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小组创建请求 DTO
 * 继承 TeamCreateBO 并通过重写 getter 方法添加字段校验
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamCreateRequest extends TeamCreateBO {

    /**
     * 获取所属部门ID，带非空校验
     *
     * @return 部门ID
     */
    @Override
    @NotNull(message = "所属部门不能为空")
    public Integer getDeptId() {
        return super.getDeptId();
    }

    /**
     * 获取小组名称，带非空校验
     *
     * @return 小组名称
     */
    @Override
    @NotBlank(message = "小组名称不能为空")
    public String getTeamName() {
        return super.getTeamName();
    }

    /**
     * 获取小组编码，带非空校验
     *
     * @return 小组编码
     */
    @Override
    @NotBlank(message = "小组编码不能为空")
    public String getTeamCode() {
        return super.getTeamCode();
    }
}
