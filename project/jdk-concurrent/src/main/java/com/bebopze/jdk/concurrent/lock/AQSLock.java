package com.bebopze.jdk.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

///**
// * Java并发包 基石 - AQS      -->  https://www.cnblogs.com/waterystone/p/4920797.html
// *                           -->  https://www.cnblogs.com/chengxiao/p/7141160.html
// *
// * ----------------------------------------------
// *
// *      AQS是一个用来构建 锁/同步器  的框架，使用AQS能简单且高效地，构造出应用广泛的大量的同步器。
// *
// *      比如我们提到的 ReentrantLock，Semaphore，
// *      其他的诸如 ReentrantReadWriteLock，SynchronousQueue，FutureTask 等等皆是基于AQS的。
// *
// *      当然，我们自己也能利用AQS非常轻松容易地构造出符合我们自己需求的同步器。
// *
// *
// * ----------------------------------------------
// *
// *      AQS 方便使用者 实现 不同类型的 同步组件
// *
// *      独占式：ReentrantLock
// *      共享式：Semaphore、CountDownLatch
// *      组合式：ReentrantReadWriteLock
// *
// *      总之，AQS为使用提供了底层支撑，如何组装实现，使用者可以自由发挥。
// *
// *
// *
// * ----------------------------------------------
// *
// *      同步器的设计是基于模板方法模式的
// *
// *      一般的使用方式是这样：
// *
// *          1.使用者继承AbstractQueuedSynchronizer并重写指定的方法。（ 这些重写方法很简单，无非是 对于共享资源state 的 获取和释放 ）
// *
// *          2.将AQS组合在自定义同步组件的实现中，并调用其模板方法，而这些模板方法 会调用 使用者重写的方法。
// *
// *
// * ----------------------------------------------
// *
// *      如何使用：
// *
// *          首先，我们需要去继承AbstractQueuedSynchronizer这个类，然后我们 根据我们的需求 去重写相应的方法
// *
// *          比如
// *
// *              要实现 独占锁，那就去重写 tryAcquire、tryRelease
// *
// *              要实现 共享锁，就去重写 tryAcquireShared、tryReleaseShared
// *
// *          最后，在我们的组件中 调用 AQS中的模板方法 就可以了，而这些模板方法是 会调用 到我们之前 重写的 那些方法的。
// *
// *
// *      也就是说，我们只需要很小的工作量就可以实现自己的同步组件，
// *
// *      重写的那些方法，仅仅是 一些简单的 对于共享资源state 的 获取和释 放操作，
// *
// *      至于像是 获取资源失败，线程需要阻塞之类的操作，自然是AQS帮我们完成了。
// *
// *
// * ----------------------------------------------
// *
// *      设计思想：
// *
// *          对于使用者来讲，
// *
// *          我们无需关心 获取资源失败、线程排队、线程阻塞/唤醒等一系列复杂的实现，
// *
// *          这些都在AQS中为我们处理好了。
// *
// *          我们只需要负责好 自己的那个环节 就好，也就是 获取/释放共享资源state 的姿势T_T。
// *
// *
// *          很经典的模板方法设计模式的应用，
// *
// *          AQS为我们定义好顶级逻辑的骨架，并提取出 公用的线程、入队/出队、阻塞/唤醒 等一系列复杂逻辑的实现，
// *
// *          将 部分简单的 可由使用者 决定的操作逻辑，延迟到 子类中 去实现 即可。
// *
// *
// *
// * @author bebopze
// * @date 2020/6/15
// */
public class AQSLock implements Lock {


    /**
     * 静态内部类，继承AQS
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        final ConditionObject newCondition() {
            return new ConditionObject();
        }


        // 这其实是模板方法模式的一个很经典的应用

        // AQS定义的这些可重写的方法


        /**
         * 该线程是否正在独占资源，只有用到condition才需要去实现它。
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }


        // ----------------重写独占模式 获取/释放锁 方法-------------------

        /**
         * 当状态为0的时候获取锁，CAS操作成功，则state状态为1      // 独占式获取同步状态，试着获取，成功返回true，反之为false
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {

            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            return false;
        }

        /**
         * 释放锁，将同步状态置为0     // 独占式释放同步状态， 等待中的其他线程，此时将有机会获取到同步状态
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {

            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }

            setExclusiveOwnerThread(null);
            setState(0);

            return true;
        }


//        // ----------------共享模式方法，则无需重写-------------------
//        //  这里之所以没有定义成abstract，
//        //  是因为独占模式下只用实现tryAcquire-tryRelease，而共享模式下只用实现tryAcquireShared-tryReleaseShared。
//        //  如果都定义成abstract，那么每个模式也要去实现另一模式下的接口。
//        //  说到底，Doug Lea还是站在咱们开发者的角度，尽量减少不必要的工作量。
//
//
//        /**
//         * 共享式获取同步状态，返回值大于等于0，代表获取成功；反之获取失败；
//         *
//         * @param arg
//         * @return
//         */
//        @Override
//        protected int tryAcquireShared(int arg) {
//            return super.tryAcquireShared(arg);
//        }
//
//        /**
//         * 共享式释放同步状态，成功为true，失败为false
//         *
//         * @param arg
//         * @return
//         */
//        @Override
//        protected boolean tryReleaseShared(int arg) {
//            return super.tryReleaseShared(arg);
//        }
    }


    /**
     * 同步对象完成一系列复杂的操作，我们仅需指向它即可
     */
    private final Sync sync = new Sync();


    // ---------------------加锁操作---------------------

    /**
     * 加锁操作  -->  代理到acquire（模板方法）上就行，acquire会调用我们重写的tryAcquire方法
     */
    @Override
    public void lock() {

        // acquire()是独占模式下线程获取共享资源的顶层入口。

        // 如果获取到资源，线程直接返回；
        // 否则进入等待队列，直到获取到资源为止；
        // 且整个过程忽略中断的影响。

        // 这也正是lock()的语义，当然不仅仅只限于lock()。
        // 获取到资源后，线程就可以去执行其临界区代码了。

        sync.acquire(1);
//        sync.acquireShared(1);
    }

    /**
     * 加锁操作
     *
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
//        sync.acquireSharedInterruptibly(1);
    }

    /**
     * 加锁操作
     *
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
//        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }


    // ---------------------释放锁---------------------

    /**
     * 释放锁  -->  代理到release（模板方法）上就行，release会调用我们重写的tryRelease方法
     */
    @Override
    public void unlock() {

        // release()是独占模式下线程释放共享资源的顶层入口。
        // 它会释放指定量的资源，如果彻底释放了（即state=0）,它会唤醒等待队列里的其他线程来获取资源。

        sync.release(1);
//        sync.releaseShared(1);
    }


    // ---------------------是否持有锁---------------------

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }


    // ---------------------条件变量---------------------

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


    public static void main(String[] args) throws InterruptedException {

        test_00();

        test_0();

        test_1();

        test_2();
    }

    private static boolean test_00() {

        return m1() || m2();
    }

    private static boolean m1() {
        System.out.println("----m1-----");
        return true;
    }

    private static boolean m2() {
        System.out.println("----m2-----");
        return true;
    }


    private static void test_0() throws InterruptedException {

        AQSLock aqsLock = new AQSLock();


        aqsLock.lock();

        aqsLock.tryLock();
        aqsLock.tryLock(30, TimeUnit.SECONDS);
        aqsLock.lockInterruptibly();

        aqsLock.unlock();
    }


    private static void test_1() throws InterruptedException {

        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.getHoldCount();


        reentrantLock.lock();

        reentrantLock.tryLock();
        reentrantLock.tryLock(30, TimeUnit.SECONDS);
        reentrantLock.lockInterruptibly();

        reentrantLock.unlock();
    }


    private static void test_2() throws InterruptedException {

        ReentrantReadWriteLock rwReentrantLock = new ReentrantReadWriteLock();


        ReentrantReadWriteLock.ReadLock readLock = rwReentrantLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwReentrantLock.writeLock();


        Condition condition = readLock.newCondition();

        readLock.lock();

        readLock.tryLock();
        readLock.tryLock(30, TimeUnit.SECONDS);
        readLock.lockInterruptibly();

        //
        boolean rTryLock = readLock.tryLock();
        boolean wTryLock = writeLock.tryLock();


//        LockSupport.park();
//        LockSupport.unpark();
        Condition condition1 = readLock.newCondition();
        condition1.await();
        condition1.signal();
        condition1.signalAll();


//        wait();
//        notify();
//        notifyAll();
    }
}
