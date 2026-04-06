package com.tooolan.ddd.app.log.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.log.request.PageLogBo;
import com.tooolan.ddd.app.log.response.LogVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.log.model.Log;
import com.tooolan.ddd.domain.log.repository.param.PageLogParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 日志应用层转换器
 * 负责业务类 ↔ 领域模型 ↔ 视图对象的转换
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@Mapper(componentModel = "spring")
public interface LogAppConverter {

    /**
     * 将领域模型转换为视图对象
     *
     * @param log 领域模型
     * @return 视图对象
     */
    LogVo toVo(Log log);

    /**
     * 将领域模型列表转换为视图对象列表
     *
     * @param logs 领域模型列表
     * @return 视图对象列表
     */
    List<LogVo> toVoList(List<Log> logs);

    /**
     * 将分页日志业务类转换为查询参数类
     *
     * @param bo 分页日志业务类
     * @return 查询参数类
     */
    PageLogParam toParam(PageLogBo bo);

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    default PageVo<LogVo> toPageVo(PageQueryResult<Log> result) {
        return PageVo.of(result, this::toVo);
    }

}
