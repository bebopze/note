package com.junzijian.jdk.concurrent.threadpool;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.*;

/**
 * @author liuzhe
 * @date 2019/6/5
 */
public class MyFutureTask {


//    FutureTask(Callable<V> callable) {
//    }

//    FutureTask(Runnable runnable, V result) {
//    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // --------------------------------ExecutorService 执行-------------------------------
        // 创建 FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);

        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();

        // 提交 FutureTask
        Future<?> future = es.submit(futureTask);

        Object o = future.get(); // Runnable 是 无法获取result的
        boolean cancel = future.cancel(true);
        boolean cancelled = future.isCancelled();
        boolean done = future.isDone();

        // 获取计算结果  -get是阻塞方法
        Integer result = futureTask.get();

        System.out.println(JSON.toJSONString(result));


        // --------------------------------Thread 执行-------------------------------
        // 创建 FutureTask
        FutureTask<Integer> futureTask_2 = new FutureTask<>(() -> 1 + 2);
        // 创建并启动线程
        Thread T1 = new Thread(futureTask_2);
        T1.start();
        // 获取计算结果
        Integer result_2 = futureTask_2.get();


    }
}
