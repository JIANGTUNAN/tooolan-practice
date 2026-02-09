package com.tooolan.practice.content;

import com.tooolan.practice.config.DdnsConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IP容器类
 * 管理当前使用的IP（主力IP或容灾IP）
 *
 * @author tooolan
 * @since 2026年2月7日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IpContainer {

    private final DdnsConfig ddnsConfig;

    /**
     * 当前使用的IP
     */
    @Getter
    private volatile String currentIp;

    /**
     * 是否正在使用容灾IP
     */
    private final AtomicBoolean usingBackup = new AtomicBoolean(false);

    /**
     * 初始化IP容器
     * 默认使用主力IP
     */
    @PostConstruct
    public void init() {
        this.currentIp = ddnsConfig.getPrimaryIp();
        log.info("IP容器初始化完成 - 主力IP: {}, 容灾IP: {}, 当前IP: {}",
                ddnsConfig.getPrimaryIp(), ddnsConfig.getBackupIp(), currentIp);
    }

    /**
     * 切换到容灾IP
     * 将当前IP切换为容灾IP并标记使用状态
     */
    public synchronized void switchToBackup() {
        this.currentIp = ddnsConfig.getBackupIp();
        this.usingBackup.set(true);
        log.warn("【IP切换】已切换至容灾IP: {}", currentIp);
    }

    /**
     * 切换到主力IP
     * 将当前IP切换为主力IP并标记使用状态
     */
    public synchronized void switchToPrimary() {
        this.currentIp = ddnsConfig.getPrimaryIp();
        this.usingBackup.set(false);
        log.info("【IP切换】已切回主力IP: {}", currentIp);
    }

    /**
     * 判断是否正在使用容灾IP
     *
     * @return true-使用容灾IP, false-使用主力IP
     */
    public boolean isUsingBackup() {
        return usingBackup.get();
    }

}
