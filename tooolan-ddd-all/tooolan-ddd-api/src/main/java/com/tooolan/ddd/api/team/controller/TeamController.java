package com.tooolan.ddd.api.team.controller;

import com.tooolan.ddd.api.common.ResultVO;
import com.tooolan.ddd.api.team.dto.*;
import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.team.service.TeamApplicationService;
import com.tooolan.ddd.app.team.vo.TeamVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小组管理控制器
 * 提供小组相关的 REST API，包括小组的增删改查和转移操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamApplicationService teamApplicationService;

    /**
     * 创建小组
     *
     * @param request 小组创建请求
     * @return 创建成功的小组信息
     */
    @PostMapping
    public ResultVO<TeamVo> createTeam(@Valid @RequestBody TeamCreateRequest request) {
        TeamVo vo = teamApplicationService.createTeam(request);
        return ResultVO.success("小组创建成功", vo);
    }

    /**
     * 根据ID查询小组详情
     *
     * @param teamId 小组ID
     * @return 小组详细信息
     */
    @GetMapping("/{teamId}")
    public ResultVO<TeamVo> getTeamById(@PathVariable("teamId") Integer teamId) {
        TeamVo vo = teamApplicationService.getTeamById(teamId);
        return ResultVO.success(vo);
    }

    /**
     * 分页查询小组列表
     *
     * @param request 查询请求条件
     * @return 分页小组列表
     */
    @GetMapping
    public ResultVO<PageVo<TeamVo>> getTeamPage(TeamQueryRequest request) {
        PageVo<TeamVo> vo = teamApplicationService.getTeamPage(request);
        return ResultVO.success(vo);
    }

    /**
     * 更新小组信息
     *
     * @param teamId  小组ID
     * @param request 更新请求
     * @return 更新后的小组信息
     */
    @PutMapping("/{teamId}")
    public ResultVO<TeamVo> updateTeam(
            @PathVariable("teamId") Integer teamId,
            @Valid @RequestBody TeamUpdateRequest request) {
        TeamVo vo = teamApplicationService.updateTeam(teamId, request);
        return ResultVO.success("小组更新成功", vo);
    }

    /**
     * 删除小组
     *
     * @param teamId 小组ID
     * @return 操作结果
     */
    @DeleteMapping("/{teamId}")
    public ResultVO<Void> deleteTeam(@PathVariable("teamId") Integer teamId) {
        teamApplicationService.deleteTeam(teamId);
        return ResultVO.success("小组删除成功", null);
    }

    /**
     * 小组转移部门
     *
     * @param teamId  小组ID
     * @param request 转移请求
     * @return 操作结果
     */
    @PostMapping("/{teamId}/transfer")
    public ResultVO<Void> transferTeam(
            @PathVariable("teamId") Integer teamId,
            @Valid @RequestBody TeamTransferRequest request) {
        teamApplicationService.transferTeam(teamId, request);
        return ResultVO.success("小组转移成功", null);
    }

    /**
     * 查询部门下的小组
     *
     * @param deptId 部门ID
     * @return 小组列表
     */
    @GetMapping("/dept/{deptId}")
    public ResultVO<List<TeamVo>> getTeamsByDept(@PathVariable("deptId") Integer deptId) {
        List<TeamVo> teams = teamApplicationService.getTeamsByDept(deptId);
        return ResultVO.success(teams);
    }
}
