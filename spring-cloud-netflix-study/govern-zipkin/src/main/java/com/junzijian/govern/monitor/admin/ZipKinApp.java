package com.junzijian.govern.monitor.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class ZipKinApp {

    public static void main(String[] args) {
        SpringApplication.run(ZipKinApp.class, args);
    }

}
