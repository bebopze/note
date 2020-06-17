package com.junzijian.jdk.concurrent.concurrentmode;

import javax.security.auth.login.AccountException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 3.1  Immutability模式：如何利用不变性解决并发问题？
 *
 * @author bebop
 * @date 2019/6/13
 */
public class ImmutabilityMode {


    // -------------------------- 享元模式  Long --------------------------

    public static final Integer num_1 = null;
    public static final int num_2 = 2;

    public static final ImmutabilityMode.A claa_A = new A();


    public static void main(String[] args) {


        test__final_Base_Type();
        test__final_Object();


        test__final_3();

        test__final__SAFE();


        test__final__THINK();


        Long value = valueOf(127);

        System.out.println(value);


        A a = new A();
        B b = new B();

        System.out.println(a.al == b.bl);


    }

    private static void test__final__THINK() {

        /**
         * 根据文章内容,
         * 一个类具备不可变属性需要满足"类和属性都必须是 final 的,所有方法均是只读的",
         * 类的属性如果是引用型,该属性对应的类也需要满足不可变类的条件,且不能提供修改该属性的方法,
         * Account类的唯一属性user是final的,提供的方法是可读的,user的类型是StringBuffer,StringBuffer也是final的,
         * 这样看来,Account类是不可变性的,
         *
         * 但是去看StringBuffer的源码,
         * 你会发现StringBuffer类的属性value是可变的
         * < String类中的value定义:private final char value[]; StringBuffer类中的value定义:char[] value;>,
         * 并且提供了append(Object object)和setCharAt(int index, char ch)修改value.
         * 所以,Account类不具备不可变性
         *
         *
         *
         * 这段代码应该是线程安全的，但它不是不可变模式。
         * StringBuffer只是字段引用不可变，值是可以调用StringBuffer的方法改变的，
         * 这个需要改成把字段改成String这样的不可变对象来解决。
         */
        final class Account {

            private final StringBuffer user;

            public Account(String user) {
                this.user = new StringBuffer(user);
            }

            public StringBuffer getUser() {
                return this.user;
            }

            @Override
            public String toString() {
                return "user" + user;
            }
        }

    }

    private static void test__final__SAFE() {



        class SafeWM {

            class WMRange {

                final int upper;
                final int lower;

                WMRange(int upper, int lower) {
                    this.upper = upper;
                    this.lower = lower;
                }
            }

            final AtomicReference<WMRange> rf = new AtomicReference<>(new WMRange(10, 5));

            // 设置库存上限
            void setUpper(int v) {
                while (true) {
                    WMRange or = rf.get();
                    // 检查参数合法性
                    if (v < or.lower) {
                        throw new IllegalArgumentException();
                    }
                    WMRange nr = new WMRange(v, or.lower);
                    if (rf.compareAndSet(or, nr)) {
                        return;
                    }
                }
            }
        }


        SafeWM safeWM = new SafeWM();
        safeWM.setUpper(10);
        safeWM.setUpper(11);

        System.out.println(safeWM);

    }

    private static void test__final_3() {


        // Foo 线程安全
        final class Foo {
            final int age = 0;
            final int name = 1;
        }

        // Bar 线程不安全
        class Bar {
            Foo foo;

            void setFoo(Foo f) {
                this.foo = f;
            }
        }

    }


    private static void test__final_Base_Type() {

        // Error:(41, 9) java: 无法为最终变量num_1分配值
//        num_1 = 2;
//        num_2 = 1;

        System.out.println(num_1);
        System.out.println(num_2);

    }

    private static void test__final_Object() {

        // Error:(55, 9) java: 无法为最终变量claa_A分配值
//        claa_A = new A();

        System.out.println("start   ->  " + claa_A.num);

        claa_A.setAX(2L);

        System.out.println("end   ->  " + claa_A.num);
    }


    static Long valueOf(long l) {
        final int offset = 128;
        // [-128,127] 直接的数字做了缓存
        if (l >= -128 && l <= 127) {
            return LongCache.cache[(int) l + offset];
        }
        return new Long(l);
    }

    // 缓存，等价于对象池
    // 仅缓存 [-128,127] 之间的数字
    static class LongCache {

        static final Long cache[] = new Long[-(-128) + 127 + 1];

        static {
            for (int i = 0; i < cache.length; i++) {
                cache[i] = new Long(i - 128);
            }
        }
    }


    // -------------------------- 享元模式  的对象不能拿来做锁 --------------------------

    static class A {

        private Long num = 1L;

        // [-128,127] 是共享对象，  之外不是
        Long al = Long.valueOf(1);

        public void setAX(Long val) {
            synchronized (al) {
                // 省略代码无数

                num = val;
            }
        }
    }

    static class B {
        Long bl = Long.valueOf(1);

        public void setBY() {
            synchronized (bl) {
                // 省略代码无数
            }
        }
    }


//    public final class String {
//
//        private final char value[];
//
//        public String() {
//        }
//
//        // 字符替换
//        String replace(char oldChar, char newChar) {
//
//            // 无需替换，直接返回 this
//            if (oldChar == newChar) {
//                return this;
//            }
//
//            int len = value.length;
//            int i = -1;
//
//            /* avoid getfield opcode */
//            char[] val = value;
//
//            // 定位到需要替换的字符位置
//            while (++i < len) {
//                if (val[i] == oldChar) {
//                    break;
//                }
//            }
//
//            // 未找到 oldChar，无需替换
//            if (i >= len) {
//                return this;
//            }
//
//            // 创建一个 buf[]，这是关键
//            // 用来保存替换后的字符串
//            char buf[] = new char[len];
//            for (int j = 0; j < i; j++) {
//                buf[j] = val[j];
//            }
//
//            while (i < len) {
//                char c = val[i];
//                buf[i] = (c == oldChar) ? newChar : c;
//                i++;
//            }
//
//            // 创建一个新的字符串返回
//            // 原字符串不会发生任何变化
//            return new java.lang.String(buf, true);
//        }
//    }


}
