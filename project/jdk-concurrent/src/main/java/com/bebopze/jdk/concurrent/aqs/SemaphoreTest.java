package com.bebopze.jdk.concurrent.aqs;


import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * - Semaphore                                      - https://blog.csdn.net/qq_33803102/article/details/106428002
 * -
 *
 * @author bebopze
 * @date 2020/12/15
 */
public class SemaphoreTest {


    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * 1个信号  ->  互斥锁
     */
    private static final Semaphore semaphore_mutex_lock = new Semaphore(1);
//    private static final Semaphore semaphore = new Semaphore(1);

    /**
     * 多个信号  ->  同步器
     */
    private static final Semaphore semaphore = new Semaphore(2);


    public static void main(String[] args) {

        test_1();

//        test_2();
    }


    private static void test_1() {


        Runnable r1 = () -> {

            try {

                semaphore.acquire();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "----拿到了吃饭许可-----公瑾领盒饭");

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                semaphore.release();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "====释放了吃饭许可=====公瑾OVER");

            }
        };


        Runnable r2 = () -> {

            try {

                semaphore.acquire();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "----拿到了吃饭许可-----鲁肃领盒饭");

                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                semaphore.release();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "====释放了吃饭许可=====鲁肃OVER");

            }
        };

        Runnable r3 = () -> {

            try {

                semaphore.acquire();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "----拿到了吃饭许可-----吕蒙领盒饭");

                Thread.sleep(15000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                semaphore.release();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "====释放了吃饭许可=====吕蒙OVER");

            }
        };


        Runnable r4 = () -> {

            try {

                semaphore.acquire();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "----拿到了吃饭许可-----陆逊领盒饭");

                Thread.sleep(20000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                semaphore.release();
                System.out.println(LocalDateTime.now() + "  " + Thread.currentThread().getName() + "====释放了吃饭许可=====陆逊OVER");

            }
        };


        // 使用阻塞操作   ->   必须用独立线程池   ===>   否则阻塞  ->  导致线程池不可用！！！
        //
        //      任务数量 > 线程数量  ==>  1个任务  ->  占用1个线程  ->  立马被阻塞  ===>  线程池无可用线程

        executor.execute(r1);
        executor.execute(r2);
        executor.execute(r3);
        executor.execute(r4);
    }


    private static void test_2() {

        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(3);

        for (int i = 0; i < 10; i++) {

            Runnable runnable = () -> {
                try {
                    sp.acquire();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName()
                        + "进入，当前已有" + (3 - sp.availablePermits()) + "个并发");


                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName()
                        + "即将离开");
                sp.release();


                // 下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
                System.out.println("线程" + Thread.currentThread().getName()
                        + "已离开，当前已有" + (3 - sp.availablePermits()) + "个并发");
            };

            service.execute(runnable);
        }

    }
}
