package com.tooolan.ddd.api.common.err;

import com.tooolan.ddd.api.common.constant.ResponseCode;
import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.domain.common.constant.CommonErrorCode;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.exception.DomainException;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理所有异常，HTTP 状态码始终返回 200，通过系统状态码区分业务状态
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理资源不存在异常
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResultVo<Void>> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.warn("资源不存在: {} - {}", request.getRequestURI(), e.getMessage());
        ResultVo<Void> result = ResultVo.error(ResponseCode.NOT_FOUND, e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理业务规则异常
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ResultVo<Void>> handleBusinessRuleException(BusinessRuleException e, HttpServletRequest request) {
        log.warn("业务规则校验失败: {} - {}", request.getRequestURI(), e.getMessage());
        ResultVo<Void> result = ResultVo.error(ResponseCode.BAD_REQUEST, e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理领域异常（兜底）
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ResultVo<Void>> handleDomainException(DomainException e, HttpServletRequest request) {
        log.error("领域层异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        ResultVo<Void> result = ResultVo.error(ResponseCode.BAD_REQUEST, e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理 @Valid 校验失败异常
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultVo<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验失败: {} - {}", request.getRequestURI(), errorMessage);
        ResultVo<Void> result = ResultVo.error(ResponseCode.VALIDATION_FAILED, CommonErrorCode.PARAM_VALIDATION_FAILED.getCode(), errorMessage);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理 @Validated 校验失败异常
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResultVo<Void>> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数约束违反: {} - {}", request.getRequestURI(), errorMessage);
        ResultVo<Void> result = ResultVo.error(ResponseCode.VALIDATION_FAILED, CommonErrorCode.PARAM_CONSTRAINT_VIOLATION.getCode(), errorMessage);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理非法参数异常
     * 注意：不暴露原始异常消息，使用用户友好的提示
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultVo<Void>> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("非法参数: {} - {}", request.getRequestURI(), e.getMessage(), e);
        ResultVo<Void> result = ResultVo.error(ResponseCode.BAD_REQUEST, CommonErrorCode.ILLEGAL_ARGUMENT.getCode(), CommonErrorCode.ILLEGAL_ARGUMENT.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理非法状态异常
     * 注意：不暴露原始异常消息，使用用户友好的提示
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResultVo<Void>> handleIllegalStateException(IllegalStateException e, HttpServletRequest request) {
        log.error("非法状态: {} - {}", request.getRequestURI(), e.getMessage(), e);
        ResultVo<Void> result = ResultVo.error(ResponseCode.INTERNAL_ERROR, CommonErrorCode.ILLEGAL_STATE.getCode(), CommonErrorCode.ILLEGAL_STATE.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理所有未捕获的异常（兜底）
     * 注意：完全隐藏技术细节，返回用户友好的提示
     *
     * @param e       异常对象
     * @param request HTTP 请求
     * @return 统一响应对象
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultVo<Void>> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        ResultVo<Void> result = ResultVo.error(ResponseCode.INTERNAL_ERROR, CommonErrorCode.SYSTEM_ERROR.getCode(), CommonErrorCode.SYSTEM_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
