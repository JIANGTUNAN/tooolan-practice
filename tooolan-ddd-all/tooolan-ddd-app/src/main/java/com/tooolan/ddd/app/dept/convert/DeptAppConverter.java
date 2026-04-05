package com.tooolan.ddd.app.dept.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.dept.request.PageDeptBo;
import com.tooolan.ddd.app.dept.request.SaveDeptBo;
import com.tooolan.ddd.app.dept.request.UpdateDeptBo;
import com.tooolan.ddd.app.dept.response.DeptTreeVo;
import com.tooolan.ddd.app.dept.response.DeptVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptTree;
import com.tooolan.ddd.domain.dept.repository.param.PageDeptParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 部门转换器
 * 负责跨层对象转换
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Mapper(componentModel = "spring")
public interface DeptAppConverter {

    /**
     * 将领域模型转换为视图对象
     *
     * @param dept 领域模型
     * @return 视图对象
     */
    @Mapping(target = "parentName", ignore = true)
    DeptVo toVo(Dept dept);

    /**
     * 将部门树节点转换为树形视图对象
     * 注意：children 字段由 DeptTreeVo.buildTree() 方法统一构建
     *
     * @param deptTree 部门树节点
     * @return 树形视图对象
     */
    @Mapping(target = "parentName", ignore = true)
    @Mapping(target = "children", ignore = true)
    DeptTreeVo toTreeVo(DeptTree deptTree);

    /**
     * 将领域模型列表转换为视图对象列表
     *
     * @param depts 领域模型列表
     * @return 视图对象列表
     */
    List<DeptVo> toVoList(List<Dept> depts);

    /**
     * 将领域模型转换为部门树节点
     *
     * @param dept 领域模型
     * @return 部门树节点
     */
    @Mapping(target = "children", ignore = true)
    DeptTree toTree(Dept dept);

    /**
     * 批量将领域模型转换为部门树节点
     *
     * @param depts 领域模型列表
     * @return 部门树节点列表
     */
    List<DeptTree> toTreeList(List<Dept> depts);

    /**
     * 将保存部门 BO 转换为领域模型
     *
     * @param bo 保存部门 BO
     * @return 领域模型
     */
    @Mapping(target = "deptId", ignore = true)
    Dept toSaveDomain(SaveDeptBo bo);

    /**
     * 将更新部门 BO 转换为领域模型（部分更新）
     *
     * @param bo 更新部门 BO
     * @return 领域模型
     */
    Dept toUpdateDomain(UpdateDeptBo bo);

    /**
     * 将 BO 转换为 Param
     *
     * @param bo BO 对象
     * @return Param 对象
     */
    PageDeptParam toParam(PageDeptBo bo);

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    default PageVo<DeptVo> toPageVo(PageQueryResult<Dept> result) {
        return PageVo.of(result, this::toVo);
    }

    /**
     * 将分页查询结果和已转换的视图对象列表组合为分页视图对象
     * 用于需要对视图对象做额外处理（如填充关联名称）的场景
     *
     * @param result 分页查询结果
     * @param voList 已转换的视图对象列表
     * @return 分页视图对象
     */
    default PageVo<DeptVo> toPageVo(PageQueryResult<Dept> result, List<DeptVo> voList) {
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
