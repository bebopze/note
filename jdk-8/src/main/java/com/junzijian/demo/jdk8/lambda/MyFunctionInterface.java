package com.junzijian.demo.jdk8.lambda;

/**
 * 自定义函数式接口  -> 编写一个只有一个抽象方法的接口即可
 *
 * @author junzijian
 * @date 2019/12/9
 */
// @FunctionalInterface是可选的，但加上该标注编译器会帮你检查接口是否符合函数接口规范。 就像加入@Override标注，会检查函数是否被重载一样。
@FunctionalInterface
public interface MyFunctionInterface {

    /**
     * 唯一抽象方法
     *
     * @param name
     * @return
     */
    String sayHello(String name);


    /**
     * FunctionalInterface 只能有且只有一个抽象方法
     */
//    String method2();
}
