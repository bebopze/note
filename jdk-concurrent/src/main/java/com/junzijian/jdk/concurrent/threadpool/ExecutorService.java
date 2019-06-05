//package com.junzijian.jdk.concurrent.threadpool;
//
//import lombok.Data;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
///**
// * @author liuzhe
// * @date 2019/6/5
// */
//public class ExecutorService {
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//
//        java.util.concurrent.ExecutorService executor = Executors.newFixedThreadPool(1);
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
//    class Task implements Runnable {
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
//}
//
//@Data
//class Result {
//
//    private String AAA;
//
//    private String XXX;
//}
