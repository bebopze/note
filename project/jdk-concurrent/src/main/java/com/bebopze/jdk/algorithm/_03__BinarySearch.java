package com.bebopze.jdk.algorithm;

/**
 * 3、二分查找
 *
 * @author bebopze
 * @date 2020/8/24
 */
public class _03__BinarySearch {


    public static void main(String[] args) {

        test__1();
    }


    private static void test__1() {

        int[] arr = new int[]{1, 5, 8, 9, 12, 56, 87, 99};


        int idx_1 = binarySearch_1(arr, 9);
        int idx_2 = binarySearch_2(arr, 100);

        System.out.println(idx_1);
        System.out.println(idx_2);
    }


    // ------------------------- 二分查找 实现 ----------------------------

    /**
     * 1、循环实现
     *
     * @param arr
     * @param value
     * @return
     */
    public static int binarySearch_1(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }


    /**
     * 2、递归实现
     *
     * @param arr
     * @param val
     * @return
     */
    public static int binarySearch_2(int[] arr, int val) {
        return binarySearch_recursion(arr, 0, arr.length - 1, val);
    }

    private static int binarySearch_recursion(int[] a, int low, int high, int value) {
        if (low > high) {
            return -1;
        }

        int mid = low + ((high - low) >> 1);
        if (a[mid] == value) {
            return mid;
        } else if (a[mid] < value) {
            return binarySearch_recursion(a, mid + 1, high, value);
        } else {
            return binarySearch_recursion(a, low, mid - 1, value);
        }
    }


}
