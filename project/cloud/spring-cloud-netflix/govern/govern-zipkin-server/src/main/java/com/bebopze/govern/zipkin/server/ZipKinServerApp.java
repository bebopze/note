package com.bebopze.govern.zipkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

@EnableDiscoveryClient
@EnableZipkinServer
@SpringBootApplication
public class ZipKinServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ZipKinServerApp.class, args);
    }

}
