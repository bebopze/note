package com.bebopze.jdk.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author bebopze
 * @date 2019/6/6
 */
public class MyCompletionService {


    public static void main(String[] args) throws InterruptedException, ExecutionException {


        test_create_CompletionService();

        test_CompletionService_2();

    }

    private static Integer test_CompletionService_2() {

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建 CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        // 用于保存 Future 对象
        List<Future<Integer>> futures = new ArrayList<>(3);
        // 提交异步任务，并保存 future 到 futures
        futures.add(cs.submit(() -> getPriceByS1()));
        futures.add(cs.submit(() -> getPriceByS2()));
        futures.add(cs.submit(() -> getPriceByS3()));

        // 获取最快返回的任务执行结果
        Integer r = 0;
        try {
            // 只要有一个成功返回，则 break
            for (int i = 0; i < 3; ++i) {
                r = cs.take().get();
                // 简单地通过判空来检查是否成功返回
                if (r != null) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 取消所有任务
            for (Future<Integer> f : futures) {
                f.cancel(true);
            }
        }
        // 返回结果
        return r;
    }

    /**
     * 下面的示例代码完整地展示了如何利用 CompletionService 来实现高性能的询价系统。
     *
     * 其中，我们没有指定 completionQueue，因此默认使用无界的 LinkedBlockingQueue。
     * 之后通过CompletionService 接口提供的 submit() 方法提交了三个询价操作，
     * 这三个询价操作将会被CompletionService 异步执行。
     *
     * 最后，我们通过 CompletionService 接口提供的 take() 方法获取一个 Future 对象
     * （前面我们提到过，加入到阻塞队列中的是任务执行结果的 Future 对象），
     * 调用Future 对象的 get() 方法就能返回询价操作的执行结果了。
     */
    private static void test_create_CompletionService() throws InterruptedException, ExecutionException {

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 创建 CompletionService
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);

        // 异步向电商 S1 询价
        completionService.submit(() -> getPriceByS1());
        // 异步向电商 S2 询价
        completionService.submit(() -> getPriceByS2());
        // 异步向电商 S3 询价
        completionService.submit(() -> getPriceByS3());

        // 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            Integer result = completionService.take().get();
            executor.execute(() -> save(result));
        }
    }


    private static void save(Integer result) {
        System.out.println("save---------");
    }

    private static Integer getPriceByS1() {
        return 1;
    }

    private static Integer getPriceByS2() {
        return 2;
    }

    private static Integer getPriceByS3() {
        return 3;
    }
}
