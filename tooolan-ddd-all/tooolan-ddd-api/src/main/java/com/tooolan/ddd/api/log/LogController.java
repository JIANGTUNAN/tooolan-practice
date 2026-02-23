package com.tooolan.ddd.api.log;

import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.api.log.request.PageLogDTO;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.log.response.LogVo;
import com.tooolan.ddd.app.log.service.LogApplicationService;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.log.constant.LogErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统操作日志管理 控制器
 * 提供日志查询相关的 REST API
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/logs")
public class LogController {

    private final LogApplicationService logApplicationService;


    /**
     * 根据ID查询日志信息
     *
     * @param logId 日志ID
     * @return 日志信息
     */
    @GetMapping("/get/{logId}")
    public ResultVo<LogVo> get(@PathVariable Long logId) {
        LogVo logVo = logApplicationService.getLogById(logId)
                .orElseThrow(() -> new NotFoundException(LogErrorCode.NOT_FOUND));
        return ResultVo.success(logVo);
    }

    /**
     * 分页查询日志信息
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public ResultVo<PageVo<LogVo>> page(@Validated PageLogDTO dto) {
        PageVo<LogVo> pageVo = logApplicationService.pageLog(dto);
        return ResultVo.success(pageVo);
    }

}
