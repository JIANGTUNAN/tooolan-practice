package com.tooolan.ddd.app.log.convert;

import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.log.request.PageLogBo;
import com.tooolan.ddd.app.log.response.LogVo;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;

import java.util.List;

/**
 * 日志转换器
 * 负责跨层对象转换
 *
 * @author tooolan
 * @since 2026年2月23日
 */
public class LogConvert {

    /**
     * 将领域模型转换为视图对象
     *
     * @param log 领域模型
     * @return 视图对象
     */
    public static LogVo toVo(Log log) {
        if (log == null) {
            return null;
        }
        LogVo vo = new LogVo();
        vo.setId(log.getId());
        vo.setModule(log.getModule());
        vo.setAction(log.getAction());
        vo.setTargetType(log.getTargetType());
        vo.setTargetId(log.getTargetId());
        vo.setTargetName(log.getTargetName());
        vo.setContent(log.getContent());
        vo.setOperatorId(log.getOperatorId());
        vo.setOperatorName(log.getOperatorName());
        vo.setOperatorIp(log.getOperatorIp());
        vo.setCreatedAt(log.getCreatedAt());
        return vo;
    }

    /**
     * 将 BO 转换为 Param
     *
     * @param bo BO 对象
     * @return Param 对象
     */
    public static PageLogParam toParam(PageLogBo bo) {
        if (bo == null) {
            return null;
        }
        PageLogParam param = new PageLogParam();
        param.setPageNum(bo.getPageNum());
        param.setPageSize(bo.getPageSize());
        param.setModule(bo.getModule());
        param.setAction(bo.getAction());
        param.setTargetType(bo.getTargetType());
        param.setOperatorName(bo.getOperatorName());
        param.setCreatedAtStart(bo.getCreatedAtStart());
        param.setCreatedAtEnd(bo.getCreatedAtEnd());
        return param;
    }

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    public static PageVo<LogVo> toPageVo(PageQueryResult<Log> result) {
        if (result == null) {
            PageVo<LogVo> vo = new PageVo<>();
            vo.setPageNum(0);
            vo.setPageSize(0);
            vo.setPages(0);
            vo.setTotal(0);
            vo.setRecords(List.of());
            return vo;
        }
        List<LogVo> vos = result.getRecords().stream()
                .map(LogConvert::toVo)
                .toList();
        PageVo<LogVo> vo = new PageVo<>();
        vo.setPageNum(result.getPageNum());
        vo.setPageSize(result.getPageSize());
        vo.setPages(result.getPages());
        vo.setTotal(result.getTotal());
        vo.setRecords(vos);
        return vo;
    }

}
