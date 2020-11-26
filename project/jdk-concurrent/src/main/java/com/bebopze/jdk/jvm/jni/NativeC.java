package com.bebopze.jdk.jvm.jni;

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


    /**
     * 我们需要 在其他native方法 被调用之前 完成链接工作
     *
     *   因此，我们往往会在 类的初始化方法里 调用该registerNatives方法
     */
    static {
        registerNatives();
    }


    public static void main(String[] args) {
        try {
            System.loadLibrary("hello");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.exit(1);
        }
        new NativeC().hello("jni native hello");
    }

}

