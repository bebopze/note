package com.bebopze.jdk.happenbefore;

import lombok.Builder;
import lombok.ToString;

/**
 * Java内存模型                 Happen-Before 规则     ====>   可见性❗️❗️❗️
 *
 * @author bebopze
 * @date 2020/9/9
 */
public class HappenBefore {


    // 本质：
    //      Happens-Before 规则 关注的是 可见性❗️❗️❗️


    // -----------------------------------------------------------------------------------------------------------------


    public static void main(String[] args) throws InterruptedException {


        // 顺序（同一线程）     结果一定  ==  顺序方式 推演的结果
        test__order();


        // 锁                 解锁  -- HB -->  后续 加锁
        test__lock();


        // 可见               写  -- HB -->  后续 读
        test__volatile();

        // 传递               A->B，B->C       A-->C
        test__transfer();


        test__start();

        test__join();
    }


    private static void test__order() {

        int a = 0;
        System.out.println(a);

        a = 1;
        System.out.println(a);


        System.out.println("-----------------------------------------------");
    }


    private static void test__lock() {


        // 解锁  -- HB -->  后续 加锁


    }


    private static void test__volatile() throws InterruptedException {

        Person p = Person.builder().height(0).build();


        // 子线程
        Thread thread_1 = new Thread("Thread-1") {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-----------" + p);

                p.height = 178;

                System.out.println(Thread.currentThread().getName() + "-----------" + p);
            }
        };


        // 子线程
        Thread thread_2 = new Thread("Thread-2") {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-----------" + p);

                p.height = 180;

                System.out.println(Thread.currentThread().getName() + "-----------" + p);
            }
        };


        thread_1.start();
        thread_1.join();


        thread_2.start();
        thread_2.join();


        System.out.println("-----------------------------------------------");
    }


    private static void test__transfer() {


    }


    private static void test__start() throws InterruptedException {

        Person p = Person.builder().age(0).build();


        // 子线程
        Thread thread_B = new Thread("Thread-B-1") {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-----------" + p);
            }
        };


        // 主线程
        Thread.currentThread().setName("Thread-A-1");

        p.age = 16;

        // 主 ---start---> 子       异步
        thread_B.start();


        System.out.println("-----------------------------------------------");
    }


    private static void test__join() throws InterruptedException {

        Person p = Person.builder().age(0).build();


        // 子线程
        Thread thread_B = new Thread("Thread-B-2") {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-----------" + p);

                p.age = 20;
            }
        };


        // 主线程
        Thread.currentThread().setName("Thread-A-2");

        p.age = 18;

        // 主 ---start---> 子         异步
        thread_B.start();


        // 主 ---join---> 子          同步
        thread_B.join();

        System.out.println(Thread.currentThread().getName() + "-----------" + p);
    }
}


@ToString
@Builder
class Person {

    int age;

    volatile int height;
}