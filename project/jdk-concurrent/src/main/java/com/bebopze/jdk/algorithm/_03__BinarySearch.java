package com.bebopze.jdk.algorithm;

/**
 * 3、二分查找
 *
 * @author bebopze
 * @date 2020/8/24
 */
public class _03__BinarySearch {


    public static void main(String[] args) {

        // 简易
        test__1();

        // 变体
        test__2();
    }


    private static void test__1() {

        int[] arr = new int[]{1, 5, 8, 9, 12, 56, 87, 99};


        int idx_1 = binarySearch_1(arr, 9);
        int idx_2 = binarySearch_2(arr, 100);

        System.out.println(idx_1);
        System.out.println(idx_2);
    }


    private static void test__2() {

        int[] arr = new int[]{1, 5, 9, 13, 17, 17, 17, 66, 88, 88, 99};

        System.out.println("查找第一个值等于给定值的元素     ------- " + bsearch_1(arr, 17));
        System.out.println("查找最后一个值等于给定值的元素    ------- " + bsearch_2(arr, 17));
        System.out.println("查找第一个大于等于给定值的元素    ------- " + bsearch_3(arr, 18));
        System.out.println("查找最后一个小于等于给定值的元素  ------- " + bsearch_4(arr, 17));
    }

    // ------------------------- 二分查找 给定值查找 实现 ----------------------------

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


    // ----------------------------- 二分查找 变体 实现 -------------------------------


    /**
     * 1、查找第一个值等于给定值的元素
     *
     * @param a
     * @param value
     * @return
     */
    public static int bsearch_1(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (a[mid - 1] != value)) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 2、查找最后一个值等于给定值的元素
     *
     * @param a
     * @param value
     * @return
     */
    public static int bsearch_2(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == a.length - 1) || (a[mid + 1] != value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 3、查找第一个大于等于给定值的元素
     *
     * @param a
     * @param value
     * @return
     */
    public static int bsearch_3(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= value) {
                if ((mid == 0) || (a[mid - 1] < value)) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 4、查找最后一个小于等于给定值的元素
     *
     * @param a
     * @param value
     * @return
     */
    public static int bsearch_4(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else {
                if ((mid == a.length - 1) || (a[mid + 1] > value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

}
