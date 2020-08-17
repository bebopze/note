package com.bebopze.jdk.frame.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Builder模式
 *
 * @author bebopze
 * @date 2020/8/17
 */
public class _01__Cache {


    public static void main(String[] args) {


        // 必须使用 Builder 模式的主要原因是，在真正构造 Cache 对象的时候，我们必须做一些必要的参数校验。
        test__CacheBuilder();
    }


    private static void test__CacheBuilder() {

        Cache<String, String> cache = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        cache.put("k1", "v1");
        String value = cache.getIfPresent("k1");

        System.out.println(value);
    }
}
