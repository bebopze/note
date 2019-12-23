package com.junzijian.diveinspringboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
//@ServletComponentScan(basePackages = "com.junzijian.diveinspringboot.web.servlet")
public class DiveInSpringBootApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DiveInSpringBootApplication.class)
//				.web(WebApplicationType.NONE)
                .run(args);
//		SpringApplication.run(DiveInSpringBootApplication.class, args);
    }
}
