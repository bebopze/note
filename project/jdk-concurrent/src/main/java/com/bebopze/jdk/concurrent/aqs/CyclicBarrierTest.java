package com.bebopze.jdk.concurrent.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * - CyclicBarrier                                      - https://blog.csdn.net/qq_39241239/article/details/87030142
 * -
 *
 * @author bebopze
 * @date 2020/12/15
 */
public class CyclicBarrierTest {


    /**
     * - 使用阻塞操作   ->   必须用独立线程池   ===>   否则阻塞  ->  导致线程池不可用！！！
     * -
     * -    任务数量 > 线程数量  ==>  1个任务  ->  占用1个线程  ->  立马被阻塞  ===>  线程池无可用线程
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);


    public static void main(String[] args) {

        test_1();

        test_2();
    }


    private static void test_1() {

        /**
         *
         *
         * @param parties            相互等待的 总线程数
         *
         * @param barrierAction      回调函数       // 开启下一局之前  ->  最后一个到达栅栏的线程执行  ->  回调函数
         *
         *                                         // 先执行回调函数   ->  再执行唤醒
         *
         */
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {

            // 用线程池执行回调函数  ->  同步转异步  提升效率
            executor.execute(() -> System.out.println("======人到齐了，开饭吧======"));
        });


        // ---------------------------------------------------------------


        Runnable r1 = () -> {
            System.out.println("----公瑾到了");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable r2 = () -> {
            System.out.println("----子敬到了");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };


        Runnable r3 = () -> {
            System.out.println("----吕蒙到了");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable r4 = () -> {
            System.out.println("----陆逊到了");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
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


        CyclicBarrier barrier = new CyclicBarrier(7, new HorseRace());


        for (int i = 0; i < 7; i++) {
            Horse horse = new Horse(barrier);
            HorseRace.horses.add(horse);
            HorseRace.exec.execute(horse);
        }
    }
}


class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;

    public Horse(CyclicBarrier b) {
        barrier = b;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    //赛马每次随机跑几步
                    strides += rand.nextInt(3);
                }
                barrier.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

}


/**
 * 赛马
 */
class HorseRace implements Runnable {

    private static final int FINISH_LINE = 75;
    static List<Horse> horses = new ArrayList();
    static ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public void run() {
        StringBuilder s = new StringBuilder();
        //打印赛道边界
        for (int i = 0; i < FINISH_LINE; i++) {
            s.append("=");
        }
        System.out.println(s);
        //打印赛马轨迹
        for (Horse horse : horses) {
            System.out.println(horse.tracks());
        }
        //判断是否结束
        for (Horse horse : horses) {
            if (horse.getStrides() >= FINISH_LINE) {
                System.out.println(horse + "won!");
                exec.shutdownNow();
                return;
            }
        }
        //休息指定时间再到下一轮
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("barrier-action sleep interrupted");
        }
    }

}