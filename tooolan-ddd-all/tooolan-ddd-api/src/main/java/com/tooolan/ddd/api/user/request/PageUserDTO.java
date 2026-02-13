package com.tooolan.ddd.api.user.request;

import com.tooolan.ddd.app.user.request.PageUserBo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 分页查询用户信息 DTO
 * 继承 PageUserBo，复用分页和查询条件字段
 * 添加分页参数校验
 *
 * @author tooolan
 * @since 2026年2月13日
 */
public class PageUserDTO extends PageUserBo {

    @Override
    @NotNull(message = "当前页码数不能为空")
    @Min(value = 1, message = "当前页码数必须大于0")
    public Integer getPageNum() {
        return super.getPageNum();
    }

    @Override
    @NotNull(message = "每页显示条目数不能为空")
    @Min(value = 1, message = "每页显示条目数必须大于0")
    @Max(value = 500, message = "每页显示条目数不能超过500")
    public Integer getPageSize() {
        return super.getPageSize();
    }

}
