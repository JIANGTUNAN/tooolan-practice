package com.tooolan.ddd.domain.common.exception;

/**
 * 资源不存在异常
 * 表示查询的资源在系统中不存在
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public class NotFoundException extends DomainException {

    /**
     * 构造资源不存在异常
     *
     * @param message 错误消息
     */
    public NotFoundException(String message) {
        super("RESOURCE_NOT_FOUND", message);
    }

    /**
     * 构造资源不存在异常
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     */
    public NotFoundException(String resourceType, Object resourceId) {
        super("RESOURCE_NOT_FOUND", String.format("%s不存在: %s", resourceType, resourceId));
    }

}
