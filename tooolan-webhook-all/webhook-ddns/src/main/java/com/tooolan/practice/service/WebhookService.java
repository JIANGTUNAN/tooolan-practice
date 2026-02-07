package com.tooolan.practice.service;

import com.tooolan.practice.content.IpContainer;
import com.tooolan.practice.dto.UptimeKumaWebhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Webhook处理服务
 * 处理Uptime Kuma发送的监控通知并执行IP切换
 *
 * @author tooolan
 * @since 2026年2月7日
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookService {

    private final IpContainer ipContainer;

    /**
     * 处理Uptime Kuma Webhook通知
     * 根据服务状态自动切换IP
     *
     * @param webhook Webhook数据
     */
    public void handleWebhook(UptimeKumaWebhook webhook) {
        if (webhook.getStatus() == null) {
            log.warn("未获取到状态信息");
            return;
        }

        log.info("服务: {}, 状态: {}, 消息: {}", webhook.getName(), webhook.getStatus(), webhook.getMsg());

        switch (webhook.getStatus()) {
            case 0:
                handleDownEvent(webhook);
                break;
            case 1:
                handleUpEvent(webhook);
                break;
            default:
                log.warn("未知状态码: {}", webhook.getStatus());
        }
    }

    /**
     * 处理服务宕机事件
     * 切换到容灾IP
     *
     * @param webhook Webhook数据
     */
    private void handleDownEvent(UptimeKumaWebhook webhook) {
        log.error("【服务宕机】服务: {} - 消息: {}", webhook.getName(), webhook.getMsg());
        ipContainer.switchToBackup();
        log.info("当前服务IP: {}", ipContainer.getCurrentIp());
    }

    /**
     * 处理服务恢复事件
     * 切回主力IP
     *
     * @param webhook Webhook数据
     */
    private void handleUpEvent(UptimeKumaWebhook webhook) {
        log.info("【服务恢复】服务: {} - 消息: {}", webhook.getName(), webhook.getMsg());
        ipContainer.switchToPrimary();
        log.info("当前服务IP: {}", ipContainer.getCurrentIp());
    }

}
