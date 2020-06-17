package com.junzijian.jdk.concurrent.threadpool;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.*;

/**
 * @author bebopze
 * @date 2019/6/5
 */
public class MyFutureTask {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


//        test_FutureTask__ExecutorService();

//        test_FutureTask__Thread();

        test_FutureTask();

    }


    /**
     * 下面的示例代码就是用这一章提到的 Future 特性来实现的。
     * 首先，我们创建了两个 FutureTask
     * ——ft1 和 ft2，ft1 完成洗水壶、烧开水、泡茶的任务，ft2 完成洗茶壶、洗茶杯、拿茶叶的任务；
     * 这里需要注意的是 ft1 这个任务在执行泡茶任务前，需要等待 ft2 把茶叶拿来，
     * 所以 ft1 内部需要引用 ft2，
     * 并在执行泡茶之前，调用 ft2 的 get() 方法实现等待。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test_FutureTask() throws ExecutionException, InterruptedException {

        // 创建任务 T2 的 FutureTask
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        // 创建任务 T1 的 FutureTask
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        // 线程 T1 执行任务 ft1
        Thread T1 = new Thread(ft1);
        T1.start();

        // 线程 T2 执行任务 ft2
        Thread T2 = new Thread(ft2);
        T2.start();

        // 等待线程 T1 执行结果
        System.out.println(ft1.get());


        // 一次执行结果：
        //        T1:
        //        洗水壶...
        //        T2:
        //        洗茶壶...
        //        T1:
        //        烧开水...
        //        T2:
        //        洗茶杯...
        //        T2:
        //        拿茶叶...
        //        T1:
        //        拿到茶叶:
        //        龙井
        //        T1:
        //        泡茶...
        //        上茶:
        //        龙井

    }


    private static void test_FutureTask__ExecutorService() throws ExecutionException, InterruptedException {

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

    }

    private static void test_FutureTask__Thread() throws ExecutionException, InterruptedException {

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


/**
 * T1Task 需要执行的任务：洗水壶、烧开水、泡茶
 */
class T1Task implements Callable<String> {

    FutureTask<String> ft2;

    // T1 任务需要 T2 任务的 FutureTask
    T1Task(FutureTask<String> ft2) {
        this.ft2 = ft2;
    }

    @Override
    public String call() throws Exception {

        System.out.println("T1: 洗水壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1: 烧开水...");
        TimeUnit.SECONDS.sleep(15);

        // 获取 T2 线程的茶叶
        String tf = ft2.get();
        System.out.println("T1: 拿到茶叶:" + tf);

        System.out.println("T1: 泡茶...");

        return " 上茶:" + tf;
    }
}

/**
 * T2Task 需要执行的任务: 洗茶壶、洗茶杯、拿茶叶
 */
class T2Task implements Callable<String> {

    @Override
    public String call() throws Exception {

        System.out.println("T2: 洗茶壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2: 洗茶杯...");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2: 拿茶叶...");
        TimeUnit.SECONDS.sleep(1);

        return " 龙井 ";
    }
}
