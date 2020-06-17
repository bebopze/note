package com.junzijian.jdk.string;

import java.io.InputStream;

/**
 * @author bebop
 * @date 2020/6/12
 */
public class StringDemo {

    public static void main(String[] args) {

//        InputStream in = System.in;

        String s1 = "";
        s1 = "String常量池";

        StringBuffer sb = new StringBuffer("String缓冲区");
        sb = sb.append("--------------");

        System.out.println(s1);
    }
}
