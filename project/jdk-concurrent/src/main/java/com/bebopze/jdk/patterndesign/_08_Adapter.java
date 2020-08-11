package com.bebopze.jdk.patterndesign;

/**
 * 8. 适配器模式
 *
 * @author bebopze
 * @date 2020/8/7
 */
public class _08_Adapter {


    // 是一种 "补偿模式"，用来补救设计上的缺陷。       --->   无奈之举


    // 适配器模式的英文翻译是 Adapter Design Pattern。
    //      顾名思义，这个模式就是用来做适配的，
    //      它将不兼容的接口转换为可兼容的接口，
    //      让原本由于接口不兼容而不能一起工作的类可以一起工作。


    // 对于这个模式，有一个经常被拿来解释它的例子，就是 USB 转接头充当适配器，把两种不兼容的接口，通过转接变得可以一起工作。


    // 实现方式：
    //      1、类适配器   使用 继承关系 来实现
    //      2、对象适配器 使用 组合关系 来实现


    // -----------------------------------------------------------------------------------------------------------------


    public static void main(String[] args) {


        // 类适配器         -->   继承
        test_Adaptor();


        // 对象适配器       --> 组合
        test_Adaptor2();
    }


    private static void test_Adaptor() {

        Adaptor adaptor = new Adaptor();

        adaptor.f1();
        adaptor.f2();
        adaptor.fc();

        System.out.println();
    }

    private static void test_Adaptor2() {

        Adaptee2 adaptee = new Adaptee2();

        // 组合
        Adaptor2 adaptor = new Adaptor2(adaptee);


        adaptor.f1();
        adaptor.f2();
        adaptor.fc();

        System.out.println();
    }
}


// ------------------------------------- 类适配器: 基于继承 --------------------------------------

/**
 * 原始接口
 */
interface ITarget {

    void f1();

    void f2();

    void fc();
}

/**
 * 不兼容接口
 */
class Adaptee {

    public void fa() {
        System.out.println("f1 ----兼容了---> fa");
    }

    public void fb() {
        System.out.println("f2 ----兼容了---> fb");
    }

    public void fc() {
        System.out.println("fc ----兼容了---> fa");
    }
}

/**
 * 类适配器: 基于继承           通过第三方转接口 适配器 Adaptor  -->  兼容 ITarget 和 Adaptee
 */
class Adaptor extends Adaptee implements ITarget {

    @Override
    public void f1() {
        super.fa();
    }

    @Override
    public void f2() {
        //...重新实现f2()...

        System.out.print("...重新实现f2()...");

        super.fb();
    }

    // 这里fc()不需要实现，直接继承自Adaptee，这是跟对象适配器最大的不同点
}


// ------------------------------------- 对象适配器：基于组合 --------------------------------------

/**
 * 原始接口
 */
interface ITarget2 {

    void f1();

    void f2();

    void fc();
}

/**
 * 不兼容接口
 */
class Adaptee2 {

    public void fa() {
        System.out.println("f1 ----兼容了---> fa");
    }

    public void fb() {
        System.out.println("f2 ----兼容了---> fb");
    }

    public void fc() {
        System.out.println("fc ----兼容了---> fa");
    }
}

/**
 * 对象适配器：基于组合       通过第三方转接口 适配器 Adaptor2  -->  兼容 ITarget2 和 Adaptee2
 */
class Adaptor2 implements ITarget2 {

    private Adaptee2 adaptee;

    /**
     * 组合  -->  通过构造传入组合对象 adaptee
     *
     * @param adaptee
     */
    public Adaptor2(Adaptee2 adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void f1() {
        // 委托给Adaptee
        adaptee.fa();
    }

    @Override
    public void f2() {
        //...重新实现f2()...

        System.out.print("...重新实现f2()...");

        adaptee.fb();
    }

    @Override
    public void fc() {
        adaptee.fc();
    }
}