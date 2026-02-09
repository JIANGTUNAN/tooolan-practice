package com.tooolan.practice.ddd.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

/**
 * 日志级别颜色转换器
 * 为不同日志级别配置柔和的 ANSI 颜色，提升控制台日志可读性
 *
 * 颜色方案：
 * - ERROR: 红色 (31m)
 * - WARN: 黄色 (33m)
 * - INFO: 青色 (36m)
 * - DEBUG: 绿色 (32m)
 * - TRACE: 灰色 (90m)
 *
 * @author tooolan
 * @since 2026年2月9日
 */
public class ColorLevelConverter extends CompositeConverter<ILoggingEvent> {

    /**
     * 根据日志级别转换为带颜色编码的字符串
     * 将日志级别包装在 ANSI 颜色代码中，实现控制台彩色输出
     *
     * @param event 日志事件，包含日志级别等信息
     * @param in 原始字符串（通常是日志级别文本）
     * @return 带有 ANSI 颜色代码的字符串
     */
    @Override
    protected String transform(ILoggingEvent event, String in) {
        Level level = event.getLevel();
        String colorCode = switch (level.toInt()) {
            case Level.ERROR_INT -> "\u001B[31m"; // 红色
            case Level.WARN_INT -> "\u001B[33m";  // 黄色
            case Level.INFO_INT -> "\u001B[36m";  // 青色
            case Level.DEBUG_INT -> "\u001B[32m"; // 绿色
            case Level.TRACE_INT -> "\u001B[90m"; // 灰色
            default -> "\u001B[37m";              // 白色
        };
        return colorCode + in + "\u001B[0m";
    }

}
