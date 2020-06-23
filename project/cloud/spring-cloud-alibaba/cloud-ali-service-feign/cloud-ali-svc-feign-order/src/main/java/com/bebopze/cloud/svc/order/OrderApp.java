package com.bebopze.cloud.svc.order;

import com.bebopze.framework.util.IdWorker;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.bebopze")
@MapperScan("com.bebopze.cloud.svc.order.mapper")
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(2, 1);
    }
}