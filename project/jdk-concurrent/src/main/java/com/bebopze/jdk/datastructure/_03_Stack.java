package com.bebopze.jdk.datastructure;

/**
 * 3、栈
 *
 * @author bebopze
 * @date 2020/8/20
 */
public class _03_Stack {


    // 先进后出     后进先出


    // 时间复杂度：
    //      O(1)

    // 空间复杂度：
    //      O(n)


    public static void main(String[] args) {

        test__ArrayStack();

        test__LinkedStack();
    }


    private static void test__ArrayStack() {

        ArrayStack<Integer> stack = new ArrayStack<>(3);

        System.out.println(stack.push(1));
        System.out.println(stack.push(2));
        System.out.println(stack.push(3));
        System.out.println(stack.push(4));


        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    private static void test__LinkedStack() {

        LinkedStack<Integer> stack = new LinkedStack<>();

        System.out.println(stack.push(1));
        System.out.println(stack.push(2));
        System.out.println(stack.push(3));
        System.out.println(stack.push(4));


        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

}


/**
 * 栈
 *
 * @param <T>
 */
interface IStack<T> {

    /**
     * 入栈
     *
     * @param e
     * @return
     */
    boolean push(T e);

    /**
     * 出栈
     *
     * @return
     */
    T pop();
}


/**
 * 顺序栈      ->  数组 实现
 *
 * @param <T>
 */
class ArrayStack<T> implements IStack<T> {

    // 数组
    private Object[] items;

    // 栈的大小
    private int n;

    // 栈中元素个数
    private int count;


    /**
     * 初始化数组，申请一个大小为 capacity 的数组空间
     *
     * @param capacity
     */
    public ArrayStack(int capacity) {
        this.items = new Object[capacity];
        this.n = capacity;
        this.count = 0;
    }


    @Override
    public boolean push(T item) {
        // 数组空间不够了，直接返回false，入栈失败。
        if (count == n) {
            return false;
        }
        // 将item放到下标为count的位置，并且count加一
        items[count] = item;
        ++count;
        return true;
    }

    @Override
    public T pop() {
        // 栈为空，则直接返回null
        if (count == 0) {
            return null;
        }
        // 返回下标为count-1的数组元素，并且栈中元素个数count减一
        Object tmp = items[count - 1];
        --count;
        return (T) tmp;
    }
}

/**
 * 链式栈      ->  链表 实现
 *
 * @param <T>
 */
class LinkedStack<T> implements IStack<T> {

    @Override
    public boolean push(T e) {

        return false;
    }

    @Override
    public T pop() {

        return null;
    }
}