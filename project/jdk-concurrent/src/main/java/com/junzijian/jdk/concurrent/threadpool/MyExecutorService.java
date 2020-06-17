package com.junzijian.jdk.concurrent.threadpool;

import lombok.Data;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author bebop
 * @date 2019/6/6
 */
public class MyExecutorService {

//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//
//        String a = "a";
//
//        // 创建 Result 对象 r
//        Result r = new Result();
//        r.setAAA(a);
//        // 提交任务
//        Future<Result> future = executor.submit(new Task(r), r);
//        Result fr = future.get();
//
//        // 下面等式成立
////        fr == r;
////        fr.getAAA() == a;
////        fr.getXXX() == x;
//
//    }
//
//
//    static class Task implements Runnable {
//        Result r;
//
//        // 通过构造函数传入 result
//        Task(Result r) {
//            this.r = r;
//        }
//
//        @Override
//        public void run() {
//            // 可以操作 result
//            a = r.getAAA();
//            r.setXXX(x);
//        }
//    }
//
//
//    @Data
//    static class Result {
//
//        private String AAA;
//
//        private String XXX;
//    }

}

