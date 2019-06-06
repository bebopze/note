package com.junzijian.jdk.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * @author liuzhe
 * @date 2019/6/5
 */
public class MyCompletableFuture {


    public static void main(String[] args) {

//        test_a();

//        test_b();

//        test_c();

//        test_Exception();
        test_Exception_try_catch_finally();

//        test_think_question();

//        test_think_question_2();
    }

//    private static void test_think_question_2() {
//
//        // 创建线程池
//        ExecutorService executor = Executors.newFixedThreadPool(3);
//
//        // 异步向电商 S1 询价
//        Future<Integer> f1 =
//                executor.submit(
//                        () -> getPriceByS1());
//        // 异步向电商 S2 询价
//        Future<Integer> f2 =
//                executor.submit(
//                        () -> getPriceByS2());
//        // 异步向电商 S3 询价
//        Future<Integer> f3 =
//                executor.submit(
//                        () -> getPriceByS3());
//
//        // 获取电商 S1 报价并保存
//        r = f1.get();
//        executor.execute(() -> save(r));
//
//        // 获取电商 S2 报价并保存
//        r = f2.get();
//        executor.execute(() -> save(r));
//
//        // 获取电商 S3 报价并保存
//        r = f3.get();
//        executor.execute(() -> save(r));
//    }

//    private static void test_think_question() {
//
//        // 采购订单
//        PurchersOrder po;
//        CompletableFuture<Boolean> cf =
//                CompletableFuture
//                        .supplyAsync(() -> {
//                            // 在数据库中查询规则
//                            return findRuleByJdbc();
//                        })
//                        .thenApply(r -> {
//                            // 规则校验
//                            return check(po, r);
//                        });
//
//        Boolean isOk = cf.join();
//    }

    private static void test_Exception_try_catch_finally() {

        CompletableFuture<Integer> f0 =

                CompletableFuture
                        .supplyAsync(() -> 7 / 1)                   // try
                        .thenApply(r1 -> {
                            System.out.println("try-1");            // try
                            return r1 * 1;
                        })
                        .thenApply(r2 -> {

                                    System.out.println("try-2");    // try
                                    return r2 * 2;
                                }
                        )
                        .exceptionally(e -> {                       // catch

                            System.out.println("catch");
                            return 0;
                        })
                        .handle((f, ex) -> {                        // finally

                            System.out.println("finally");
                            return -1;
                        });

        System.out.println(f0.join());
    }

    private static void test_Exception() {

        CompletableFuture<Integer> f0 =
                CompletableFuture.supplyAsync(() -> (7 / 0))
                        .thenApply(r -> r * 10);

        System.out.println(f0.join());
    }


    private static void test_a() {

        // 任务1：洗水壶 -> 烧开水
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {

            System.out.println("T1: 洗水壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T1: 烧开水...");
            sleep(15, TimeUnit.SECONDS);
        });

        // 任务2：洗茶壶 -> 洗茶杯 -> 拿茶叶
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {

            System.out.println("T2: 洗茶壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T2: 洗茶杯...");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2: 拿茶叶...");
            sleep(1, TimeUnit.SECONDS);

            return " 龙井 ";
        });

        // 任务3：任务1 和 任务2完成后    执行：泡茶
        CompletableFuture<String> f3 =

                f1.thenCombine(f2, (t1_result, t2_result) -> {

                    System.out.println("T1: 拿到茶叶:" + t2_result);

                    System.out.println("T1: 泡茶...");

                    return " 上茶:" + t2_result;
                });

        // 等待任务 3 执行结果
        System.out.println(f3.join());
    }


    /**
     * 通过下面的示例代码，你可以看一下 thenApply() 方法是如何使用的。
     * 首先通过 supplyAsync() 启动一个异步流程，之后是两个串行操作，
     * 整体看起来还是挺简单的。
     * 不过，虽然这是一个异步流程，
     * 但任务①②③却是串行执行的，②依赖①的执行结果，③依赖②的执行结果。
     */
    private static void test_b() {

        CompletableFuture<String> f0 =
                CompletableFuture.supplyAsync(() -> "Hello World")      // ①
                        .thenApply(s -> s + " QQ")                      // ②
                        .thenApply(String::toUpperCase);                // ③

        System.out.println(f0.join());
    }


    /**
     * 下面的示例代码展示了如何使用 applyToEither()方法来描述一个 OR 汇聚关系。
     */
    private static void test_c() {

        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> {
                    int t = getRandom(5, 10);
                    System.out.println("f1 : " + t);
                    sleep(t, TimeUnit.SECONDS);
                    return String.valueOf(t);
                });

        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    int t = getRandom(5, 10);
                    System.out.println("f2 : " + t);
                    sleep(t, TimeUnit.SECONDS);
                    return String.valueOf(t);
                });

        // f1 OR f2 谁先执行完，就返回 谁的结果
        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);

        System.out.println(f3.join());
    }


    /**
     * getRandom
     *
     * @param origin
     * @param bound
     * @return
     */
    private static int getRandom(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }


    /**
     * sleep
     *
     * @param t
     * @param u
     */
    static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {

        }
    }


}
