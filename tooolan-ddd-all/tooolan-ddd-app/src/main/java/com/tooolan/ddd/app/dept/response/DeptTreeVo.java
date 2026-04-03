package com.tooolan.ddd.app.dept.response;

import cn.hutool.core.collection.CollUtil;
import com.tooolan.ddd.app.dept.convert.DeptConvert;
import com.tooolan.ddd.domain.dept.model.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 部门树形视图对象
 * 继承 DeptVo，添加 children 属性，用于树形结构展示
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTreeVo extends DeptVo {

    /**
     * 子部门列表
     */
    List<DeptTreeVo> children;

    /**
     * 从扁平部门列表构建树形结构
     *
     * @param depts 扁平的部门列表
     * @return 树形结构的部门列表（只包含顶级部门）
     */
    public static List<DeptTreeVo> buildTree(List<Dept> depts) {
        if (depts == null || depts.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. 转换为 DeptTreeVo 并构造 id -> DeptTreeVo 映射
        Map<Integer, DeptTreeVo> voMap = depts.stream()
                .map(DeptConvert::toTreeVo)
                .collect(Collectors.toMap(
                        DeptTreeVo::getDeptId,
                        Function.identity()
                ));

        // 2. 构建树形结构
        List<DeptTreeVo> roots = new ArrayList<>();
        for (DeptTreeVo vo : voMap.values()) {
            if (vo.getParentId() == null) {
                // 顶级部门
                roots.add(vo);
            } else {
                // 添加到父节点
                DeptTreeVo deptTreeVo = voMap.get(vo.getParentId());
                if (deptTreeVo.getChildren() == null) {
                    deptTreeVo.setChildren(CollUtil.newArrayList(vo));
                } else {
                    deptTreeVo.getChildren().add(vo);
                }
            }
        }
        return roots;
    }

}
