package com.bebopze.jdk.jvm;

/**
 * JNI native method
 *
 * @author bebopze
 * @date 2020/7/29
 */
public class NativeC {

    // javac -h . NativeC.java


    // native 方法的链接


    // 第一种是让 Java 虚拟机自动查找符合 默认命名规范的 C 函数，并且链接起来。


    public static native int hashcode();

    public native void hello();

    public native void hello(String name);


    // ------------------------------------------------------------------------

    // 第二种链接方式则是在 C 代码中 主动链接

    private static native void registerNatives();

    static {
        registerNatives();
    }

}

