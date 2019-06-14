package com.junzijian.jdk.concurrent.concurrentmode;

/**
 * 3.1  Immutability模式：如何利用不变性解决并发问题？
 *
 * @author liuzhe
 * @date 2019/6/13
 */
public class ImmutabilityMode {


    // -------------------------- 享元模式  Long --------------------------


    public static void main(String[] args) {

        Long value = valueOf(127);

        System.out.println(value);


        A a = new A();
        B b = new B();

        System.out.println(a.al == b.bl);


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

        // [-128,127] 是共享对象，  之外不是
        Long al = Long.valueOf(1);

        public void setAX() {
            synchronized (al) {
                // 省略代码无数
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
