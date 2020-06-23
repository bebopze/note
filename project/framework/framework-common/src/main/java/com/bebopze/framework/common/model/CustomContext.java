package com.bebopze.framework.common.model;

/**
 * @author bebopze
 * @date 2019/10/24
 */
public class CustomContext {

    private static final ThreadLocal<CustomContextVO> bebopzeContext = new ThreadLocal<>();


    public static CustomContextVO get() {
        return bebopzeContext.get();
    }

    public static void add(CustomContextVO customContextVO) {
        bebopzeContext.set(customContextVO);
    }

    public static void remove() {
        bebopzeContext.remove();
    }
}
