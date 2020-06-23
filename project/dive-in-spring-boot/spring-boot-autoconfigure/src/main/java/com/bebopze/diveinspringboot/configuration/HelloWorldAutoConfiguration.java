package com.bebopze.diveinspringboot.configuration;

import com.bebopze.diveinspringboot.annotation.EnableHelloWorld;
import com.bebopze.diveinspringboot.condition.ConditionalOnSystemProperty;
import org.springframework.context.annotation.Configuration;

/**
 * HelloWorld 自动装配
 *
 * @author bebopze
 * @since 2018/5/15
 */
@Configuration // Spring 模式注解装配
@EnableHelloWorld // Spring @Enable 模块装配
@ConditionalOnSystemProperty(name = "user.name", value = "bebopze") // 条件装配
public class HelloWorldAutoConfiguration {
}
