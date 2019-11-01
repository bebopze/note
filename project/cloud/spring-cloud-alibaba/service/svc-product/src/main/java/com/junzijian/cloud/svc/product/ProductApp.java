package com.junzijian.cloud.svc.product;

import com.junzijian.framework.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients("com.junzijian.cloud.client")
@SpringBootApplication
@ComponentScan("com.junzijian")
@MapperScan("com.junzijian.cloud.svc.product.mapper")
public class ProductApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(3, 1);
    }
}