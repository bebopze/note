package com.bebopze.cloud.api.biz;

//import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;

import com.bebopze.framework.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableFeignClients("com.bebopze.cloud.client")
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.bebopze")
@MapperScan("com.bebopze.cloud.svc.biz.mapper")
public class BizApp {

    public static void main(String[] args) {
        SpringApplication.run(BizApp.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

//    @LoadBalanced
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

//    @LoadBalanced
//    @Bean
//    @SentinelRestTemplate
//    public RestTemplate restTemplate1() {
//        return new RestTemplate();
//    }
}
