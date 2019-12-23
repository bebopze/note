package com.junzijian.diveinspringboot.bootstrap;

import com.junzijian.diveinspringboot.condition.ConditionalOnSystemProperty;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 系统属性条件引导类
 *
 * @author junzijian
 * @since 2018/5/15
 */
public class ConditionalOnSystemPropertyBootstrap {

    @Bean
    @ConditionalOnSystemProperty(name = "user.name", value = "junzijian")
    public String helloWorld() {
        return "Hello,World 君子剑";
    }


    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ConditionalOnSystemPropertyBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // 通过名称和类型获取 helloWorld Bean
        String helloWorld = context.getBean("helloWorld", String.class);

        System.out.println("helloWorld Bean : " + helloWorld);

        // 关闭上下文
        context.close();
    }
}
