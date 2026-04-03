package com.tooolan.ddd.api.dept;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.dept.request.DeleteDeptDTO;
import com.tooolan.ddd.api.dept.request.PageDeptDTO;
import com.tooolan.ddd.api.dept.request.SaveDeptDTO;
import com.tooolan.ddd.api.dept.request.UpdateDeptDTO;
import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.dept.DeptApplicationService;
import com.tooolan.ddd.app.dept.response.DeptTreeVo;
import com.tooolan.ddd.app.dept.response.DeptVo;
import com.tooolan.ddd.domain.dept.constant.DeptErrorCode;
import com.tooolan.ddd.domain.dept.exception.DeptException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/sys/depts")
@RequiredArgsConstructor
public class DeptController {

    private final DeptApplicationService deptApplicationService;

    /**
     * 根据ID查询部门信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @SaCheckLogin
    @GetMapping("/get/{deptId}")
    public ResultVo<DeptVo> get(@PathVariable Integer deptId) {
        DeptVo vo = deptApplicationService.getById(deptId)
                .orElseThrow(() -> new DeptException(DeptErrorCode.NOT_FOUND));
        return ResultVo.success(vo);
    }

    /**
     * 分页查询部门信息
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @SaCheckLogin
    @GetMapping("/page")
    public ResultVo<PageVo<DeptVo>> page(@Validated PageDeptDTO dto) {
        PageVo<DeptVo> result = deptApplicationService.page(dto);
        return ResultVo.success(result);
    }

    /**
     * 查询部门树形结构
     *
     * @return 树形结构的部门列表
     */
    @SaCheckLogin
    @GetMapping("/tree")
    public ResultVo<List<DeptTreeVo>> tree() {
        List<DeptTreeVo> tree = deptApplicationService.tree();
        return ResultVo.success(tree);
    }

    /**
     * 新增部门信息
     *
     * @param dto 部门信息
     * @return 操作结果
     */
    @SaCheckLogin
    @PostMapping("/save")
    public ResultVo<Void> save(@Validated @RequestBody SaveDeptDTO dto) {
        deptApplicationService.save(dto);
        return ResultVo.success();
    }

    /**
     * 编辑部门信息
     *
     * @param dto 部门信息
     * @return 操作结果
     */
    @SaCheckLogin
    @PutMapping("/update")
    public ResultVo<Void> update(@Validated @RequestBody UpdateDeptDTO dto) {
        deptApplicationService.update(dto);
        return ResultVo.success();
    }

    /**
     * 批量删除部门信息
     *
     * @param dto 部门ID列表
     * @return 操作结果
     */
    @SaCheckLogin
    @DeleteMapping("/delete")
    public ResultVo<Void> delete(@Validated @RequestBody DeleteDeptDTO dto) {
        deptApplicationService.delete(dto);
        return ResultVo.success();
    }

}
