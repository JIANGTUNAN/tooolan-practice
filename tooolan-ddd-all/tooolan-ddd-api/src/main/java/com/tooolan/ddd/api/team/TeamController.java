package com.tooolan.ddd.api.team;

import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.team.request.PageTeamDTO;
import com.tooolan.ddd.api.team.request.SaveTeamDTO;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.common.response.OptionVo;
import com.tooolan.ddd.app.team.TeamApplicationService;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 小组管理控制器
 * 提供小组相关的 REST API，包括小组的增删改查和转移操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequestMapping("/sys/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamApplicationService teamApplicationService;


    /**
     * 获取小组状态枚举选项列表
     * 用于下拉框选择，支持按描述模糊筛选
     *
     * @param desc 状态描述（可选，用于模糊筛选）
     * @return 状态选项列表
     */
    @GetMapping("/optionsStatus")
    public ResultVo<OptionVo<Integer>> optionsStatus(@RequestParam(required = false) String desc) {
        OptionVo<Integer> options = new OptionVo<>();
        Arrays.stream(TeamStatusEnum.values())
                .filter(status -> desc == null || status.getDesc().contains(desc))
                .forEach(status -> options.addOption(status.getValue(), status.getDesc()));
        return ResultVo.success(options);
    }

    /**
     * 获取小组选项列表
     *
     * @param teamName 小组名称（可选，模糊匹配）
     * @return 小组选项列表
     */
    @GetMapping("/options")
    public ResultVo<OptionVo<Integer>> options(@RequestParam(required = false) String teamName) {
        OptionVo<Integer> options = teamApplicationService.getTeamOptions(teamName);
        return ResultVo.success(options);
    }

    /**
     * 根据ID查询小组信息
     *
     * @param teamId 小组ID
     * @return 小组信息
     */
    @GetMapping("/get/{teamId}")
    public ResultVo<TeamVo> get(@PathVariable Integer teamId) {
        TeamVo team = teamApplicationService.getTeamById(teamId)
                .orElseThrow(() -> new NotFoundException(TeamErrorCode.NOT_FOUND));
        return ResultVo.success(team);
    }

    /**
     * 分页查询小组信息
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public ResultVo<PageVo<TeamVo>> page(@Validated PageTeamDTO dto) {
        PageVo<TeamVo> pageVo = teamApplicationService.pageTeam(dto);
        return ResultVo.success(pageVo);
    }

    /**
     * 新增小组信息
     *
     * @param dto 小组信息
     * @return 操作结果
     */
    @PostMapping("/save")
    public ResultVo<Void> save(@Validated @RequestBody SaveTeamDTO dto) {
        teamApplicationService.saveTeam(dto);
        return ResultVo.success();
    }

}
