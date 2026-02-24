package com.tooolan.ddd.api.team;

import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.team.request.PageTeamDTO;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.team.TeamApplicationService;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
