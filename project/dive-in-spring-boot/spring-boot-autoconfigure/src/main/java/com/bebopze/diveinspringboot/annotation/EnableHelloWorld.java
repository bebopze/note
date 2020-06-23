package com.bebopze.diveinspringboot.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活 HelloWorld 模块
 *
 * @author bebopze
 * @since 2018/5/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import(HelloWorldConfiguration.class)
@Import(HelloWorldImportSelector.class)
public @interface EnableHelloWorld {
}
