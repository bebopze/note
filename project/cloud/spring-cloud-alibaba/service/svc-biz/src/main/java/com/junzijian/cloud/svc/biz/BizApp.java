package com.junzijian.cloud.svc.biz;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication//(exclude = PageHelperAutoConfiguration.class)
@ComponentScan("com.junzijian")
//@EntityScan("com.junzijian.cloud.framework.model")
public class BizApp {

    public static void main(String[] args) {
        SpringApplication.run(BizApp.class, args);
    }

}
