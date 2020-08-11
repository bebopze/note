package com.bebopze.jdk.patterndesign;

import com.bebopze.jdk.eventbus.*;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 12. 观察者 模式（Observer）--->  发布订阅 模式（Publish-Subscribe）
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class _12_Publish_Subscribe {


    // 观察者模式 也被称为 发布订阅模式
    //
    // 定义：
    //      在 对象之间 定义一个 一对多的依赖，当 一个对象状态 改变 的时候，所有依赖的对象 都会 自动收到通知。

    // 1主题  -->  N订阅者


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        // 1、同步阻塞
        test__template();


        // 2、异步非阻塞
        test__EventBus();
    }


    /**
     * 发布-订阅  的“模板代码”
     */
    private static void test__template() {


        // 1、观察者 需要注册到 被观察者 中
        // 2、被观察者 需要依次遍历 观察者，来发送消息


        // 主题（被观察者）
        ConcreteSubject subject = new ConcreteSubject();

        // 订阅者（观察者）
        ConcreteObserverOne concreteObserverOne = new ConcreteObserverOne();
        ConcreteObserverTwo concreteObserverTwo = new ConcreteObserverTwo();


        // 订阅（注册）   -->    N个订阅者   订阅   1个主题
        //
        // 一对多的依赖   --->   主题(1) - 订阅者(N)
        subject.registerObserver(concreteObserverOne);
        subject.registerObserver(concreteObserverTwo);


        // 主题发布消息 ---通知---> 所有观察者
        subject.notifyObservers(new Message(1L, "发布者-发布一条消息"));
    }


    private static void test__EventBus() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Observer1 observer1 = new Observer1();
        Observer2 observer2 = new Observer2();
        Observer3 observer3 = new Observer3();

        Msg1 msg1 = new Msg1(1L, "消息1");
        Msg2 msg2 = new Msg2(2L, "消息2");
        Msg3 msg3 = new Msg3(3L, "消息3");


        // 同步/异步 EventBus
        EventBus eventBus = new AsyncEventBus("event_bus__name", executorService);
//        EventBus eventBus = new EventBus();

        // 注册观察者
        eventBus.register(observer1);
        eventBus.register(observer2);
        eventBus.register(observer3);

        // 发送消息
        eventBus.post(msg1);
        System.out.println("----------------------------------");

        eventBus.post(msg2);
        System.out.println("----------------------------------");

        eventBus.post(msg3);


        System.out.println("====================================== >>> eventBus name : " + eventBus.identifier());
    }
}


// ------------------------------------------------------ 实现 ----------------------------------------------------------


/**
 * 订阅者     /   （观察者）
 */
interface Observer {
    /**
     * 处理通知
     *
     * @param message
     */
    void update(Message message);
}

/**
 * 主题
 */
interface Subject {

    /**
     * 订阅主题
     *
     * @param observer 观察者  -->  订阅 主题
     */
    void registerObserver(Observer observer);

    /**
     * 取消订阅 主题
     *
     * @param observer 观察者  -->  取消订阅 主题
     */
    void removeObserver(Observer observer);

    /**
     * 主题发布消息 ---通知---> 所有观察者
     *
     * @param message 通知内容
     */
    void notifyObservers(Message message);
}


/**
 * 主题
 */
class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * 主题发布消息 ---通知---> 所有观察者           // 被观察者需要依次遍历观察者来发送消息
     *
     * @param message 通知内容
     */
    @Override
    public void notifyObservers(Message message) {
        for (Observer observer : observers) {
            // 通知 观察者
            observer.update(message);
        }
    }

}

/**
 * 订阅者1     /   （观察者1）
 */
class ConcreteObserverOne implements Observer {

    @Override
    public void update(Message message) {
        // 获取消息通知，执行自己的逻辑...
        System.out.println(Thread.currentThread().getName() + " --- 订阅者1 处理通知..." + message);
    }
}

/**
 * 订阅者2     /   （观察者2）
 */
class ConcreteObserverTwo implements Observer {

    @Override
    public void update(Message message) {
        // 获取消息通知，执行自己的逻辑...
        System.out.println(Thread.currentThread().getName() + " --- 订阅者2 处理通知..." + message);
    }
}


@Data
@AllArgsConstructor
class Message {
    private Long id;
    private String msg;
}