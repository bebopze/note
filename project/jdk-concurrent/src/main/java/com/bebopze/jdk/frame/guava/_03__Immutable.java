package com.bebopze.jdk.frame.guava;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable模式
 *
 * @author bebopze
 * @date 2020/8/17
 */
public class _03__Immutable {


    public static void main(String[] args) {

        test__ImmutableList();
    }


    private static void test__ImmutableList() {

        List<String> originalList = new ArrayList<>();
        originalList.add("a");
        originalList.add("b");
        originalList.add("c");


        // JDK      原list -> 影响jdk_list
        // this直接赋值
        List<String> jdk_UnmodifiableList = Collections.unmodifiableList(originalList);

        // Guava    原list -> 不影响guava_list
        // 元素遍历，新容器存储
        List<String> guava_ImmutableList = ImmutableList.copyOf(originalList);


        jdk_UnmodifiableList.add("d"); // 抛出UnsupportedOperationException
        guava_ImmutableList.add("d");  // 抛出UnsupportedOperationException

        originalList.add("d");


        // a b c d
        print(originalList);

        // a b c d
        print(jdk_UnmodifiableList);

        // a b c
        print(guava_ImmutableList);
    }


    private static void print(List<String> list) {
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

}
