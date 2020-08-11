package com.bebopze.jdk.patterndesign;

import com.bebopze.jdk.callback.Client;

/**
 * 13. 模板(方法)模式
 *
 * @author bebopze
 * @date 2020/8/11
 */
public class _13_TemplateMethod {


    // 作用：
    //      复用、扩展


    // 经典实现：
    //      模板方法 定义为 final，可以 避免被子类重写。
    //      需要子类重写的方法 定义为 abstract，可以 强迫子类去实现。


    // 实际用例：
    //      Java IO  InputStream    read(byte b[], int off, int len);   -->    read();
    //      AbstractList            addAll();                           -->    add();
    //      Servlet                 service();                          -->    doGet();    /   doPost();
    //      JUnit                   runBare();                          -->    setUp();    /   tearDown();


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        // 1、经典实现

        // 模板  -->  继承  -->   子类复写
        test__template();


        // 回调  -->  组合  -->   回调对象
        test_callback();
    }

    private static void test_callback() {

        Client.custom_callback();
    }


    private static void test__template() {

        AbstractClass tempClass1 = new ConcreteClass1();
        tempClass1.templateMethod();

        System.out.println("-------------------------");

        AbstractClass tempClass2 = new ConcreteClass2();
        tempClass2.templateMethod();
    }

}


// ----------------------------------- 经典实现 ---------------------------------------


abstract class AbstractClass {

    /**
     * 模板方法 定义为 final，避免被子类重写。
     */
    public final void templateMethod() {
        // logic...

        // 子类扩展
        method1();

        // 子类扩展
        method2();

        // logic...
    }


    // -------------- 预留 扩展点 --------------

    /**
     * 需要子类重写的方法 定义为 abstract，可以 强迫子类去实现。
     */
    protected abstract void method1();

    /**
     * 需要子类重写的方法 定义为 abstract，可以 强迫子类去实现。
     */
    protected abstract void method2();
}


class ConcreteClass1 extends AbstractClass {

    @Override
    protected void method1() {
        //...
        System.out.println("method1-------扩展1");
    }

    @Override
    protected void method2() {
        //...
        System.out.println("method2-------扩展1");
    }
}

class ConcreteClass2 extends AbstractClass {

    @Override
    protected void method1() {
        //...

        System.out.println("method1-------扩展2");
    }

    @Override
    protected void method2() {
        //...
        System.out.println("method2-------扩展2");
    }
}
