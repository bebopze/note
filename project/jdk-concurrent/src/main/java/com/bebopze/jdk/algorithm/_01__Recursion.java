package com.bebopze.jdk.algorithm;

/**
 * 1、递归
 *
 * @author bebopze
 * @date 2020/8/21
 */
public class _01__Recursion {


    public static void main(String[] args) {

        test__1();
    }


    private static void test__1() {

        System.out.println("递归f1：" + f1(10));
        System.out.println("非递归f1_：" + f1_(10));


        System.out.println("递归f2：" + f2(10));
        System.out.println("非递归f2_：" + f2_(10));
    }


    // ------------------------- 递归 -> 非递归（for 循环） -------------------------

    // 递归
    static int f1(int n) {
        if (n == 1) {
            return 1;
        }
        return f1(n - 1) + 1;
    }

    // 非递归（for 循环）
    static int f1_(int n) {

        int ret = 1;

        // 非递归（for 循环）
        for (int i = 2; i <= n; ++i) {
            ret = ret + 1;
        }
        return ret;
    }


    // 递归
    static int f2(int n) {

        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        return f2(n - 1) + f2(n - 2);
    }

    // 非递归（for 循环）
    static int f2_(int n) {

        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int ret = 0;
        int pre = 2;
        int prepre = 1;

        // 非递归（for 循环）
        for (int i = 3; i <= n; ++i) {
            ret = pre + prepre;
            prepre = pre;
            pre = ret;
        }

        return ret;
    }


    // -------------------------


}
