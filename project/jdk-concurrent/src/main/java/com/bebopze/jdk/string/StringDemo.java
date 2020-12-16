package com.bebopze.jdk.string;

/**
 * - String、String Pool（字符串常量池）                               - https://www.cnblogs.com/fangfuhai/p/5500065.html
 *
 * @author bebopze
 * @date 2020/6/12
 */
public class StringDemo {

    public static void main(String[] args) {

        test_1();

//        test_2();
    }


    private static void test_1() {

        // ================ 两种赋值方式 ================

        // --------------------------------------- 1、引用类型  赋值方式 ---------------------------------------
        // -
        // -   1、在堆中 创建String对象，在构造函数中 -> 传入value字段的值 -> "abc"    返回String对象的堆地址引用
        // -
        // -   2、将 堆地址引用  赋值给  变量str_1


        String str_1 = new String("abc");
        String str_2 = new String("abc");


        // --------- intern
        // -
        // -   1、在 string-pool（字符串常量池） 中"查缓存"   ==>   有 -> 返回 常量池引用
        // -
        // -   2、无 -> 将 堆中对象  copy一份到  string-pool（字符串常量池）

        String str_intern = str_1.intern();


        // --------------------------------------- 2、“基本类似” 赋值方式 ---------------------------------------
        // -
        // -   1、在 string-pool（字符串常量池） 中"查缓存"   ==>   有 -> 返回引用   /  无 -> 创建"abc"对象，返回引用
        // -
        // -   2、将 常量池中的引用  -->  赋值给 变量s_1

        String s_1 = "abc";
        String s_2 = "abc";


        // ---------------------------------------------------------------------

        System.out.println("=========================================");


        System.out.println("str_1 == str_2   ->   " + (str_1 == str_2));
        System.out.println("  s_1 == s_2     ->   " + (s_1 == s_2));


        System.out.println("-----------------------------------------");


        System.out.println("str_1 == s_1   ->   " + (str_1 == s_1));


        System.out.println("-----------------------------------------");


        System.out.println("str_intern == str_1   ->   " + (str_intern == str_1));


        System.out.println("-----------------------------------------");


        System.out.println("str_intern == s_1   ->   " + (str_intern == s_1));
        System.out.println("str_intern == s_2   ->   " + (str_intern == s_2));


        System.out.println("=========================================");
    }


    private static void test_2() {


//        InputStream in = System.in;


        String s1 = "";
        s1 = "String常量池";

        StringBuffer sb = new StringBuffer("String缓冲区");
        sb = sb.append("--------------");

        System.out.println(s1);
    }
}
