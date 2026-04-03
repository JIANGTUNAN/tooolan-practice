package com.tooolan.ddd.api.dept.request;

import com.tooolan.ddd.app.dept.request.SaveDeptBo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaveDeptDTO extends SaveDeptBo {

    @NotBlank(message = "部门名称不能为空")
    @Override
    public String getDeptName() {
        return super.getDeptName();
    }

    @NotBlank(message = "部门编码不能为空")
    @Override
    public String getDeptCode() {
        return super.getDeptCode();
    }
}
