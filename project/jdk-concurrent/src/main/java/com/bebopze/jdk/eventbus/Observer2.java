package com.bebopze.jdk.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 观察者2
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class Observer2 {


    @Subscribe
    public void handleMsg(Msg2 msg) {
        System.out.println(Thread.currentThread().getName() + " --- Observer2 处理消息：" + msg);
    }
}
