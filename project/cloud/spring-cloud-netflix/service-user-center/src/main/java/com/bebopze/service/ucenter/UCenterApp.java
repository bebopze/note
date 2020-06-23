package com.bebopze.service.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bebopze
 * @date 2019/4/29
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.bebopze.framework.model") //扫描实体类
@ComponentScan("com.bebopze.framework.common")
@ComponentScan("com.bebopze.service.ucenter")
public class UCenterApp {

    public static void main(String[] args) {
        SpringApplication.run(UCenterApp.class, args);
    }
}
