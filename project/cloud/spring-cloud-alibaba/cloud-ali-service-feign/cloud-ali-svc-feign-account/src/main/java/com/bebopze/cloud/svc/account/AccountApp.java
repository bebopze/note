package com.bebopze.cloud.svc.account;

import com.bebopze.framework.util.IdWorker;
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
@MapperScan("com.bebopze.cloud.svc.account.mapper")
public class AccountApp {

    public static void main(String[] args) {
        SpringApplication.run(AccountApp.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(5, 1);
    }
}
