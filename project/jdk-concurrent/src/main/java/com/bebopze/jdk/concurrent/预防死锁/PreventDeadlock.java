package com.bebopze.jdk.concurrent.预防死锁;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;

/**
 * 预防死锁
 *
 * @author bebopze
 * @date 2020/5/11
 */
public class PreventDeadlock {

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {

        test();


        test_Semaphore();
        test_Semaphore_();

        test_StampedLock();

        test_CountDownLatch();

        test_CyclicBarrier();


        test_Collections_Synchronized();

        test_Collections_Concurrent();

        test_Atomic();


        test_ThreadPool();

        test_CompletableFuture();

        test_CompletionService();


        System.out.println();
    }

    /**
     * 破坏：占有且等待     --> 可以 一次性申请所有资源
     * <p>
     * 等待-通知：synchronized + 等待:wait() + 通知/唤醒:notify()、notifyAll()
     */
    static void test() throws InterruptedException {

        // 标准范式         范式，意味着是经典做法，所以没有特殊理由不要尝试换个写法
//        while(条件不满足) {
//            wait();
//        }


//        Thread thread = new Thread();
//        thread.sleep(1);
//        thread.wait(1);

        // synchronized 只支持一个条件变量    对管程模型进行了 精简  使用简单


        ReentrantLock lock = new ReentrantLock();

        // JDK Lock 支持多个条件变量
        Condition condition_1 = lock.newCondition();
        Condition condition_2 = lock.newCondition();

        condition_1.await();

        condition_1.signal();
        condition_1.signalAll();


        // 局部变量 必须为final 才能传递到子线程内
        final Integer var = 0;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = var + 1;

            }
        });
        thread.start();


        String s = new String("123");


        ReentrantReadWriteLock rwReentrantLock = new ReentrantReadWriteLock();


        ReentrantReadWriteLock.ReadLock readLock = rwReentrantLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwReentrantLock.writeLock();


        Condition r_condition = readLock.newCondition();
        Condition w_condition = writeLock.newCondition();

        // 共享Lock  -->   acquireShared
        readLock.lock();
        readLock.lockInterruptibly();

        boolean rTryLock = readLock.tryLock();
        boolean wTryLock = writeLock.tryLock();


        r_condition.await();
        r_condition.signalAll();

        w_condition.await();
        w_condition.signalAll();

    }


    static void test_Semaphore() throws InterruptedException {

        Semaphore_Pool semaphore_pool = new Semaphore_Pool();


        Object item = semaphore_pool.getItem();

        semaphore_pool.putItem(1);
    }


    /**
     * Semaphore   ->   N 个进入临界区                  Lock   ->   1 个进入临界区
     * Semaphore   ->   唤醒 1 个，不 check             Lock   ->   唤醒 N 个，竞争，且 check 条件是否仍然满足
     * -                 因无 Condition                              有 Condition
     */
    static class Semaphore_Pool {

        private static final int MAX_AVAILABLE = 100;

        // init
        private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

        public Object getItem() throws InterruptedException {
            // down
            available.acquire();
            return getNextAvailableItem();
        }

        public void putItem(Object x) {
            if (markAsUnused(x)) {
                // up
                available.release();
            }
        }

        // Not a particularly efficient data structure; just for demo

        protected Object[] items = null; //... whatever kinds of items being managed
        protected boolean[] used = new boolean[MAX_AVAILABLE];

        protected synchronized Object getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null; // not reached
        }

        protected synchronized boolean markAsUnused(Object item) {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (item == items[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }


    static void test_Semaphore_() throws InterruptedException {

        // Semaphore -> N        Lock -> 1
        Semaphore semaphore = new Semaphore(5, false);

        semaphore.tryAcquire(1);
        semaphore.release();

        ObjPool<Integer, Integer> objPool = new ObjPool<>(10, 12345);


        ExecutorService executorService = Executors.newFixedThreadPool(100);

        List<Callable<String>> callableList = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {

            Callable callable = new Callable() {
                @Override
                public Integer call() throws Exception {


                    objPool.exec(t -> {

                        System.out.println(Thread.currentThread().getName());

                        return t;
                    });

                    return null;
                }
            };

            callableList.add(callable);
        }


        List<Future<String>> futures = executorService.invokeAll(callableList);

        executorService.shutdownNow();


        System.out.println("------------");

    }


    static void test_StampedLock() {

        //
        StampedLock stampedLock = new StampedLock();


    }

    static void test_CountDownLatch() throws InterruptedException {

        //
        CountDownLatch countDownLatch = new CountDownLatch(10);

        countDownLatch.countDown();

        countDownLatch.getCount();

        countDownLatch.await();


//        // 创建2个线程的线程池
//        Executor executor = Executors.newFixedThreadPool(2);
//
//        while (存在未对账订单) {
//
//            // 计数器初始化为2
//            CountDownLatch latch = new CountDownLatch(2);
//
//            // 查询未对账订单
//            executor.execute(() -> {
//                pos = getPOrders();
//                latch.countDown();
//            });
//            // 查询派送单
//            executor.execute(() -> {
//                dos = getDOrders();
//                latch.countDown();
//            });
//
//            // 等待两个查询操作结束
//            latch.await();
//
//            // 执行对账操作
//            diff = check(pos, dos);
//
//            // 差异写入差异库
//            save(diff);
//        }

    }


    /**
     * Cyclic Barrier       循环栅栏/屏障   -->  可循环利用的屏障
     * [ˈsaɪklɪk]  [ˈbæriər]
     */
    static void test_CyclicBarrier() {


//        // 订单队列
//        Vector<P> pos;
//        // 派送单队列
//        Vector<D> dos;
//
//        // 执行回调的线程池
//        Executor executor = Executors.newFixedThreadPool(1);
//
//        final CyclicBarrier barrier = new CyclicBarrier(2, () -> {

        // CyclicBarrier的回调函数 执行在 一个回合里 最后执行await() 的线程上

        // 回调函数执行完之后才会唤醒等待的线程     这里用了线程池执行回调函数  同步转异步 提升效率
//            executor.execute(() -> check());
//        });
//
//        void check() {
//            P p = pos.remove(0);
//            D d = dos.remove(0);
//            // 执行对账操作
//            diff = check(p, d);
//            // 差异写入差异库
//            save(diff);
//        }
//
//        void checkAll() {
//
//            // 循环查询订单库
//            Thread T1 = new Thread(() -> {
//                while (存在未对账订单) {
//                    // 查询订单库
//                    pos.add(getPOrders());
//                    // 等待
//                    barrier.await();   // await
//                }
//            });
//            T1.start();
//
//            // 循环查询运单库
//            Thread T2 = new Thread(() -> {
//                while (存在未对账订单) {
//                    // 查询运单库
//                    dos.add(getDOrders());
//                    // 等待
//                    barrier.await();    // await
//                }
//            });
//            T2.start();
//        }

    }


    static void test_Collections_Synchronized() {

        // 同步容器

        List list = Collections.synchronizedList(new ArrayList());
        synchronized (list) {
            Iterator i = list.iterator();
            while (i.hasNext()) {
                System.out.println(i.next());
            }
        }

    }

    private static void test_Collections_Concurrent() {

        // 并发容器

        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add(new Object());
        copyOnWriteArrayList.set(0, new Object());
        copyOnWriteArrayList.get(0);


        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("k", "v");
        concurrentHashMap.get("k");

        ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();
        concurrentSkipListMap.put("k", "v");
        concurrentSkipListMap.get("k");


        // queue
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1000);
        // put
        // offer  在添加元素时，如果发现队列已满无法添加的话，会直接返回false
        linkedBlockingQueue.offer(new Object());
        // 等同offer     add方法在添加元素的时候，若超出了度列的长度会直接抛出异常
        linkedBlockingQueue.add(new Object());
        try {
            // put  若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素
            linkedBlockingQueue.put(new Object());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // take
        // poll：将首个元素从队列中弹出，如果队列是空的，就返回null
        Object poll = linkedBlockingQueue.poll();
        // peek：查看首个元素，不会移除首个元素，如果队列是空的就返回null
        Object peek = linkedBlockingQueue.peek();
        // element：查看首个元素，不会移除首个元素，如果队列是空的就抛出异常NoSuchElementException
        Object element = linkedBlockingQueue.element();


    }

    private static void test_Atomic() {

        AtomicLong atomicLong = new AtomicLong(1000);

        atomicLong.compareAndSet(1000, 2000);

    }


    private static void test_ThreadPool() throws ExecutionException, InterruptedException, TimeoutException {


        ExecutorService executorService = Executors.newFixedThreadPool(20);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable-task");
            }
        };

        Callable<String> callable = new Callable() {
            @Override
            public String call() throws Exception {
                return "callable-task";
            }
        };


        // execute
        executorService.execute(runnable);


        // result
        Future<?> runnable_future_task = executorService.submit(runnable);
        // 阻塞
        runnable_future_task.get();

        Future<String> runnable_future_task_2 = executorService.submit(runnable, new String());
        String runnable_future_task__result = runnable_future_task_2.get(10, TimeUnit.SECONDS);


        Future<String> callable_future_task = executorService.submit(callable);
        String callable_future_task__result = callable_future_task.get(30, TimeUnit.SECONDS);


        // FutureTask
        FutureTask<String> runnable_futureTask = new FutureTask(runnable, new String());
        FutureTask<String> callable_futureTask = new FutureTask(callable);

        new Thread(runnable_futureTask).start();
        new Thread(callable_futureTask).start();

        String r_1 = runnable_futureTask.get();
        String r_2 = callable_futureTask.get();


    }

    private static void test_CompletableFuture() throws ExecutionException, InterruptedException {

        // 强烈建议 你要根据不同的业务类型创建不同的线程池，以避免互相干扰
        final ExecutorService executorService = Executors.newFixedThreadPool(10);


        // 任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("T1:洗水壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T1:烧开水...");
                    sleep(15, TimeUnit.SECONDS);
                }, executorService);

        // 任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("T2:洗茶壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T2:洗茶杯...");
                    sleep(2, TimeUnit.SECONDS);

                    System.out.println("T2:拿茶叶...");
                    sleep(1, TimeUnit.SECONDS);
                    return "龙井";
                }, executorService);

        // 任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 =
                f1.thenCombine(f2, (__, tf) -> {
                    System.out.println("T1:拿到茶叶:" + tf);
                    System.out.println("T1:泡茶...");
                    return "上茶:" + tf;
                });

        // 等待任务3执行结果
        System.out.println(f3.join());


        // 一次执行结果：
        //        T1:洗水壶...
        //        T2:洗茶壶...
        //        T1:烧开水...
        //        T2:洗茶杯...
        //        T2:拿茶叶...
        //        T1:拿到茶叶:龙井
        //        T1:泡茶...
        //        上茶:龙井

    }

    static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }


    private static void test_CompletionService() throws InterruptedException, ExecutionException {

        // 强烈建议 你要根据不同的业务类型创建不同的线程池，以避免互相干扰
        final Executor executor = Executors.newFixedThreadPool(10);

        LinkedBlockingQueue<Future<Integer>> linkedBlockingQueue = new LinkedBlockingQueue<>(1024);


        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor, linkedBlockingQueue);

        // 异步向电商S1询价
        cs.submit(() -> {
            System.out.println("网络请求-1");
            return 1;
        });
        // 异步向电商S2询价
        cs.submit(() -> {
            System.out.println("网络请求-2");
            return 2;
        });
        // 异步向电商S3询价
        cs.submit(() -> {
            System.out.println("网络请求-3");
            return 3;
        });


        // 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            Integer result = cs.take().get();
            executor.execute(() -> System.out.println("异步获取的价格写入DB  -->  save(" + result + ")"));
        }
    }


}


/**
 * 等待 - 通知 机制 是一种非常普遍的 线程间协作 的方式
 * <p>
 * 工作中 轮询 等待某个状态 ---> 可以用 等待 - 通知 机制来优化.
 * <p>
 * Java 语言内置的 synchronized 配合 wait()、notify()、notifyAll() 这三个方法可以快速实现这种机制.
 */
class Allocator {

    private List<Object> als;


    // 一次性申请所有资源
    synchronized void apply(Object from, Object to) {

        // 经典写法
        while (!als.contains(from) || !als.contains(to)) {

            try {
                // 等待
                wait();
            } catch (Exception e) {
            }
        }

        als.add(from);
        als.add(to);
    }

    // 归还资源
    synchronized void free(Object from, Object to) {

        als.remove(from);
        als.remove(to);


        /**
         * 通知
         *
         * 尽量使用 notifyAll()
         *
         * notify() 是会 随机地通知 等待队列中的 一个 线程，而 notifyAll() 会通知等待队列中的所有线程.
         *
         * 所谓的感觉往往都蕴藏着风险：实际上使用 notify() 也很有风险，它的风险在于 可能导致某些线程 永远不会 被通知到。
         */
        notifyAll();
    }
}


class ObjPool<T, R> {
    final List<T> pool;
    // 用信号量实现限流器
    final Semaphore sem;

    // 构造函数
    ObjPool(int size, T t) {
        pool = new Vector<T>() {
        };
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    // 利用对象池的对象，调用 func
    R exec(Function<T, R> func) throws InterruptedException {
        T t = null;
        sem.acquire();
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            sem.release();
        }
    }
}
// 创建对象池