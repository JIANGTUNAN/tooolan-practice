package com.tooolan.practice.controller;

import com.tooolan.practice.content.IpContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * IP查询控制器
 * 提供当前IP状态查询接口
 *
 * @author tooolan
 * @since 2026年2月7日
 */
@RestController
@RequestMapping("/api/ip")
@RequiredArgsConstructor
public class IpController {

    private final IpContainer ipContainer;


    /**
     * 获取当前使用的IP
     *
     * @return 当前IP地址
     */
    @GetMapping("/current")
    public ResponseEntity<String> getCurrentIp() {
        String currentIp = ipContainer.getCurrentIp();
        return ResponseEntity.ok(currentIp);
    }

    /**
     * 获取IP状态详情
     *
     * @return 包含currentIp和usingBackup的状态信息
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("currentIp", ipContainer.getCurrentIp());
        status.put("usingBackup", ipContainer.isUsingBackup());
        return ResponseEntity.ok(status);
    }

}
