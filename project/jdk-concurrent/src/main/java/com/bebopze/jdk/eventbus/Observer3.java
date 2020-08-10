package com.bebopze.jdk.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 观察者3
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class Observer3 {


    @Subscribe
    public void handleMsg(Msg3 msg) {
        System.out.println(Thread.currentThread().getName() + " --- Observer3 处理消息：" + msg);
    }
}
