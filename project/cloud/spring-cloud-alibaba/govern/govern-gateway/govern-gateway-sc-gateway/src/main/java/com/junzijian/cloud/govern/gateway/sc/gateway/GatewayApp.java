package com.junzijian.cloud.govern.gateway.sc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhe
 * @date 2019/3/30
 */
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }


    @RequestMapping(value = "/fallbackcontroller")
    public Map<String, String> fallBackController() {
        Map<String, String> resp = new HashMap();
        resp.put("success", "false");
        resp.put("code", "-100");
        resp.put("msg", "service not available");
        return resp;
    }
}
