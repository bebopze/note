package com.bebopze.cloud.api.biz;

import com.bebopze.framework.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableTransactionManagement
@ComponentScan("com.bebopze")
//@MapperScan("com.bebopze.cloud.api.biz.mapper")
//@EnableDubbo
public class BizApp {

    public static void main(String[] args) {
        SpringApplication.run(BizApp.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

}

