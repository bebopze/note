package com.bebopze.jdk.datastructure;

import java.util.Arrays;

/**
 * 8、堆
 *
 * @author bebopze
 * @date 2020/8/26
 */
public class _08__Heap {


    public static void main(String[] args) {

        test__1();
    }


    private static void test__1() {

        Heap heap = new Heap(8);

        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }

        System.out.println(heap.toString());
    }


}


class Heap {

    // 数组，从下标1开始存储数据
    private int[] a;
    // 堆可以存储的最大数据个数
    private int n;
    // 堆中已经存储的数据个数
    private int count;


    public Heap(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }


    public void insert(int data) {

        if (count >= n) {
            // 堆满了
            return;
        }
        ++count;
        a[count] = data;
        int i = count;

        // 自下往上堆化
        while (i / 2 > 0 && a[i] > a[i / 2]) {

            // swap()函数作用：交换下标为i和i/2的两个元素
            swap(a, i, i / 2);
            i = i / 2;
        }
    }


    private void swap(int[] a, int i, int i_) {

    }


    @Override
    public String toString() {
        return "Heap{" +
                "a=" + Arrays.toString(a) +
                ", n=" + n +
                ", count=" + count +
                '}';
    }
}