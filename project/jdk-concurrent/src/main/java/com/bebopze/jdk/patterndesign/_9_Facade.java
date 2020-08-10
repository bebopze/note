package com.bebopze.jdk.patterndesign;

/**
 * 9. 门面模式
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class _9_Facade {

    // 应用场景：
    //  1、解决易用性问题
    //      封装系统的底层实现，隐藏系统的复杂性，提供一组更加简单易用、更高层的接口。
    //
    //  2、解决性能问题
    //      多个接口调用替换为一个门面接口调用，减少网络通信成本
    //
    //  3、解决分布式事务问题
    //      在一个 事务(门面接口) 中执行两个 SQL操作(子接口)


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        // 1、解决易用性问题
        test_1();

        // 2、解决性能问题
        test_2();

        // 3、解决分布式事务问题
        test_3();
    }


    // ----------------------------------实现-----------------------------


    private static void test_1() {

        // 封装系统的底层实现，隐藏系统的复杂性，提供一组更加简单易用、更高层的接口。

//        // logic...
//        interface_1();
//        // logic...
//
//        // logic...
//        interface_2();
//        // logic...
//
//        // logic...
//        interface_3();
//        // logic...
    }


    private static void test_2() {

        // 多个接口调用替换为一个门面接口调用，减少网络通信成本

//        interface_1();
//        interface_2();
//        interface_3();

    }


    private static void test_3() {

        // 在一个 事务(门面接口) 中执行两个 SQL操作(子接口)

//        sql_1();
//        sql_2();
    }

}
