package com.bebopze.jdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bebopze
 * @date 2020/11/25
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface CheckGetter {


}
