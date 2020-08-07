package com.bebopze.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK Proxy
 * -
 * -    Java 动态代理与class字节码动态修改技术    -->     https://blog.csdn.net/lxlmycsdnfree/article/details/71404121
 * -    jdk动态代理和Cglib字节码增强             -->     https://www.cnblogs.com/msi-chen/p/10801816.html
 * -                                                  https://www.cnblogs.com/jmcui/p/11968698.html
 *
 * @author bebopze
 * @date 2020/8/5
 */
public class JDKProxy {


    // JDK动态代理
    //    利用拦截器加上反射机制生成一个实现代理接口的匿名类，在调用具体方法时，调用InvocationHandler来处理。


    // JDK动态代理只需要 JDK环境 就 可以进行代理，流程为：
    //     1、实现InvocationHandler
    //     2、使用Proxy.newProxyInstance产生代理对象
    //     3、被代理的对象必须实现接口


    // 两种代理方式的本质：
    //      1、JDK 动态代理 是针对实现了 接口 的类生成代理，不是针对类。
    //
    //      2、CGLIB 使用的是 为被代理类 生成一个 子类，通过 继承 的方式，覆盖并增强 其方法。
    //         但是因为是继承，所以不能声明 被代理类 和 增强方法 为 final。
    //         final无法被继承、覆盖，就无法实现CGLIB代理。


    // 场景：
    //     有接口类的代理


    // Spring AOP 动态代理选择：
    //     1、当一个类有接口的时候，就会选用JDK的动态代理
    //     2、当一个类没有实现接口的时候，就会选用CGLIB代理的方式


    // --------------------------------------------------------------------------------


    public static void main(String[] args) {

        test_Proxy();

        test_MyFactoryBean();
    }


    public static void test_Proxy() {

        // 代理对象
        UserServiceImpl target = new UserServiceImpl();

        // JDK 代理类      -- 通过反射 实现被代理类的接口
        UserService proxy = (UserService) new MyInvocationHandler(target).getProxy();

        proxy.eat();
        proxy.wc();


        System.out.println();
    }


    public static void test_MyFactoryBean() {

        // target ： 目标类
        final UserService target = new UserServiceImpl();

        // proxy
        UserService proxy = MyFactoryBean.getInstance(target);

        proxy.eat();
        proxy.wc();
    }
}


// -------------------------------------------------- JDK Proxy 实现 ----------------------------------------------------


/**
 * 接口
 */
interface UserService {

    void eat();

    void wc();
}

/**
 * 被代理类             // 3、被代理的对象必须实现接口
 */
class UserServiceImpl implements UserService {

    @Override
    public void eat() {
        System.out.println("--------->吃饭");
    }

    @Override
    public void wc() {
        System.out.print("上茅房------>");
    }
}


/**
 * 切面类
 */
class MyAspect {

    public void before() {
        System.out.print("先洗手再");
    }

    public void after() {
        System.out.print("后要洗手");
    }
}


/**
 * 产生代理对象的工厂类
 */
class MyFactoryBean {

    public static UserService getInstance(UserService target) {

        // Aspect : 切面类
        final MyAspect aspect = new MyAspect();

        // Weaving : 织入，也就是产生代理的过程                                  // 2、使用Proxy.newProxyInstance产生代理对象
        UserService proxy = (UserService) Proxy.newProxyInstance(

                // ClassLoader loader
                MyFactoryBean.class.getClassLoader(),

                // Class<?>[] interfaces,
                new Class[]{UserService.class},

                // InvocationHandler handler
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        // 接口方法增强
                        if (method.getName().equals("eat")) {
                            aspect.before();
                        }

                        // 执行原方法
                        Object result = method.invoke(target, args);

                        // 接口方法增强
                        if (method.getName().equals("wc")) {
                            aspect.after();
                        }

                        return result;
                    }
                });

        return proxy;
    }
}


/**
 * 1、实现 InvocationHandler    ---> 生成代理类，及方法增强           - https://blog.csdn.net/lxlmycsdnfree/article/details/71404121
 * -
 * - 等同于 Cglib Proxy --> MethodInterceptor
 * -
 */
class MyInvocationHandler implements InvocationHandler {

    /**
     * 被代理目标对象
     */
    private Object target;

    /**
     * target对象 通过构造传入
     *
     * @param target
     */
    public MyInvocationHandler(Object target) {
        this.target = target;
    }


    // Aspect : 切面类
    final MyAspect aspect = new MyAspect();


    /**
     * 对代理类的方法 进行 覆盖增强      =====>  target -> interface -> method 重写(增强)
     * -
     * - 等同于 Cglib Proxy --> MethodInterceptor --> intercept()
     * -
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 方法增强
        if (method.getName().equals("eat")) {
            aspect.before();
        }

        // 执行原方法
        Object result = method.invoke(target, args);

        // 方法增强
        if (method.getName().equals("wc")) {
            aspect.after();
        }

        return result;
    }


    /**
     * 生成 target的接口 的 代理对象
     * <p>
     * target -->  接口  -->  生成 接口代理类 实例对象
     *
     * @return
     */
    public Object getProxy() {

        /**
         * 通过反射 实现被代理类target的接口
         */
        Object proxyInstance = Proxy.newProxyInstance(

                // ClassLoader
                Thread.currentThread().getContextClassLoader(),

                // Class<?>[] interfaces      --->  被代理对象target 的 所有接口
                target.getClass().getInterfaces(),

                // InvocationHandler handler    ---> 注册 handler（的 invoke 代理函数）
                this
        );

        return proxyInstance;
    }
}
