package com.tooolan.ddd.api.dept.controller;

import com.tooolan.ddd.api.common.ResultVO;
import com.tooolan.ddd.api.dept.dto.*;
import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.dept.service.DeptApplicationService;
import com.tooolan.ddd.app.dept.vo.DeptTreeNodeVo;
import com.tooolan.ddd.app.dept.vo.DeptVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 * 提供部门相关的 REST API，包括部门的增删改查和移动操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class DeptController {

    private final DeptApplicationService deptApplicationService;

    /**
     * 创建部门
     *
     * @param request 部门创建请求
     * @return 创建成功的部门信息
     */
    @PostMapping
    public ResultVO<DeptVo> createDept(@Valid @RequestBody DeptCreateRequest request) {
        DeptVo vo = deptApplicationService.createDept(request);
        return ResultVO.success("部门创建成功", vo);
    }

    /**
     * 根据ID查询部门详情
     *
     * @param deptId 部门ID
     * @return 部门详细信息
     */
    @GetMapping("/{deptId}")
    public ResultVO<DeptVo> getDeptById(@PathVariable("deptId") Integer deptId) {
        DeptVo vo = deptApplicationService.getDeptById(deptId);
        return ResultVO.success(vo);
    }

    /**
     * 分页查询部门列表
     *
     * @param request 查询请求条件
     * @return 分页部门列表
     */
    @GetMapping
    public ResultVO<PageVo<DeptVo>> getDeptPage(DeptQueryRequest request) {
        PageVo<DeptVo> vo = deptApplicationService.getDeptPage(request);
        return ResultVO.success(vo);
    }

    /**
     * 更新部门信息
     *
     * @param deptId  部门ID
     * @param request 更新请求
     * @return 更新后的部门信息
     */
    @PutMapping("/{deptId}")
    public ResultVO<DeptVo> updateDept(
            @PathVariable("deptId") Integer deptId,
            @Valid @RequestBody DeptUpdateRequest request) {
        DeptVo vo = deptApplicationService.updateDept(deptId, request);
        return ResultVO.success("部门更新成功", vo);
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     * @return 操作结果
     */
    @DeleteMapping("/{deptId}")
    public ResultVO<Void> deleteDept(@PathVariable("deptId") Integer deptId) {
        deptApplicationService.deleteDept(deptId);
        return ResultVO.success("部门删除成功", null);
    }

    /**
     * 获取部门树形结构
     *
     * @return 部门树节点列表
     */
    @GetMapping("/tree")
    public ResultVO<List<DeptTreeNodeVo>> getDeptTree() {
        List<DeptTreeNodeVo> tree = deptApplicationService.getDeptTree();
        return ResultVO.success(tree);
    }

    /**
     * 移动部门节点
     *
     * @param deptId  部门ID
     * @param request 移动请求
     * @return 操作结果
     */
    @PostMapping("/{deptId}/move")
    public ResultVO<Void> moveDept(
            @PathVariable("deptId") Integer deptId,
            @Valid @RequestBody DeptMoveRequest request) {
        deptApplicationService.moveDept(deptId, request);
        return ResultVO.success("部门移动成功", null);
    }

    /**
     * 获取部门下的小组列表
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    @GetMapping("/{deptId}/teams")
    public ResultVO<List<DeptVo>> getDeptTeams(@PathVariable("deptId") Integer deptId) {
        List<DeptVo> teams = deptApplicationService.getDeptTeams(deptId);
        return ResultVO.success(teams);
    }
}
