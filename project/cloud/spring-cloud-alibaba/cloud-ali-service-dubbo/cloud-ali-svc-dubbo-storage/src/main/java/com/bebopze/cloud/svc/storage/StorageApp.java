package com.bebopze.cloud.svc.storage;

import com.bebopze.framework.util.IdWorker;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.bebopze")
@MapperScan("com.bebopze.cloud.svc.storage.mapper")
@EnableDubbo//(scanBasePackages = "com.bebopze.cloud.svc.storage.service.impl")
public class StorageApp {

    public static void main(String[] args) {
        SpringApplication.run(StorageApp.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0, 1);
    }
}
