package com.tooolan.ddd.app.dept.response;

import cn.hutool.core.collection.CollUtil;
import com.tooolan.ddd.app.dept.convert.DeptAppConverter;
import com.tooolan.ddd.domain.dept.model.DeptTree;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门树形视图对象
 * 用于展示层级结构的部门数据
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
     * 从部门树节点列表构建树形结构
     *
     * @param deptTrees 部门树节点列表
     * @param converter 部门转换器
     * @return 树形结构的部门列表（只包含顶级部门）
     */
    public static List<DeptTreeVo> buildTree(List<DeptTree> deptTrees, DeptAppConverter converter) {
        if (deptTrees == null || deptTrees.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. 转换为 Vo 并建立 ID -> Vo 映射
        Map<Integer, DeptTreeVo> voMap = new HashMap<>();
        for (DeptTree deptTree : deptTrees) {
            DeptTreeVo vo = converter.toTreeVo(deptTree);
            voMap.put(vo.getDeptId(), vo);
        }

        // 2. 构建树形结构
        List<DeptTreeVo> roots = new ArrayList<>();
        for (DeptTreeVo vo : voMap.values()) {
            if (vo.getParentId() == null) {
                // 顶级部门
                roots.add(vo);
            } else {
                // 添加到父节点
                DeptTreeVo parentVo = voMap.get(vo.getParentId());
                if (parentVo != null) {
                    if (parentVo.getChildren() == null) {
                        parentVo.setChildren(CollUtil.newArrayList(vo));
                    } else {
                        parentVo.getChildren().add(vo);
                    }
                }
            }
        }
        return roots;
    }

}
