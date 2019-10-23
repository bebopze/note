package com.junzijian.jdk.concurrent.concurrentmode;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;

/**
 * 3.3 线程本地存储模式：没有共享，就没有伤害
 *
 * @author liuzhe
 * @date 2019/6/16
 */
public class ThreadLocalModel {

    public static void main(String[] args) {

        test__1();

        // 在线程池中，正确使用 ThreadLocal  ===> try{}finally{}方案，手动释放资源
        test__2();
    }

    /**
     * 在线程池中，正确使用 ThreadLocal  ===> try{}finally{}方案，手动释放资源
     */
    private static void test__2() {

//        ExecutorService es;
//        ThreadLocal<Object> tl;
//        es.execute(() -> {
//            Object obj = new Object();
//            //ThreadLocal 增加变量
//            tl.set(obj);
//            try {
//                // 省略业务逻辑代码
//            } finally {
//                // 手动清理 ThreadLocal
//                tl.remove();
//            }
//        });

    }

//    class Thread {
//        // 内部持有 ThreadLocalMap
//        ThreadLocal.ThreadLocalMap threadLocals;
//    }

//    class ThreadLocal<T> {
//        public T get() {
//            // 首先获取线程持有的
//            //ThreadLocalMap
//            ThreadLocalMap map = java.lang.Thread.currentThread().threadLocals;
//            // 在 ThreadLocalMap 中
//            // 查找变量
//            ThreadLocalMap.Entry e = map.getEntry(this);
//            return e.value;
//        }
//
//        static class ThreadLocalMap {
//            // 内部是数组而不是 Map
//            Entry[] table;
//
//            // 根据 ThreadLocal 查找 Entry
//            Entry getEntry(ThreadLocal key) {
//                // 省略查找逻辑
//            }
//
//            //Entry 定义
//            static class Entry extends
//                    WeakReference<ThreadLocal> {
//                Object value;
//            }
//        }
//    }


    private static void test__1() {

        // 不同线程执行下面代码
        // 返回的 df 是不同的
        DateFormat df = SafeDateFormat.get();
    }


    static class SafeDateFormat {
        // 定义 ThreadLocal 变量
        static final ThreadLocal<DateFormat> tl =
                ThreadLocal.withInitial(
                        () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                );

        static DateFormat get() {
            return tl.get();
        }
    }
}
