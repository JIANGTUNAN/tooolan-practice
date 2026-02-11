package com.tooolan.ddd.api.dept.dto;

import com.tooolan.ddd.app.dept.bo.DeptCreateBO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门创建请求 DTO
 * 继承 DeptCreateBO 并通过重写 getter 方法添加字段校验
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptCreateRequest extends DeptCreateBO {

    /**
     * 获取部门名称，带非空校验
     *
     * @return 部门名称
     */
    @Override
    @NotBlank(message = "部门名称不能为空")
    public String getDeptName() {
        return super.getDeptName();
    }

    /**
     * 获取部门编码，带非空校验
     *
     * @return 部门编码
     */
    @Override
    @NotBlank(message = "部门编码不能为空")
    public String getDeptCode() {
        return super.getDeptCode();
    }
}
