package com.junzijian.cloud.svc.biz;

//import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableFeignClients("com.junzijian.cloud.client")
@SpringBootApplication//(exclude = PageHelperAutoConfiguration.class)
@ComponentScan("com.junzijian")
//@EntityScan("com.junzijian.cloud.framework.model")
public class BizApp {

    public static void main(String[] args) {
        SpringApplication.run(BizApp.class, args);
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
