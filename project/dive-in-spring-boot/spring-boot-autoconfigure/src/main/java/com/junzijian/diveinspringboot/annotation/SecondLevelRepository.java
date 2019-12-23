package com.junzijian.diveinspringboot.annotation;

import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * 二级 {@link Repository}
 *
 * @author junzijian
 * @since 2018/5/14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FirstLevelRepository
public @interface SecondLevelRepository {

    String value() default "";

}
