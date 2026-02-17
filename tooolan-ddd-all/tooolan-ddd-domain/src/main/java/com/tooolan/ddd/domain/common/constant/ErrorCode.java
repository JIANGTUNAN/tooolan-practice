package com.tooolan.ddd.domain.common.constant;

/**
 * 错误码接口
 * 所有错误码枚举都需要实现此接口
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * 默认返回枚举名称
     *
     * @return 错误码
     */
    default String getCode() {
        return ((Enum<?>) this).name();
    }

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    String getMessage();

}
