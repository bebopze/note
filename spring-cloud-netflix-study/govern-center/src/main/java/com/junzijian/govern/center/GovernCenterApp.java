package com.junzijian.govern.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liuzhe
 */
@EnableEurekaServer // 标识此工程是一个EurekaServer
//@EnableDiscoveryClient
@SpringBootApplication
public class GovernCenterApp {

    public static void main(String[] args) {
        SpringApplication.run(GovernCenterApp.class, args);
    }

}
