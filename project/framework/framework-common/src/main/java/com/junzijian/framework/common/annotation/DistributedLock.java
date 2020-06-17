package com.junzijian.framework.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bebop
 * @date 2018/8/3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

    /**
     * 当前锁的：自动过期时间  （单位：秒）默认值：1s
     *
     * @return
     */
    long value() default 1;
}
