package com.bebopze.jdk.test;

/**
 * @author bebopze
 * @date 2020/12/22
 */
public class Test {


    public static void main(String[] args) {

        test_1();
    }


    /**
     * 基本数据类型    直接赋值都是 ==
     */
    private static void test_1() {

        int a = 1;
        int b = 1;
        System.out.println(a == b);     // true


        int c = 200;
        int d = 200;
        System.out.println(c == d);     // true
    }
}
