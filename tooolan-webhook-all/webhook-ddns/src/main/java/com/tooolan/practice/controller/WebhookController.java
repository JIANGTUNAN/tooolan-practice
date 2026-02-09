package com.tooolan.practice.controller;

import com.tooolan.practice.dto.UptimeKumaWebhook;
import com.tooolan.practice.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Webhook控制器
 * 接收Uptime Kuma监控通知
 *
 * @author tooolan
 * @since 2026年2月7日
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhook")
public class WebhookController {

    private final WebhookService webhookService;


    /**
     * 接收Uptime Kuma的Webhook通知
     * 支持form表单和JSON格式
     * 根据服务状态自动切换IP
     *
     * @param webhook Webhook数据
     * @return 200 OK
     */
    @PostMapping("/uptime-kuma")
    public ResponseEntity<?> receiveUptimeKumaWebhook(@RequestBody UptimeKumaWebhook webhook) {
        log.info("收到Webhook: service={}, status={}, msg={}", webhook.getName(), webhook.getStatus(), webhook.getMsg());
        webhookService.handleWebhook(webhook);
        return ResponseEntity.ok().build();
    }

}
