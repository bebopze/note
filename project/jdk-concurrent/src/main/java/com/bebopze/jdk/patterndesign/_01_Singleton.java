package com.bebopze.jdk.patterndesign;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 1. 单例模式
 *
 * @author bebopze
 * @date 2020/8/3
 */
public class _01_Singleton {


    //      全局唯一 对象


    // 定义
    //      一个类只允许创建一个对象（或者叫实例），那这个类就是一个单例类，这种设计模式就叫作单例设计模式，简称单例模式。

    // 用处
    //      有些数据在系统中只应该保存一份，就比较适合设计为单例类。比如，系统的配置信息类。
    //      还可以使用单例解决 资源访问冲突 的问题。


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        long id_1 = IdGenerator_1.getInstance().getId();
        long id_2 = IdGenerator_2.getInstance().getId();
        long id_3 = IdGenerator_3.getInstance().getId();
        long id_4 = IdGenerator_4.getInstance().getId();
        long id_5 = IdGenerator_5.INSTANCE.getId();

        System.out.println();
    }


    // ----------------------------------实现-----------------------------


    /**
     * 1. 饿汉式
     */
    static public class IdGenerator_1 {
        private AtomicLong id = new AtomicLong(0);
        private static final IdGenerator_1 instance = new IdGenerator_1();

        private IdGenerator_1() {
        }

        public static IdGenerator_1 getInstance() {
            return instance;
        }

        public long getId() {
            return id.incrementAndGet();
        }
    }

    /**
     * 2. 懒汉式
     */
    static public class IdGenerator_2 {
        private AtomicLong id = new AtomicLong(0);
        private static IdGenerator_2 instance;

        private IdGenerator_2() {
        }

        public static synchronized IdGenerator_2 getInstance() {
            if (instance == null) {
                instance = new IdGenerator_2();
            }
            return instance;
        }

        public long getId() {
            return id.incrementAndGet();
        }
    }


    /**
     * 3. 双重检测       -- http://www.imooc.com/article/276841
     */
    static public class IdGenerator_3 {
        private AtomicLong id = new AtomicLong(0);
        private static IdGenerator_3 instance;

        private IdGenerator_3() {
        }

        public static IdGenerator_3 getInstance() {
            if (instance == null) {
                synchronized (IdGenerator_3.class) { // 此处为类级别的锁
                    if (instance == null) {
                        instance = new IdGenerator_3();
                    }
                }
            }
            return instance;
        }

        public long getId() {
            return id.incrementAndGet();
        }
    }


    /**
     * 4. 静态类部类
     * -
     * -
     * 当外部类 IdGenerator 被加载的时候，并不会创建 SingletonHolder 实例对象。
     * 只有当调用 getInstance() 方法时，SingletonHolder 才会被加载，这个时候才会创建 instance。
     * instance 的唯一性、创建过程的线程安全性，都由 JVM 来保证。
     * 所以，这种实现方法既保证了线程安全，又能做到延迟加载。
     */
    static public class IdGenerator_4 {
        private AtomicLong id = new AtomicLong(0);

        private IdGenerator_4() {
        }

        private static class SingletonHolder {
            private static final IdGenerator_4 instance = new IdGenerator_4();
        }

        public static IdGenerator_4 getInstance() {
            return IdGenerator_4.SingletonHolder.instance;
        }

        public long getId() {
            return this.id.incrementAndGet();
        }
    }

    /**
     * 5. 枚举     - Java 枚举类型本身的特性   --->  天然单例
     */
    public enum IdGenerator_5 {

        INSTANCE;

        private AtomicLong id = new AtomicLong(0);

        public long getId() {
            return id.incrementAndGet();
        }
    }
}


