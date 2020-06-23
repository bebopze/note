package com.bebopze.jdk.concurrent.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author bebopze
 * @date 2019/6/12
 */
public class MyForkJoinTask {


    public static void main(String[] args) {

        test_1();


        test_MapReduce();
    }

    private static void test_MapReduce() {


        String[] fc = {
                "hello world",
                "hello me",
                "hello fork",
                "hello join",
                "fork join in world"
        };


        // 创建 ForkJoin 线程池
        ForkJoinPool fjp = new ForkJoinPool(3);

        // 创建任务
        MR mr = new MR(fc, 0, fc.length);

        // 启动任务
        Map<String, Long> result = fjp.invoke(mr);

        // 输出结果
        result.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    // MR 模拟类
    static class MR extends RecursiveTask<Map<String, Long>> {

        private String[] fc;
        private int start, end;

        // 构造函数
        MR(String[] fc, int fr, int to) {
            this.fc = fc;
            this.start = fr;
            this.end = to;
        }

        @Override
        protected Map<String, Long> compute() {
            if (end - start == 1) {
                return calc(fc[start]);
            } else {
                int mid = (start + end) / 2;
                MR mr1 = new MR(fc, start, mid);

                // 前半部分数据 fork一个递归任务去处理（  关键代码 mr1.fork()  ）
                mr1.fork();

                MR mr2 = new MR(fc, mid, end);
                // 计算子任务，并返回合并的结果
                return merge(mr2.compute(), mr1.join());  // 后半部分数据 则在 当前任务中递归处理（ mr2.compute() ）
            }
        }

        /**
         * 合并结果
         *
         * @param r1
         * @param r2
         * @return
         */
        private Map<String, Long> merge(Map<String, Long> r1, Map<String, Long> r2) {

            Map<String, Long> result = new HashMap<>();
            result.putAll(r1);

            // 合并结果
            r2.forEach((k, v) -> {
                Long c = result.get(k);
                if (c != null) {
                    result.put(k, c + v);
                } else {
                    result.put(k, v);
                }
            });

            return result;
        }

        /**
         * 统计单词数量
         *
         * @param line
         * @return
         */
        private Map<String, Long> calc(String line) {

            Map<String, Long> result = new HashMap<>();

            // 分割单词
            String[] words = line.split("\\s+");

            // 统计单词数量
            for (String w : words) {
                Long v = result.get(w);
                if (v != null) {
                    result.put(w, v + 1);
                } else {
                    result.put(w, 1L);
                }
            }

            return result;
        }
    }


    private static void test_1() {

        // 创建分治任务线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        // 创建分治任务
        Fibonacci fib = new Fibonacci(30);
        // 启动分治任务
        Integer result = fjp.invoke(fib);
        // 输出结果
        System.out.println(result);

    }


    // 递归任务
    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务   区别之处在于 计算 Fibonacci(n - 1) 使用了 异步子任务，这是通过 f1.fork() 这条语句实现的。
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }

}
