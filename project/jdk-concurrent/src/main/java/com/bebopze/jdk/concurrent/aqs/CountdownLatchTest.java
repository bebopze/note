package com.bebopze.jdk.concurrent.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * - CountDownLatch                                      - https://www.cnblogs.com/Lee_xy_z/p/10470181.html
 * -
 *
 * @author bebopze
 * @date 2020/12/15
 */
public class CountdownLatchTest {


    public static void main(String[] args) {

        // 主线程等待子线程执行完成再执行
        test_1();


        test_2();
    }


    // ------------------------------------------------------------------------


    /**
     * 主线程等待子线程执行完成再执行
     */
    private static void test_1() {

        ExecutorService service = Executors.newFixedThreadPool(3);

        final CountDownLatch latch = new CountDownLatch(3);


        for (int i = 0; i < 3; i++) {

            Runnable runnable = new Runnable() {

                @Override
                public void run() {

                    try {

                        System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("子线程" + Thread.currentThread().getName() + "执行完成");

                        // 当前线程调用此方法，则计数减一
                        latch.countDown();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            service.execute(runnable);
        }

        try {
            System.out.println("主线程" + Thread.currentThread().getName() + "等待子线程执行完成...");

            //阻塞当前线程，直到计数器的值为0
            latch.await();

            System.out.println("主线程" + Thread.currentThread().getName() + "开始执行...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println();
    }

    /**
     * 百米赛跑，4名运动员选手到达场地等待裁判口令，裁判一声口令，选手听到后同时起跑，当所有选手到达终点，裁判进行汇总排名¬
     */
    private static void test_2() {

        ExecutorService service = Executors.newCachedThreadPool();

        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);


        for (int i = 0; i < 4; i++) {

            Runnable runnable = new Runnable() {

                @Override
                public void run() {

                    try {
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        cdOrder.await();

                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                        cdAnswer.countDown();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            service.execute(runnable);
        }


        try {

            Thread.sleep((long) (Math.random() * 10000));

            System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
            cdOrder.countDown();

            System.out.println("裁判" + Thread.currentThread().getName() + "已发送口令，正在等待所有选手到达终点");
            cdAnswer.await();

            System.out.println("所有选手都到达终点");
            System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        service.shutdown();
    }

}
