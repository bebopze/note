package com.junzijian.diveinspringboot.configuration;

import com.junzijian.diveinspringboot.annotation.EnableHelloWorld;
import com.junzijian.diveinspringboot.condition.ConditionalOnSystemProperty;
import org.springframework.context.annotation.Configuration;

/**
 * HelloWorld 自动装配
 *
 * @author junzijian
 * @since 2018/5/15
 */
@Configuration // Spring 模式注解装配
@EnableHelloWorld // Spring @Enable 模块装配
@ConditionalOnSystemProperty(name = "user.name", value = "junzijian") // 条件装配
public class HelloWorldAutoConfiguration {
}
