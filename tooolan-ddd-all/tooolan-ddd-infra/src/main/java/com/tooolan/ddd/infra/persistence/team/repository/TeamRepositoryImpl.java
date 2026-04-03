package com.tooolan.ddd.infra.persistence.team.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;
import com.tooolan.ddd.infra.persistence.team.converter.TeamConverter;
import com.tooolan.ddd.infra.persistence.team.entity.SysTeamEntity;
import com.tooolan.ddd.infra.persistence.team.mapper.SysTeamMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 小组 仓储实现
 * 实现小组领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
public class TeamRepositoryImpl extends ServiceImpl<SysTeamMapper, SysTeamEntity> implements TeamRepository {

    /**
     * 根据小组ID查询小组信息
     *
     * @param teamId 小组ID
     * @return 小组信息，不存在时返回空
     */
    @Override
    public Optional<Team> getById(Integer teamId) {
        return super.getOptById(teamId)
                .map(TeamConverter::toDomain);
    }

    /**
     * 根据小组编码查询小组信息
     *
     * @param teamCode 小组编码
     * @return 小组信息，不存在时返回空
     */
    @Override
    public Optional<Team> getByCode(String teamCode) {
        return super.lambdaQuery()
                .eq(StrUtil.isNotBlank(teamCode), SysTeamEntity::getTeamCode, teamCode)
                .oneOpt()
                .map(TeamConverter::toDomain);
    }

    /**
     * 分页查询小组信息
     *
     * @param pageTeamParam 分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageQueryResult<Team> page(PageTeamParam pageTeamParam) {
        IPage<Team> page = super.lambdaQuery()
                .like(StrUtil.isNotBlank(pageTeamParam.getTeamName()), SysTeamEntity::getTeamName, pageTeamParam.getTeamName())
                .eq(StrUtil.isNotBlank(pageTeamParam.getTeamCode()), SysTeamEntity::getTeamCode, pageTeamParam.getTeamCode())
                .in(CollUtil.isNotEmpty(pageTeamParam.getStatusList()), SysTeamEntity::getStatus, pageTeamParam.getStatusList())
                .ge(ObjUtil.isNotNull(pageTeamParam.getCreatedAtStart()), SysTeamEntity::getCreatedAt, pageTeamParam.getCreatedAtStart())
                .le(ObjUtil.isNotNull(pageTeamParam.getCreatedAtEnd()), SysTeamEntity::getCreatedAt, pageTeamParam.getCreatedAtEnd())
                .page(PageDTO.of(pageTeamParam.getPageNum(), pageTeamParam.getPageSize()))
                .convert(TeamConverter::toDomain);

        PageQueryResult<Team> pageQueryResult = new PageQueryResult<>();
        BeanUtil.copyProperties(page, pageQueryResult);
        return pageQueryResult;
    }

    /**
     * 查询小组选项列表
     * 用于下拉框选择，支持按小组名称模糊查询
     * 只返回正常状态的小组
     *
     * @param teamName 小组名称（可选，模糊匹配）
     * @return 小组列表（仅包含 ID 和名称）
     */
    @Override
    public List<Team> listOptions(String teamName) {
        return super.lambdaQuery()
                .select(SysTeamEntity::getTeamId, SysTeamEntity::getTeamName)
                .like(StrUtil.isNotBlank(teamName), SysTeamEntity::getTeamName, teamName)
                .eq(SysTeamEntity::getStatus, TeamStatusEnum.NORMAL.getValue())
                .orderByDesc(SysTeamEntity::getCreatedAt)
                .list()
                .stream()
                .map(TeamConverter::toDomain)
                .toList();
    }

    /**
     * 判断小组是否存在
     *
     * @param teamId 小组ID
     * @return 是否存在
     */
    @Override
    public boolean existById(Integer teamId) {
        return super.lambdaQuery()
                .eq(SysTeamEntity::getTeamId, teamId)
                .exists();
    }

    /**
     * 批量判断小组是否全部存在
     *
     * @param teamIds 小组ID列表
     * @return 所有小组都存在返回 true，否则返回 false
     */
    @Override
    public boolean existByIds(List<Integer> teamIds) {
        return super.lambdaQuery()
                .in(SysTeamEntity::getTeamId, teamIds)
                .count() == teamIds.size();
    }

    /**
     * 保存小组
     * 保存成功后会回填小组ID
     *
     * @param team 小组领域模型
     * @return 是否保存成功
     */
    @Override
    public boolean save(Team team) {
        SysTeamEntity entity = TeamConverter.toEntity(team);
        boolean saved = super.save(entity);
        // 回填ID
        if (saved && entity.getTeamId() != null) {
            team.setId(entity.getTeamId());
        }
        return saved;
    }

    /**
     * 根据小组ID更新小组信息
     * 仅更新非 null 字段，部分更新策略
     *
     * @param team 小组领域模型
     * @return 是否更新成功
     */
    @Override
    public boolean updateById(Team team) {
        SysTeamEntity entity = TeamConverter.toEntity(team);
        return super.updateById(entity);
    }

    /**
     * 批量逻辑删除小组
     *
     * @param teamIds 小组ID列表
     * @return 删除的记录数
     */
    @Override
    public int deleteByIds(List<Integer> teamIds) {
        return baseMapper.deleteByIds(teamIds);
    }

    /**
     * 根据部门ID列表查询小组数量
     *
     * @param deptIds 部门ID列表
     * @return 小组数量
     */
    @Override
    public long countByDeptIds(List<Integer> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return 0;
        }
        return super.lambdaQuery()
                .in(SysTeamEntity::getDeptId, deptIds)
                .count();
    }

}
