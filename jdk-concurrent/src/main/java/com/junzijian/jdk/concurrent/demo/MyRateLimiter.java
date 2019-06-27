package com.junzijian.jdk.concurrent.demo;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author liuzhe
 * @date 2019/6/26
 */
public class MyRateLimiter {

    public static void main(String[] args) {

        // test RateLimiter
        test_1();

        // my RateLimiter : 1 r/s
        test_2();

        // my RateLimiter : n r/s
        test_3();
    }

    private static void test_3() {

        class SimpleLimiter {
            // 当前令牌桶中的令牌数量
            long storedPermits = 0;
            // 令牌桶的容量
            long maxPermits = 3;
            // 下一令牌产生时间
            long next = System.nanoTime();
            // 发放令牌间隔：纳秒
            long interval = 1000_000_000;

            // 请求时间在下一令牌产生时间之后, 则
            // 1. 重新计算令牌桶中的令牌数
            // 2. 将下一个令牌发放时间重置为当前时间
            void resync(long now) {
                if (now > next) {
                    // 新产生的令牌数
                    long newPermits = (now - next) / interval;
                    // 新令牌增加到令牌桶
                    storedPermits = Math.min(maxPermits,
                            storedPermits + newPermits);
                    // 将下一个令牌发放时间重置为当前时间
                    next = now;
                }
            }

            // 预占令牌，返回能够获取令牌的时间
            synchronized long reserve(long now) {
                resync(now);
                // 能够获取令牌的时间
                long at = next;
                // 令牌桶中能提供的令牌
                long fb = Math.min(1, storedPermits);
                // 令牌净需求：首先减掉令牌桶中的令牌
                long nr = 1 - fb;
                // 重新计算下一令牌产生时间
                next = next + nr * interval;
                // 重新计算令牌桶中的令牌
                this.storedPermits -= fb;
                return at;
            }

            // 申请令牌
            void acquire() {
                // 申请令牌时的时间
                long now = System.nanoTime();
                // 预占令牌
                long at = reserve(now);
                long waitTime = Math.max(at - now, 0);
                // 按照条件等待
                if (waitTime > 0) {
                    try {
                        TimeUnit.NANOSECONDS
                                .sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static void test_2() {

        class SimpleLimiter {
            // 下一令牌产生时间
            long next = System.nanoTime();
            // 发放令牌间隔：纳秒
            long interval = 1000_000_000;

            // 预占令牌，返回能够获取令牌的时间
            synchronized long reserve(long now) {
                // 请求时间在下一令牌产生时间之后
                // 重新计算下一令牌产生时间
                if (now > next) {
                    // 将下一令牌产生时间重置为当前时间
                    next = now;
                }
                // 能够获取令牌的时间
                long at = next;
                // 设置下一令牌产生时间
                next += interval;
                // 返回线程需要等待的时间
                return Math.max(at, 0L);
            }

            // 申请令牌
            void acquire() {
                // 申请令牌时的时间
                long now = System.nanoTime();
                // 预占令牌
                long at = reserve(now);
                long waitTime = Math.max(at - now, 0);
                // 按照条件等待
                if (waitTime > 0) {
                    try {
                        TimeUnit.NANOSECONDS
                                .sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static void test_1() {

        // 限流器流速：2 个请求 / 秒
        RateLimiter limiter = RateLimiter.create(2.0);
        // 执行任务的线程池
        ExecutorService es = Executors.newFixedThreadPool(1);
        // 记录上一次执行时间
        final Long[] prev = {System.nanoTime()};
        // 测试执行 20 次
        for (int i = 0; i < 20; i++) {
            // 限流器限流
            limiter.acquire();
            // 提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                // 打印时间间隔：毫秒
                System.out.println((cur - prev[0]) / 1000_000);
                prev[0] = cur;
            });
        }

    }
}
