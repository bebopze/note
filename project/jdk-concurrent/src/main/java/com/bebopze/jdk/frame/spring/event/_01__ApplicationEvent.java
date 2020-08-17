package com.bebopze.jdk.frame.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 观察者 模式
 *
 * @author bebopze
 * @date 2020/8/17
 */
@Component
public class _01__ApplicationEvent {

    @Autowired
    private Publisher publisher;


    public static void main(String[] args) {


        test__ApplicationEvent();
    }


    private static void test__ApplicationEvent() {


        MyEvent myEvent = new MyEvent("source", "msg");

        Publisher.publishEvent(myEvent);
    }


}


/**
 * Publisher 发送者
 */
@Component
class Publisher {


    /**
     * 把观察者注册到了 ApplicationContext 对象中。
     * - 这里的 ApplicationContext 就相当于 Google EventBus 框架中的“事件总线”。
     * -
     * - 具体到源码来说，ApplicationContext 只是一个接口，具体的代码实现包含在它的实现类 AbstractApplicationContext 中。
     */
    @Autowired
    private static ApplicationContext applicationContext;


    public static void publishEvent(MyEvent event) {
        applicationContext.publishEvent(event);
    }
}


/**
 * Listener 监听者
 */
@Component
class Listener implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        MyEvent myEvent = (MyEvent) event;

        Object source = myEvent.getSource();
        String message = myEvent.getMessage();


        System.out.println(source + "--------------" + message);
    }
}


/**
 * Event 事件
 */
class MyEvent extends ApplicationEvent {
    private String message;

    public MyEvent(Object source, String message) {
        super(source);
    }

    public String getMessage() {
        return this.message;
    }
}