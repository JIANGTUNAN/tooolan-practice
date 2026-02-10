package com.tooolan.ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DDD 领域驱动设计练习应用启动类
 * 这是 DDD 项目的启动入口，负责初始化 Spring Boot 应用上下文并启动服务
 *
 * @author tooolan
 * @since 2026年2月9日
 */
@SpringBootApplication
public class DddApplication {

    /**
     * 应用程序入口方法
     * 启动 Spring Boot 应用，初始化应用上下文和 Web 服务器
     *
     * @param args 命令行参数，可用于传递运行时配置
     */
    public static void main(String[] args) {
        SpringApplication.run(DddApplication.class, args);
    }

}
