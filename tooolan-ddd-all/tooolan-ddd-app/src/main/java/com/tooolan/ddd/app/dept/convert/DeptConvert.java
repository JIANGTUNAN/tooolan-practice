package com.tooolan.ddd.app.dept.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.dept.request.PageDeptBo;
import com.tooolan.ddd.app.dept.request.SaveDeptBo;
import com.tooolan.ddd.app.dept.request.UpdateDeptBo;
import com.tooolan.ddd.app.dept.response.DeptTreeVo;
import com.tooolan.ddd.app.dept.response.DeptVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.repository.param.PageDeptParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门转换器
 * 负责跨层对象转换
 *
 * @author tooolan
 * @since 2026年4月3日
 */
public class DeptConvert {

    /**
     * 将领域模型转换为视图对象
     *
     * @param dept 领域模型
     * @return 视图对象
     */
    public static DeptVo toVo(Dept dept) {
        if (dept == null) {
            return null;
        }
        DeptVo vo = new DeptVo();
        vo.setDeptId(dept.getId());
        vo.setDeptName(dept.getDeptName());
        vo.setDeptCode(dept.getDeptCode());
        vo.setParentId(dept.getParentId());
        vo.setParentName(null);
        vo.setRemark(dept.getRemark());
        return vo;
    }

    /**
     * 将领域模型转换为树形视图对象
     *
     * @param dept 领域模型
     * @return 树形视图对象
     */
    public static DeptTreeVo toTreeVo(Dept dept) {
        if (dept == null) {
            return null;
        }
        DeptTreeVo vo = new DeptTreeVo();
        vo.setDeptId(dept.getId());
        vo.setDeptName(dept.getDeptName());
        vo.setDeptCode(dept.getDeptCode());
        vo.setParentId(dept.getParentId());
        vo.setParentName(null);
        vo.setRemark(dept.getRemark());
        return vo;
    }

    /**
     * 将领域模型列表转换为视图对象列表
     *
     * @param depts 领域模型列表
     * @return 视图对象列表
     */
    public static List<DeptVo> toVoList(List<Dept> depts) {
        if (depts == null) {
            return null;
        }
        List<DeptVo> voList = new ArrayList<>();
        for (Dept dept : depts) {
            voList.add(toVo(dept));
        }
        return voList;
    }

    /**
     * 将保存部门 BO 转换为领域模型
     *
     * @param bo 保存部门 BO
     * @return 领域模型
     */
    public static Dept toDomain(SaveDeptBo bo) {
        if (bo == null) {
            return null;
        }
        Dept dept = new Dept();
        dept.setDeptName(bo.getDeptName());
        dept.setDeptCode(bo.getDeptCode());
        dept.setParentId(bo.getParentId());
        dept.setRemark(bo.getRemark());
        return dept;
    }

    /**
     * 将更新部门 BO 转换为领域模型（部分更新）
     * 只设置传入的字段，null 字段不会被更新
     *
     * @param bo 更新部门 BO
     * @return 领域模型
     */
    public static Dept toUpdateDomain(UpdateDeptBo bo) {
        if (bo == null) {
            return null;
        }
        Dept dept = new Dept();
        dept.setId(bo.getDeptId());
        dept.setDeptName(bo.getDeptName());
        dept.setDeptCode(bo.getDeptCode());
        dept.setParentId(bo.getParentId());
        dept.setRemark(bo.getRemark());
        return dept;
    }

    /**
     * 将 BO 转换为 Param
     *
     * @param bo BO 对象
     * @return Param 对象
     */
    public static PageDeptParam toParam(PageDeptBo bo) {
        if (bo == null) {
            return null;
        }
        PageDeptParam param = new PageDeptParam();
        param.setPageNum(bo.getPageNum());
        param.setPageSize(bo.getPageSize());
        param.setDeptName(bo.getDeptName());
        param.setDeptCode(bo.getDeptCode());
        param.setParentId(bo.getParentId());
        return param;
    }

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    public static PageVo<DeptVo> toPageVo(PageQueryResult<Dept> result) {
        return PageVo.of(result, DeptConvert::toVo);
    }

    /**
     * 将分页查询结果和已转换的视图对象列表组合为分页视图对象
     * 用于需要对视图对象做额外处理（如填充关联名称）的场景
     *
     * @param result 分页查询结果
     * @param voList 已转换的视图对象列表
     * @return 分页视图对象
     */
    public static PageVo<DeptVo> toPageVo(PageQueryResult<Dept> result, List<DeptVo> voList) {
        if (result == null) {
            return PageVo.empty();
        }
        PageVo<DeptVo> vo = new PageVo<>();
        vo.setPageNum(result.getPageNum());
        vo.setPageSize(result.getPageSize());
        vo.setPages(result.getPages());
        vo.setTotal(result.getTotal());
        vo.setRecords(voList);
        return vo;
    }

}
