package com.tooolan.ddd.app.dept.assembler;

import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.dept.bo.DeptCreateBO;
import com.tooolan.ddd.app.dept.vo.DeptTreeNodeVo;
import com.tooolan.ddd.app.dept.vo.DeptVo;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.pagination.Page;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门DTO组装器
 * 负责部门领域对象与BO/VO之间的相互转换，包含树形结构构建逻辑
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Component
public class DeptAssembler {

    /**
     * 业务对象转领域对象
     *
     * @param bo 部门创建业务对象
     * @return 部门领域对象
     */
    public Dept toDomain(DeptCreateBO bo) {
        DeptCode deptCode = DeptCode.of(bo.getDeptCode());
        DeptId parentId = bo.getParentId() != null ? DeptId.of(bo.getParentId()) : null;

        return Dept.create(bo.getDeptName(), deptCode, parentId);
    }

    /**
     * 领域对象转视图对象
     *
     * @param dept 部门领域对象
     * @return 部门视图对象
     */
    public DeptVo toVo(Dept dept) {
        if (dept == null) {
            return null;
        }

        return new DeptVo(
                dept.getId() != null ? dept.getId().getValue() : null,
                dept.getDeptName(),
                dept.getDeptCode() != null ? dept.getDeptCode().getValue() : null,
                dept.getParentId() != null ? dept.getParentId().getValue() : null,
                null, // parentName - 需要从仓储获取
                dept.getRemark(),
                null, // createdBy - 从审计字段获取
                null, // createdAt - 从审计字段获取
                null, // updatedBy - 从审计字段获取
                null  // updatedAt - 从审计字段获取
        );
    }

    /**
     * 分页结果转换
     *
     * @param page 领域分页结果
     * @return 分页视图对象
     */
    public PageVo<DeptVo> toPageVo(Page<Dept> page) {
        return PageVo.of(
                page.getRecords().stream()
                        .map(this::toVo)
                        .toList(),
                page.getTotal(),
                page.getPageNum(),
                page.getPageSize()
        );
    }

    /**
     * 领域对象转树节点视图对象
     *
     * @param dept 部门领域对象
     * @return 部门树节点视图对象
     */
    public DeptTreeNodeVo toTreeNodeVo(Dept dept) {
        if (dept == null) {
            return null;
        }

        DeptTreeNodeVo node = new DeptTreeNodeVo();
        node.setDeptId(dept.getId() != null ? dept.getId().getValue() : null);
        node.setDeptName(dept.getDeptName());
        node.setDeptCode(dept.getDeptCode() != null ? dept.getDeptCode().getValue() : null);
        node.setParentId(dept.getParentId() != null ? dept.getParentId().getValue() : null);
        node.setChildren(new ArrayList<>());

        return node;
    }

    /**
     * 构建部门树
     * 将部门列表转换为树形结构
     *
     * @param depts 部门列表
     * @return 部门树节点视图对象列表（根节点）
     */
    public List<DeptTreeNodeVo> buildTree(List<Dept> depts) {
        if (depts == null || depts.isEmpty()) {
            return new ArrayList<>();
        }

        // 转换为树节点
        List<DeptTreeNodeVo> allNodes = depts.stream()
                .map(this::toTreeNodeVo)
                .toList();

        // 按父ID分组
        Map<Integer, List<DeptTreeNodeVo>> nodesByParent = allNodes.stream()
                .collect(Collectors.groupingBy(
                        node -> node.getParentId() == null ? 0 : node.getParentId(),
                        Collectors.toList()
                ));

        // 设置子节点
        allNodes.forEach(node -> {
            List<DeptTreeNodeVo> children = nodesByParent.getOrDefault(node.getDeptId(), new ArrayList<>());
            node.setChildren(children.isEmpty() ? null : children);
        });

        // 返回根节点
        return nodesByParent.getOrDefault(0, new ArrayList<>());
    }
}
