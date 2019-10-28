package com.junzijian.cloud.svc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.junzijian")
public class CASUserApp {

    public static void main(String[] args) {
        SpringApplication.run(CASUserApp.class, args);
    }
}
