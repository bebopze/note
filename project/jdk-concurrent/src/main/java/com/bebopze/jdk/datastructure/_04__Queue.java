package com.bebopze.jdk.datastructure;

/**
 * 4、队列
 *
 * @author bebopze
 * @date 2020/8/21
 */
public class _04__Queue {


    // 先进先出                 尾进 -> 头出


    public static void main(String[] args) {


        /**
         * 消除 “数据搬移”   -->   “循环队列”
         */
        test__ArrayQueue();


        test__LinkedQueue();
    }


    private static void test__ArrayQueue() {

        IQueue<Integer> queue = new ArrayQueue<>(3);

        System.out.println(queue.enqueue(1));
        System.out.println(queue.enqueue(2));
        System.out.println(queue.enqueue(3));
        System.out.println(queue.enqueue(4));


        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

    private static void test__LinkedQueue() {

        IQueue<Integer> queue = new LinkedQueue<>();

        System.out.println(queue.enqueue(1));
        System.out.println(queue.enqueue(2));
        System.out.println(queue.enqueue(3));
        System.out.println(queue.enqueue(4));


        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }
}


/**
 * 队列
 */
interface IQueue<T> {

    /**
     * 入队 enqueue()    ->      放一个数据到队列尾部
     *
     * @param e
     * @return
     */
    boolean enqueue(T e);

    /**
     * 出队 dequeue()    ->      从队列头部取一个元素
     *
     * @return
     */
    T dequeue();
}


/**
 * 顺序队列     ->      用数组实现的队列
 *
 * @param <T>
 */
class ArrayQueue<T> implements IQueue<T> {


    // -------------- 存储容器

    // 数组容器：items
    private Object[] items;
    // 数组容器大小：n
    private int n = 0;


    // -------------- 队列 头、尾 指针

    // 队头下标（指针）
    private int head = 0;
    // 队尾下标（指针）
    private int tail = 0;


    /**
     * 申请一个大小为capacity的数组
     *
     * @param capacity
     */
    public ArrayQueue(int capacity) {
        items = new Object[capacity];
        n = capacity;
    }


    /**
     * 入队
     *
     * @param item
     * @return
     */
    @Override
    public boolean enqueue(T item) {

        // tail == n表示队列末尾没有空间了
        if (tail == n) {

            // tail ==n && head==0，表示整个队列都占满了
            if (head == 0) {
                return false;
            }

            // 数据搬移         ->   延迟数据搬移  -> 不用每次出队都移位           ====>  均摊 -> O(1)
            for (int i = head; i < tail; ++i) {
                items[i - head] = items[i];
            }
            // 搬移完之后重新更新head和tail
            tail -= head;
            head = 0;

        }
        items[tail] = item;
        ++tail;
        return true;
    }


    /**
     * 出队
     *
     * @return
     */
    @Override
    public T dequeue() {
        // 如果head == tail 表示队列为空
        if (head == tail) {
            return null;
        }
        // 为了让其他语言的同学看的更加明确，把--操作放到单独一行来写了
        Object ret = items[head];
        ++head;
        return (T) ret;
    }
}


/**
 * 链式队列     ->      用链表实现的队列
 *
 * @param <T>
 */
class LinkedQueue<T> implements IQueue<T> {


    // 基于链表的实现，同样需要两个指针：head 指针和 tail 指针。
    // 它们分别指向链表的 第一个结点 和 最后一个结点。
    // 如图所示，
    // 入队时，tail->next= new_node, tail = tail->next；
    // 出队时，head = head->next。


    @Override
    public boolean enqueue(T e) {
        return false;
    }

    @Override
    public T dequeue() {
        return null;
    }
}