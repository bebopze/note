package com.bebopze.jdk.patterndesign;

import com.bebopze.jdk.proxy.CglibProxy;
import com.bebopze.jdk.proxy.JDKProxy;

/**
 * 5. 代理模式
 *
 * @see JDKProxy
 * @see CglibProxy
 *
 * @author bebopze
 * @date 2020/8/7
 */
public class _5_Proxy {


    // 核心场景：添加 跟原始类 无关的 功能      ---> 添加 业务不想关的 非功能性需求，如：监控、统计、鉴权、限流、事务、幂等、日志。
    //
    // 与业务功能解耦


    public static void main(String[] args) {

        test__JDK_Proxy();

        test__Cglib_Proxy();
    }


    private static void test__JDK_Proxy() {

        JDKProxy.test_Proxy();
    }


    private static void test__Cglib_Proxy() {

        CglibProxy.test_Proxy();
    }
}
