package com.tooolan.ddd.api.dept.request;

import com.tooolan.ddd.app.dept.request.UpdateDeptBo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateDeptDTO extends UpdateDeptBo {

    @NotNull(message = "部门ID不能为空")
    @Override
    public Integer getDeptId() {
        return super.getDeptId();
    }
}
