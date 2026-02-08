package com.tooolan.practice.dto;

import lombok.Data;

/**
 * Uptime Kuma Webhook数据传输对象
 * 用于接收Uptime Kuma监控通知
 *
 * @author tooolan
 * @since 2026年2月7日
 */
@Data
public class UptimeKumaWebhook {

    /**
     * 状态码: 0=down, 1=up
     */
    private Integer status;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 消息内容
     */
    private String msg;

}
