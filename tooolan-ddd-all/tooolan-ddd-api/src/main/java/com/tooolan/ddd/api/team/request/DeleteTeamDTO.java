package com.tooolan.ddd.api.team.request;

import com.tooolan.ddd.app.team.request.DeleteTeamBo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 批量删除小组 DTO
 *
 * @author tooolan
 * @since 2026年4月1日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteTeamDTO extends DeleteTeamBo {

    @Override
    @NotEmpty(message = "小组ID列表不能为空")
    public List<Integer> getTeamIds() {
        return super.getTeamIds();
    }

}
