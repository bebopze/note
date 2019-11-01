package com.junzijian.cloud.svc.biz;

//import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;

import com.junzijian.framework.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableFeignClients("com.junzijian.cloud.client")
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.junzijian")
@MapperScan("com.junzijian.cloud.svc.biz.mapper")
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