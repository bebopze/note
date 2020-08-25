package com.bebopze.jdk.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 2、排序
 *
 * @author bebopze
 * @date 2020/8/21
 */
public class _02___Sort {


    public static void main(String[] args) {

        int[] arr = new int[]{6, 2, 5, 3, 7, 4, 1};

        bubbleSort(arr);
        print(arr);


        insertionSort(arr);
        print(arr);

    }


    // -------------------------- O(n²) --------------------------

    /**
     * 1、冒泡排序
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {


    }


    /**
     * 2、插入排序
     *
     * @param arr 数组
     */
    public static void insertionSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; ++i) {
            int value = arr[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (arr[j] > value) {
                    // 数据移动
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            // 插入数据
            arr[j + 1] = value;
        }
    }


    /**
     * 3、选择排序（Selection Sort）
     *
     * @param arr
     */
    public static void selectionSort(int[] arr) {


    }


    // -------------------------- O(nlogⁿ) --------------------------

    /**
     * 4、归并排序
     */
//    void merge(A[p...r], A[p...q], A[q+1...r]) {
//        var i :=p，j:=q + 1，k:=0 // 初始化变量i, j, k
//        var tmp :=new array[0...r - p] // 申请一个大小跟A[p...r]一样的临时数组
//        while i <= q AND j<=r do {
//            if A[i] <= A[j] {
//                tmp[k++] = A[i++] // i++等于i:=i+1
//            } else{
//                tmp[k++] = A[j++]
//            }
//        }
//
//        // 判断哪个子数组中有剩余的数据
//        var start :=i，end:=q
//        if j <= r then start :=j, end:=r
//
//        // 将剩余的数据拷贝到临时数组tmp
//        while start <= end do {
//            tmp[k++] = A[start++]
//        }
//
//        // 将tmp中的数组拷贝回A[p...r]
//        for i:=0 to r -p do {
//            A[p + i] = tmp[i]
//        }
//    }


    /**
     * 5、快速排序
     */
//    // 快速排序，A是数组，n表示数组的大小
//    quick_sort(A, n) {
//        quick_sort_c(A, 0, n-1)
//    }
//    // 快速排序递归函数，p,r为下标
//    quick_sort_c(A, p, r) {
//        if p >= r then return
//
//                q = partition(A, p, r) // 获取分区点
//        quick_sort_c(A, p, q-1)
//        quick_sort_c(A, q+1, r)
//    }


    /**
     * 4、归并排序（Merge Sort）
     *
     * @param arr
     */
    public static void mergeSort(int[] arr) {


    }


    /**
     * 5、快速排序（Quick Sort）
     *
     * @param arr
     */
    public static void quickSort(int[] arr) {


    }


    // -------------------------- O(n) --------------------------

    /**
     * 6、桶排序（Bucket sort）
     *
     * @param arr
     */
    public static void bucketSort(int[] arr) {


    }

    /**
     * 7、计数排序（Counting sort）
     *
     * @param arr
     */
    public static void countSort(int[] arr) {


    }

    /**
     * 8、基数排序（Radix sort）
     *
     * @param arr
     */
    public static void radixSort(int[] arr) {


    }


    // --------------------------------------------------

    private static void print(int[] arr) {
        System.out.println(JSON.toJSONString(arr));
    }

}

