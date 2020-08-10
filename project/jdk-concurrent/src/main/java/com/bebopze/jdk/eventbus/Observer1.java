package com.bebopze.jdk.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 观察者1
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class Observer1 {


    @Subscribe
    public void handleMsg(Msg1 msg) {
        System.out.println(Thread.currentThread().getName() + " --- Observer1 处理消息：" + msg);
    }

}
