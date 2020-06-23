package com.bebopze.web.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Rest 引导类
 *
 * @author bebopze
 * @since 2018/5/27
 */
@SpringBootApplication(scanBasePackages = {
        "com.bebopze.web.controller",
        "com.bebopze.web.config"
})
public class SpringBootRestBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestBootstrap.class, args);
    }
}
