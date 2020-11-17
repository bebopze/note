package com.bebopze.jdk.string;

/**
 * Java 8个基本数据类型
 *
 * @author bebopze
 * @date 2020/11/17
 */
public class BasicDataType {


    // -------------- 科学记数法            3.14E3 / 3.14E-3
    //
    //      结尾的 “E+数字”   ->   表示  E之前的数字  要  ✖️  10的多少次方
    //      结尾的 “E-数字”   ->   表示  E之前的数字  要  ➗  10的多少次方
    //
    //
    // 比如
    //
    //      3.14E3      ->  3.14×1000  =  3140
    //
    //      3.14E-3     ->  3.14/1000  =  0.00314


//    public static void main(String[] args) {
//
//        // 1 byte(字节)      ->      8个 二进制位      00000000
//
//
//        // ---------- 4个整数类型：
//        byte    a = 1;             // 1 byte        8-bit	    -128	+127
//        short   b = 2;             // 2 byte	    16-bit	    -2^15	+2^15-1
//        int     c = 3;             // 4 byte	    32-bit	    -2^31	+2^31-1
//        long    d = 4L;            // 8 byte	    64-bit	    -2^63	+2^63-1
//
//
//        // ---------- 2个浮点类型：
//        float   e = 5.00F;          // 4 byte	    32-bit	    2^-149      +2^128-1
//        double  f = 6.00;           // 8 byte	    64-bit	    2^-1074     +2^1024-1
//
//
//        // ---------- 1个字符类型：
//        char g = '7';               // 2 byte	    16-bit	    Unicode 0	Unicode 2^16-1
//
//
//        // ---------- 1个布尔类型：
//        boolean h = true;           // JVM    ->   int   0/1
//    }


    public static void main(String[] args) {

        // byte
        System.out.println("基本类型：byte 二进制位数：" + Byte.SIZE);
        System.out.println("包装类：java.lang.Byte");
        System.out.println("最小值：Byte.MIN_VALUE=" + Byte.MIN_VALUE);
        System.out.println("最大值：Byte.MAX_VALUE=" + Byte.MAX_VALUE);
        System.out.println();

        // short
        System.out.println("基本类型：short 二进制位数：" + Short.SIZE);
        System.out.println("包装类：java.lang.Short");
        System.out.println("最小值：Short.MIN_VALUE=" + Short.MIN_VALUE);
        System.out.println("最大值：Short.MAX_VALUE=" + Short.MAX_VALUE);
        System.out.println();

        // int
        System.out.println("基本类型：int 二进制位数：" + Integer.SIZE);
        System.out.println("包装类：java.lang.Integer");
        System.out.println("最小值：Integer.MIN_VALUE=" + Integer.MIN_VALUE);
        System.out.println("最大值：Integer.MAX_VALUE=" + Integer.MAX_VALUE);
        System.out.println();

        // long
        System.out.println("基本类型：long 二进制位数：" + Long.SIZE);
        System.out.println("包装类：java.lang.Long");
        System.out.println("最小值：Long.MIN_VALUE=" + Long.MIN_VALUE);
        System.out.println("最大值：Long.MAX_VALUE=" + Long.MAX_VALUE);
        System.out.println();


        // float
        System.out.println("基本类型：float 二进制位数：" + Float.SIZE);
        System.out.println("包装类：java.lang.Float");
        System.out.println("最小值：Float.MIN_VALUE=" + Float.MIN_VALUE);
        System.out.println("最大值：Float.MAX_VALUE=" + Float.MAX_VALUE);
        System.out.println();

        // double
        System.out.println("基本类型：double 二进制位数：" + Double.SIZE);
        System.out.println("包装类：java.lang.Double");
        System.out.println("最小值：Double.MIN_VALUE=" + Double.MIN_VALUE);
        System.out.println("最大值：Double.MAX_VALUE=" + Double.MAX_VALUE);
        System.out.println();


        // char
        System.out.println("基本类型：char 二进制位数：" + Character.SIZE);
        System.out.println("包装类：java.lang.Character");
        // 以数值形式 而不是字符形式   将Character.MIN_VALUE输出到控制台
        System.out.println("最小值：Character.MIN_VALUE=" + (int) Character.MIN_VALUE);
        // 以数值形式 而不是字符形式   将Character.MAX_VALUE输出到控制台
        System.out.println("最大值：Character.MAX_VALUE=" + (int) Character.MAX_VALUE);
    }

}
