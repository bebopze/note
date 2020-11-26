package com.bebopze.jdk.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib Proxy
 * -
 * -    Java 动态代理与class字节码动态修改技术    -->     https://blog.csdn.net/lxlmycsdnfree/article/details/71404121
 * -    jdk动态代理和Cglib字节码增强             -->     https://www.cnblogs.com/msi-chen/p/10801816.html
 * -                                                  https://www.cnblogs.com/jmcui/p/11968698.html
 *
 * @author bebopze
 * @date 2020/8/5
 */
public class CglibProxy {

    //  --------------------- Cglib子类  无法代理 final、static


    // 无法代理 final 方法
    // 子类无法 重写 final 方法

    // CGLIB动态代理
    //     1、通过加载对象类的class文件，修改其字节码 生成子类 的方式完成，不需要实现接口.
    //     2、但是需要第三方库：CGLIB类库的支持


    // 实现：
    //     1、生成 target 的子类
    //     2、通过 ASM 修改运行时 子类方法 的 .class字节码，来进行增强


    // 场景：
    //     无接口类的代理，作为JDK Proxy的补充。


    // Spring AOP 动态代理选择：
    //     1、当一个类有接口的时候，就会选用JDK的动态代理
    //     2、当一个类没有实现接口的时候，就会选用CGLIB代理的方式


    // --------------------------------------------------------------------------------


    public static void main(String[] args) {

        test_Proxy();
    }

    public static void test_Proxy() {

        // target
        PeopleService target = new PeopleService();

        // Cglib proxy
        PeopleService proxy = (PeopleService) new MyMethodInterceptor().createProxy(target);


        proxy.eat();
        proxy.wc();


        // 子类 无法重写 final方法
        proxy.final_method();
        proxy.static_method();
    }
}


// ------------------------------------------------- Cglib Proxy 实现 ---------------------------------------------------


/**
 * - 实现 MethodInterceptor   ---> 生成代理类，及方法增强
 * -
 * - 等同于 JDK Proxy --> InvocationHandler
 * -
 */
class MyMethodInterceptor implements MethodInterceptor {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 生成代理对象  --> 创建target的子类，然后通过 ASM 字节码注入的方式，对 父类target的方法 字节码 进行修改覆盖，达到方法代理增强。
     *
     * @param target
     * @return
     */
    public Object createProxy(Object target) {

        // target
        this.target = target;

        Enhancer e = new Enhancer();
        // 等同 JDK Proxy --> ClassLoader
        e.setSuperclass(target.getClass());
        // 注册 该 MethodInterceptor    --->   的 intercept(覆盖)增强 方法
        e.setCallback(this);

        // proxy
        Object proxy = e.create();
        return proxy;
    }


    /**
     * 对代理类的方法 进行 覆盖增强      =====>  target -> method 重写(增强)
     * -
     * 等同于 JDK Proxy --> InvocationHandler --> invoke()
     * -
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        // 通过ASM 修改运行时 target 的 .class字节码，来进行增强

        if ("eat".equals(method.getName())) {
            System.out.print("先洗手再---->");
        }

        Object result = method.invoke(target, objects);

        if ("wc".equals(method.getName())) {
            System.out.println("---->之后要洗手");
        }


        // --------------- Cglib子类  无法代理 final、static

        if ("final_method".equals(method.getName())) {
            System.out.print("Cglib子类  无法代理 final");
        }

        if ("static_method".equals(method.getName())) {
            System.out.print("Cglib子类  无法代理 static");
        }


        return result;
    }
}

/**
 * 被代理类
 */
class PeopleService {

    public void eat() {
        System.out.println("吃饭");
    }

    public void wc() {
        System.out.print("上厕所");
    }


    // ---------------- Cglib子类  无法代理 final、static


    public final void final_method() {
        System.out.println("final method...   Cglib子类 无法重写父类 final方法");
    }

    public static final void static_method() {
        System.out.println("static method...  Cglib子类 无法重写父类 static方法");
    }
}

