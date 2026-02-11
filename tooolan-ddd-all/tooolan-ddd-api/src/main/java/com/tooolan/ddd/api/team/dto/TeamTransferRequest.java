package com.tooolan.ddd.api.team.dto;

import com.tooolan.ddd.app.team.bo.TeamTransferBO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小组转移请求 DTO
 * 继承 TeamTransferBO 并通过重写 getter 方法添加字段校验
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeamTransferRequest extends TeamTransferBO {

    /**
     * 获取目标部门ID，带非空校验
     *
     * @return 目标部门ID
     */
    @Override
    @NotNull(message = "目标部门不能为空")
    public Integer getTargetDeptId() {
        return super.getTargetDeptId();
    }
}
