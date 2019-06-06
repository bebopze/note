package com.junzijian.jdk.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liuzhe
 * @date 2019/6/5
 */
public class ThreadPool {


    public static void main(String[] args) throws InterruptedException {

        /**
         * 下面是使用示例
         **/
        // 创建有界阻塞队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                System.out.println("--------");
            }
        };

        // 均为向队尾add runnable
//        workQueue.offer(runnable);  // boolean   没容量 直接返回false
//        workQueue.add(runnable);    // boolean   没容量 会直接抛出异常：
//        workQueue.put(runnable);    // void      没容量 会一直阻塞 等待空间 以加入元素

        // 创建线程池
        MyThreadPool pool = new MyThreadPool(10, workQueue);

        // 提交任务
        pool.execute(() ->
                {
                    System.out.println("hello");
                }
        );

    }


}

// 简化的线程池，仅用来说明工作原理
class MyThreadPool {

    // 利用阻塞队列实现生产者 - 消费者模式
    BlockingQueue<Runnable> workQueue;

    // 保存内部工作线程
    List<WorkerThread> threads = new ArrayList<>();

    // 构造方法
    MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        // 创建工作线程
        for (int idx = 0; idx < poolSize; idx++) {
            WorkerThread work = new WorkerThread();
            work.start();
            threads.add(work);
        }
    }

    // 提交任务
    void execute(Runnable command) throws InterruptedException {
        workQueue.put(command);
    }

    // 工作线程负责消费任务，并执行任务
    class WorkerThread extends Thread {

        @Override
        public void run() {
            // 循环取任务并执行
            while (true) { // ①
                Runnable task = null;
                try {
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
            }
        }
    }
}