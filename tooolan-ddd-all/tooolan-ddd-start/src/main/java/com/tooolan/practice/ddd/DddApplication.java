package com.tooolan.practice.ddd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DDD 应用启动类
 *
 * @author tooolan
 */
@SpringBootApplication(scanBasePackages = "com.tooolan.practice")
@MapperScan("com.tooolan.practice.ddd.infra.mapper")
public class DddApplication {

    public static void main(String[] args) {
        SpringApplication.run(DddApplication.class, args);
    }

}
