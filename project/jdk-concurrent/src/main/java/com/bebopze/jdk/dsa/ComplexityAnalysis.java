package com.bebopze.jdk.dsa;

/**
 * 复杂度分析
 *
 * @author bebopze
 * @date 2020/8/18
 */
public class ComplexityAnalysis {


    public static void main(String[] args) {

        // 1、事后统计法


        // 粗略地估算   -->   大 O 复杂度表示法
//        test__O();


        // 时间复杂度分析
//        test__TimeComplexityAnalysis();
    }


    // 1. 只关注循环执行次数最多的一段代码


    /**
     * 2. 加法法则：总复杂度等于量级最大的那段代码的复杂度
     * -
     * -  100、n、n²
     * -
     * -      T1(n) = O(f(n)) ，T2(n)=O(g(n))；  那么 T(n) = T1(n) + T2(n) = max(O(f(n)), O(g(n)))  =  O(max(f(n), g(n)))
     *
     * @param n
     * @return
     */
    int cal_2(int n) {

        int sum_1 = 0;
        int p = 1;

        // 100
        for (; p < 100; ++p) {
            sum_1 = sum_1 + p;
        }


        int sum_2 = 0;
        int q = 1;

        // n
        for (; q < n; ++q) {
            sum_2 = sum_2 + q;
        }


        int sum_3 = 0;
        int i = 1;
        int j = 1;

        // n²
        for (; i <= n; ++i) {
            j = 1;
            for (; j <= n; ++j) {
                sum_3 = sum_3 + i * j;
            }
        }

        return sum_1 + sum_2 + sum_3;
    }


    // -----------------------------------------------------------------------------------------------------------------


    /**
     * 3. 乘法法则：嵌套代码的复杂度等于嵌套内外代码复杂度的乘积
     * -
     * -    T(n) = T1(n) * T2(n) = O(n*n) = O(n2)
     * -
     *
     * @param n
     * @return
     */
    int cal_3(int n) {
        int ret = 0;
        int i = 1;
        // n
        for (; i < n; ++i) {
            // n
            ret = ret + f(i);
        }
        return ret;
    }

    int f(int n) {
        int sum = 0;
        int i = 1;
        for (; i < n; ++i) {
            sum = sum + i;
        }
        return sum;
    }

}




