package com.tooolan.practice.ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DDD应用启动类
 *
 * @author tooolan
 */
@SpringBootApplication(scanBasePackages = "com.tooolan.practice.ddd")
public class DddApplication {

    public static void main(String[] args) {
        SpringApplication.run(DddApplication.class, args);
    }

}
