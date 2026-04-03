package com.tooolan.ddd.api.dept.request;

import com.tooolan.ddd.app.dept.request.DeleteDeptBo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteDeptDTO extends DeleteDeptBo {

    @NotEmpty(message = "部门ID列表不能为空")
    @Override
    public List<Integer> getDeptIds() {
        return super.getDeptIds();
    }
}
