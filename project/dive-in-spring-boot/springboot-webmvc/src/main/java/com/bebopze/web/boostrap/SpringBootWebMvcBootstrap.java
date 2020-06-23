package com.bebopze.web.boostrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Web MVC 引导类
 *
 * @author bebopze
 * @since 2018/5/21
 */
@SpringBootApplication(scanBasePackages = "com.bebopze.web")
public class SpringBootWebMvcBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebMvcBootstrap.class, args);
    }

}
