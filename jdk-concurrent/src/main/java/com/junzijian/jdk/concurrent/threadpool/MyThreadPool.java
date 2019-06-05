//package com.junzijian.jdk.concurrent.threadpool;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//
///**
// * @author liuzhe
// * @date 2019/6/5
// */
//public class MyThreadPool {
//
//    // 利用阻塞队列实现生产者 - 消费者模式
//    BlockingQueue<Runnable> workQueue;
//
//    // 保存内部工作线程
//    List<WorkerThread> threads = new ArrayList<>();
//
//    // 构造方法
//    MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
//        this.workQueue = workQueue;
//        // 创建工作线程
//        for (int idx = 0; idx < poolSize; idx++) {
//            WorkerThread work = new WorkerThread();
//            work.start();
//            threads.add(work);
//        }
//    }
//
//    // 提交任务
//    void execute(Runnable command) throws InterruptedException {
//        workQueue.put(command);
//    }
//
//    // 工作线程负责消费任务，并执行任务
//    class WorkerThread extends Thread {
//
//        @Override
//        public void run() {
//            // 循环取任务并执行
//            while (true) { // ①
//                Runnable task = null;
//                try {
//                    task = workQueue.take();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                task.run();
//            }
//        }
//    }
//}
