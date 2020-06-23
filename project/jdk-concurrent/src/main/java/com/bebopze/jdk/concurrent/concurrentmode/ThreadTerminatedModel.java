package com.bebopze.jdk.concurrent.concurrentmode;

/**
 * 线程终止模式：优雅的终止线程
 * <p>
 * 3.8 两阶段终止模式：如何优雅地终止线程？
 *
 * @author bebopze
 * @date 2019/6/26
 */
public class ThreadTerminatedModel {

    public static void main(String[] args) {

        // Thread类 自带isInterrupted()标志位
        test_1();

        // 设置自己的线程终止标志位：  （强烈建议！！！）
        test_2();
    }

    /**
     * Thread类 自带isInterrupted()标志位
     */
    private static void test_1() {

        class Proxy {

            boolean started = false;
            // 采集线程
            Thread rptThread;

            // 启动采集功能
            synchronized void start() {
                // 不允许同时启动多个采集线程
                if (started) {
                    return;
                }
                started = true;
                rptThread = new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        // 省略采集、回传实现
//                        report();
                        // 每隔两秒钟采集、回传一次数据
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // 重新设置线程中断状态
                            Thread.currentThread().interrupt();
                        }
                    }
                    // 执行到此处说明线程马上终止
                    started = false;
                });
                rptThread.start();
            }

            // 终止采集功能
            synchronized void stop() {
                rptThread.interrupt();
            }
        }

    }


    /**
     * 设置自己的线程终止标志位：  （强烈建议！！！）
     */
    private static void test_2() {


        class Proxy {
            // 线程终止标志位
            volatile boolean terminated = false;
            boolean started = false;
            // 采集线程
            Thread rptThread;

            // 启动采集功能
            synchronized void start() {
                // 不允许同时启动多个采集线程
                if (started) {
                    return;
                }
                started = true;
                terminated = false;
                rptThread = new Thread(() -> {
                    while (!terminated) {
                        // 省略采集、回传实现
//                        report();
                        // 每隔两秒钟采集、回传一次数据
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            // 重新设置线程中断状态
                            Thread.currentThread().interrupt();
                        }
                    }
                    // 执行到此处说明线程马上终止
                    started = false;
                });
                rptThread.start();
            }

            // 终止采集功能
            synchronized void stop() {
                // 设置中断标志位
                terminated = true;
                // 中断线程 rptThread
                rptThread.interrupt();
            }
        }

    }
}
