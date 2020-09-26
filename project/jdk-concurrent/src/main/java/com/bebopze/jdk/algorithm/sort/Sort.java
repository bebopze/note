package com.bebopze.jdk.algorithm.sort;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bebopze.jdk.algorithm.sort._01___Sort.print;

/**
 * 十大经典 排序算法                    - https://www.cnblogs.com/guoyaohua/p/8600214.html
 * -
 * -                                  - https://zh.wikipedia.org/wiki/排序算法
 * -
 *
 * @author bebopze
 * @date 2020/9/25
 */
public class Sort {


    public static void main(String[] args) {

        int[] arr1 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr2 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr3 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr4 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr5 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr6 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr7 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr8 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr9 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr10 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr11 = new int[]{6, 2, 5, 3, 7, 4, 1};
        int[] arr12 = new int[]{6, 2, 5, 3, 7, 4, 1};


        // ------------------------------------------------------------------------------------------------

        bubbleSort(arr1);
        print(arr1);


        insertionSort(arr2);
        print(arr2);


        selectionSort(arr3);
        print(arr3);


        // ------------------------------------------------

        shellSort(arr4);
        print(arr4);


        arr5 = mergeSort(arr5);
        print(arr5);


        quickSort(arr6, 0, arr6.length - 1);
        print(arr6);


        heapSort(arr7);
        print(arr7);


        // ------------------------------------------------


        List<Integer> list8 = bucketSort(Ints.asList(arr8), arr8.length);
        print(list8);


        countingSort(arr9);
        print(arr9);


        radixSort(arr10);
        print(arr10);


        // ------------------------------------------------------------------------------------------------


        // DualPivot QuickSort
        Arrays.sort(arr11);
        print(arr11);


        List<Integer> list12 = Ints.asList(arr12);
        Collections.sort(list12);
        print(list12);
    }


    // ------------------------------------------------------------------------------------------------


    /**
     * 1、冒泡排序
     * -
     * - 从左往右  两两相比   -->   左 < 右 --> 左右换位  左>=右，则跳过，继续往下比较
     * -
     * - 每一轮 都把最大的 移到最右边
     * -
     *
     * @param array
     * @return
     */
    public static void bubbleSort(int[] array) {
        if (array.length <= 1) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j + 1] < array[j]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }


    /**
     * 2、插入排序
     * -
     * - 从左往右   选取当前已知最大(Max)  与  已知最大的右边(v)   两两相比
     * -
     * -    v < Max   ->   依次向Max的左边 遍历比较      直到找到  x <= v < y    插入
     * -
     * -    Max <= v   跳过往下      v 变为 Max
     * -
     * -
     * -  每一轮  把当前已知Max右边的v  插入到   Max左边 x <= v < y  的位置
     * -
     * - 因为 Max左边的区间 均为从小到大有序的
     * - 每轮比较其实就是把 Max右边的v  插入到 Max左边有序区间中的  x <= v < y  的位置
     *
     * @param array
     * @return
     */
    public static void insertionSort(int[] array) {
        if (array.length <= 1) {
            return;
        }

        int current;
        for (int i = 0; i < array.length - 1; i++) {
            current = array[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && current < array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            array[preIndex + 1] = current;
        }
    }


    /**
     * 3、选择排序
     * -
     * -
     * - 从左往右    每一轮 两两相比 找出最小的数    放到上一轮min的右边    （即：上一轮min右边的v 和 当前轮min  互换位置）
     *
     * @param array
     * @return
     */
    public static void selectionSort(int[] array) {
        if (array.length <= 1) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                //找到最小的数
                if (array[j] < array[minIndex]) {
                    //将最小数的索引保存
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }


    // ------------------------------------------------------------------------------------------------


    /**
     * 4、希尔排序           // 插入排序 的一种更高效的改进版本
     * -
     * -
     * - 每轮次  以 length/2   对半划线      两个区间 都从0索引开始  一一对应  比较交换
     *
     * @param array
     * @return
     */
    public static void shellSort(int[] array) {

        int len = array.length;
        int temp, gap = len / 2;

        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            gap /= 2;
        }
    }

    public static void shellSort2(int[] arr) {
        int length = arr.length;
        int temp;
        for (int step = length / 2; step >= 1; step /= 2) {

            for (int i = step; i < length; i++) {
                temp = arr[i];
                int j = i - step;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + step] = arr[j];
                    j -= step;
                }
                arr[j + step] = temp;
            }

        }
    }


    /**
     * 5、归并排序
     * -
     * - 分治思想：分解(递归) + 合并
     * -
     * -    分解：1 -> 2 -> 4 -> ... -> 单个（拆无可拆）
     * -
     * -    合并：单个 两两比较 交换位置  -> ... -> 4 -> 2 -> 1
     * -
     * -
     * - 处理过程：
     * -
     * -    将大数据 等分到 不可拆分后
     * -
     * -    从下往上
     * -
     * -    也就是 从 最小拆分 往 最大拆分   进行  排序合并
     * -
     *
     * @param array
     * @return
     */
    public static int[] mergeSort(int[] array) {

        if (array.length < 2) {
            return array;
        }

        // 分解
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        // 递归分解
        int[] l = mergeSort(left);
        int[] r = mergeSort(right);


        // 递归合并（排序）
        int[] merge = merge(l, r);
        return merge;
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {

        int[] result = new int[left.length + right.length];

        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }

        return result;
    }


    /**
     * 6、快速排序
     * -
     * - 分治思想：分解(递归) + 分区
     * -
     * -
     * - 处理过程：
     * -
     * -    每次将选择好的 分区点 与 数组中数据   进行  比较和交换
     * -
     * -    交换完后，再如此往复
     * -
     * -    选择 下一次分区点，继续如上操作，直到数组有序
     * -
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static void quickSort(int[] array, int start, int end) {

        if (array.length <= 1 || start < 0 || end >= array.length || start > end) {
            return;
        }

        // 分区
        int smallIndex = partition(array, start, end);

        // 分解
        if (smallIndex > start) {
            quickSort(array, start, smallIndex - 1);
        }

        if (smallIndex < end) {
            quickSort(array, smallIndex + 1, end);
        }
    }

    /**
     * 快速排序算法——partition
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] array, int start, int end) {

        // 随机法
        int pivot = (int) (start + Math.random() * (end - start + 1));

        // 分区点 移到最后     -> 此时 array[end]  已变为 分区点元素
        swap(array, pivot, end);

        int smallIndex = start - 1;
        for (int i = start; i <= end; i++) {

            // array[end]  ->  分区点
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }

    /**
     * 交换数组内两个元素
     *
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    // -------------------------------------


    // 声明全局变量，用于记录数组array的长度
    static int len;

    /**
     * 7、堆排序
     *
     * @param array
     * @return
     */
    public static void heapSort(int[] array) {

        len = array.length;
        if (len <= 1) {
            return;
        }

        // 1、构建一个最大堆
        buildMaxHeap(array);

        // 2、循环将堆首位（最大值）与末位交换，然后再重新调整最大堆
        while (len > 0) {
            swap(array, 0, len - 1);
            len--;
            adjustHeap(array, 0);
        }
    }

    /**
     * 建立最大堆
     *
     * @param array
     */
    private static void buildMaxHeap(int[] array) {
        // 从最后一个非叶子节点开始 向上构造最大堆
        for (int i = (len / 2 - 1); i >= 0; i--) {
            adjustHeap(array, i);
        }
    }

    /**
     * 调整使之成为最大堆
     *
     * @param array
     * @param i
     */
    private static void adjustHeap(int[] array, int i) {

        int maxIndex = i;
        // 如果有左子树，且左子树大于父节点，则将最大指针指向左子树
        if (i * 2 < len && array[i * 2] > array[maxIndex]) {
            maxIndex = i * 2;
        }

        // 如果有右子树，且右子树大于父节点，则将最大指针指向右子树
        if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex]) {
            maxIndex = i * 2 + 1;
        }

        // 如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置
        if (maxIndex != i) {
            swap(array, maxIndex, i);
            adjustHeap(array, maxIndex);
        }
    }


    // ------------------------------------------------------------------------------------------------


    /**
     * 8、计数排序
     *
     * @param array
     * @return
     */
    public static void countingSort(int[] array) {
        if (array.length <= 1) {
            return;
        }

        int bias, min = array[0], max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        bias = 0 - min;
        int[] bucket = new int[max - min + 1];

        Arrays.fill(bucket, 0);

        for (int i = 0; i < array.length; i++) {
            bucket[array[i] + bias]++;
        }

        int index = 0, i = 0;
        while (index < array.length) {
            if (bucket[i] != 0) {
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else {
                i++;
            }
        }
    }


    /**
     * 9、桶排序
     *
     * @param array
     * @param bucketSize
     * @return
     */
    public static List<Integer> bucketSort(List<Integer> array, int bucketSize) {

        if (array == null || array.size() < 2) {
            return array;
        }

        int max = array.get(0), min = array.get(0);

        // 找到最大值最小值
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) > max) {
                max = array.get(i);
            }
            if (array.get(i) < min) {
                min = array.get(i);
            }
        }

        int bucketCount = (max - min) / bucketSize + 1;
        List<List<Integer>> bucketArr = new ArrayList<>(bucketCount);
        List<Integer> resultArr = new ArrayList<>();

        for (int i = 0; i < bucketCount; i++) {
            bucketArr.add(new ArrayList());
        }

        for (int i = 0; i < array.size(); i++) {
            bucketArr.get((array.get(i) - min) / bucketSize).add(array.get(i));
        }

        for (int i = 0; i < bucketCount; i++) {
            // 如果带排序数组中有重复数字时
            if (bucketSize == 1) {
                for (int j = 0; j < bucketArr.get(i).size(); j++) {
                    resultArr.add(bucketArr.get(i).get(j));
                }
            } else {
                if (bucketCount == 1) {
                    bucketSize--;
                }
                List<Integer> temp = bucketSort(bucketArr.get(i), bucketSize);
                for (int j = 0; j < temp.size(); j++) {
                    resultArr.add(temp.get(j));
                }
            }
        }

        return resultArr;
    }


    /**
     * 10、基数排序
     *
     * @param array
     * @return
     */
    public static void radixSort(int[] array) {

        if (array == null || array.length < 2) {
            return;
        }

        // 1.先算出最大数的位数；
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;
        }

        int mod = 10, div = 1;
        List<List<Integer>> bucketList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            bucketList.add(new ArrayList());
        }

        for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
            for (int j = 0; j < array.length; j++) {
                int num = (array[j] % mod) / div;
                bucketList.get(num).add(array[j]);
            }
            int index = 0;
            for (int j = 0; j < bucketList.size(); j++) {
                for (int k = 0; k < bucketList.get(j).size(); k++) {
                    array[index++] = bucketList.get(j).get(k);
                }
                bucketList.get(j).clear();
            }
        }
    }
}
