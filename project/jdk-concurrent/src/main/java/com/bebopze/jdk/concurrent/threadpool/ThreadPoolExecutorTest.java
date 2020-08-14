package com.bebopze.jdk.concurrent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author bebopze
 * @date 2020/8/14
 */
public class ThreadPoolExecutorTest {


    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("bebop-pool-%d")
            .build();

    private static final ExecutorService bebop_executor = new ThreadPoolExecutor(
            0,
            2,
            30L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            namedThreadFactory,
            // 提交任务的线程自己去执行该任务
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    // ---------------------------------------------------


    // corePoolSize = 0，初始化 创建 0 个核心线程

    // task 达到 Queue最大容量，开始创建最多 maximumPoolSize = 8 个线程

    // 当继续有task到达，且 > Queue的容量，触发拒绝策略：当前配置为 -> main线程自己执行

    // 当线程空闲 keepAliveTime = 30s 后，关闭线程数量至 corePoolSize = 0 个


    public static void main(String[] args) {

        for (int i = 0; i < 200; i++) {

            int finalI = i;
            Runnable r = () -> System.out.println(Thread.currentThread().getName() + " ---------- " + finalI);

            bebop_executor.submit(r);
        }

        System.out.println(Thread.currentThread().getName() + " ============== ");

//        bebop_executor.shutdown();
        bebop_executor.shutdownNow();
    }


}
