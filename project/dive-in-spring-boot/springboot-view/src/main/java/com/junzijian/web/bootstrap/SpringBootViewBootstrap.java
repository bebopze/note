package com.junzijian.web.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot 视图引导类
 *
 * @author junzijian
 * @since 2018/5/24
 */
@SpringBootApplication(scanBasePackages = "com.junzijian.web")
public class SpringBootViewBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootViewBootstrap.class, args);
    }
}
