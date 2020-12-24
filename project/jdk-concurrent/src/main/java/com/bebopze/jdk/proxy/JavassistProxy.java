//package com.bebopze.jdk.proxy;
//
//import com.github.thinwonton.java.showcase.proxy.MethodEnhanceHandler;
//import javassist.util.proxy.MethodHandler;
//import javassist.util.proxy.ProxyFactory;
//import javassist.util.proxy.ProxyObject;
//
///**
// * javassist                                - https://my.oschina.net/thinwonton/blog/1186375
// *
// * @author bebopze
// * @date 2020/12/24
// */
//public class JavassistProxy {
//
//    public static Object createProxy(Class<?> target, MethodEnhanceHandler methodEnhanceHandler)
//            throws IllegalAccessException, InstantiationException {
//        ProxyFactory factory = new ProxyFactory();
//        factory.setSuperclass(target);
//        //        //设置过滤器，判断哪些方法调用需要被拦截
//        //        factory.setFilter(new MethodFilter() {
//        //            public boolean isHandled(Method m) {
//        //                return true;
//        //            }
//        //        });
//        Class proxyClass = factory.createClass();
//        Object proxy = proxyClass.newInstance();
//
//        //设置拦截器
//        ProxyObject proxyObject = (ProxyObject) proxy;
//        proxyObject.setHandler(new InternalMethodHandler(methodEnhanceHandler));
//
//        return proxy;
//    }
//
//    private static class InternalMethodHandler implements MethodHandler {
//        private MethodEnhanceHandler methodEnhanceHandler;
//
//        public InternalMethodHandler(MethodEnhanceHandler methodEnhanceHandler) {
//            this.methodEnhanceHandler = methodEnhanceHandler;
//        }
//
//        public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
//            Object retVal = null;
//            try {
//                if (methodEnhanceHandler != null) {
//                    methodEnhanceHandler.doBefore();
//                }
//
//                retVal = proceed.invoke(self, args);
//            } catch (Exception e) {
//                if (methodEnhanceHandler != null) {
//                    methodEnhanceHandler.doThrowing(e);
//                }
//            } finally {
//                if (methodEnhanceHandler != null) {
//                    methodEnhanceHandler.doAfter();
//                }
//            }
//            return retVal;
//        }
//    }
//}