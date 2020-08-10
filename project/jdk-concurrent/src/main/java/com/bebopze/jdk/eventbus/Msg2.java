package com.bebopze.jdk.eventbus;

/**
 * 消息类型2
 * -
 * - 跟经典的观察者模式的不同之处在于：
 * -    当我们调用 post() 函数发送消息的时候，并非把消息发送给所有的观察者，而是发送给可匹配的观察者。
 * -    所谓可匹配指的是，能接收的消息类型是发送消息（post 函数定义中的 event）类型的父类。
 * -
 * - Msg1 是 Msg2 的父类  ---->  发送 Msg2类型 的消息，其 父类Msg1 也能接收到。
 * -
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class Msg2 extends Msg1 {


    public Msg2(Long id, String msg) {
        super(id, msg);
    }
}
