package com.junzijian.framework.common.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author bebop
 * @date 2019/6/11
 */
public class CustomThreadPool {

    /**
     * 线程池
     */
    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("custom-pool-%d").build();

    public static final ExecutorService executorService = new ThreadPoolExecutor(
            4,
            20,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            namedThreadFactory,
            // 直接拒绝
            new ThreadPoolExecutor.AbortPolicy()
    );
}