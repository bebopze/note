package com.bebopze.jdk.jvm.jni;

/**
 * JNI native method
 *
 * @author bebopze
 * @date 2020/7/29
 */
public class NativeC {

    // javac -h . NativeC.java


    // =============================== native 方法的链接 ===============================


    // 1、第一种是让 Java 虚拟机自动查找符合 默认命名规范的 C 函数，并且链接起来。


    public static native int hashcode();

    public native void hello();

    public native void hello(String name);


    // ------------------------------------------------------------------------

    // 2、第二种链接方式则是在 C 代码中 主动链接

    private static native void registerNatives();


    /**
     * 我们需要 在其他native方法 被调用之前 完成链接工作
     *
     *   因此，我们往往会在 类的初始化方法里 调用该registerNatives方法
     */
    static {
        registerNatives();
    }



    // ------------------------------------------------------------------------


    // ======================== 自定义 native +  C函数实现：========================


    // --------------------  1、C函数的实现
    //
    //    1、按照 JNI默认命名规范  命名C函数
    //
    //    2、实现 C函数
    //
    //        // foo.c
    //        #include <stdio.h>
    //        #include "org_example_Foo.h"
    //
    //        JNIEXPORT void JNICALL Java_org_example_Foo_bar__Ljava_lang_String_2Ljava_lang_Object_2
    //                (JNIEnv *env, jobject thisObject, jstring str, jobject obj) {
    //            printf("Hello, World\n");
    //            return;
    //        }


    // --------------------  2、C函数的编译
    //
    //    通过 gcc命令 将其编译成为 动态链接库
    //
    //        # 该命令仅适用于macOS
    //        $ gcc -I$JAVA_HOME/include -I$JAVA_HOME/include/darwin -o libfoo.dylib -shared foo.c


    // --------------------  3、C函数的发布
    //
    //     如果libfoo.dylib不在当前路径下，我们可以在启动 Java 虚拟机时配置java.library.path参数


    // --------------------  4、C函数的加载
    //
    //    在 Java 程序中，我们可以通过System.loadLibrary("foo")方法来加载libfoo.dylib


    // --------------------  5、C函数的调用
    //
    //    在Java中 通过JNI机制 调用 自定义native方法


    // ----------------------------------------------------


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

