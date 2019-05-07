package com.junzijian.service.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liuzhe
 * @date 2019/4/29
 */
@EnableDiscoveryClient
@SpringBootApplication
//@EntityScan("com.junzijian.framework.model") //扫描实体类
public class UCenterApp {

    public static void main(String[] args) {
        SpringApplication.run(UCenterApp.class, args);
    }
}
