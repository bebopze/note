package com.junzijian.framework.common.model;

/**
 * @author bebop
 * @date 2019/10/24
 */
public class CustomContext {

    private static final ThreadLocal<CustomContextVO> junzijianContext = new ThreadLocal<>();


    public static CustomContextVO get() {
        return junzijianContext.get();
    }

    public static void add(CustomContextVO customContextVO) {
        junzijianContext.set(customContextVO);
    }

    public static void remove() {
        junzijianContext.remove();
    }
}
