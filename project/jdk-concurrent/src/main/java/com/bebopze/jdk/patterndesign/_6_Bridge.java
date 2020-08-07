package com.bebopze.jdk.patterndesign;

import java.util.List;

/**
 * 6. 桥接模式      Bridge Design Pattern
 * -
 * -    将抽象和实现解耦，让它们可以独立变化。
 * -
 * -    另外一种理解方式：
 * -            一个类存在两个（或多个）独立变化的维度，我们通过组合的方式，让这两个（或多个）维度可以独立进行扩展。
 *
 * @author bebopze
 * @date 2020/8/7
 */
public class _6_Bridge {


    // 1、独立开发
    // 2、任意组合


    // ---------------------------------------------------------------


    public static void main(String[] args) {


        // Notification 类相当于抽象，MsgSender 类相当于实现。
        // 两者可以独立开发，通过组合关系（也就是桥梁）任意组合在一起。
        //
        // 所谓任意组合的意思就是，不同紧急程度的消息和发送渠道之间的对应关系。
        // 不是在代码中固定写死的，我们可以动态地去指定（比如，通过读取配置来获取对应关系）。


        // ------------------- 任意组合 ==> sender + notification -------------------

        // 1、 MsgSender、Notification 独立开发
        // 2、 MsgSender、Notification 任意组合

        // 任意 sender
        MsgSender msgSender = new MsgSender.EmailMsgSender();

        // 任意 notification
        Notification notification = new Notification.NormalNotification(msgSender);


        notification.notify();
    }


    // ----------------------------------实现-----------------------------


}


// ----------------------------------Notification 相当于 "抽象"-----------------------------
// --------------------------------------- 独立开发 ----------------------------------------


/**
 * Notification 类相当于 "抽象"
 */
abstract class Notification {

    protected MsgSender msgSender;

    Notification(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    abstract void notify(String message);


    // ----------------------------------Notification 实现    独立开发-----------------------------


    static class SevereNotification extends Notification {

        SevereNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        void notify(String message) {
            msgSender.send(message);
        }
    }

    static class UrgencyNotification extends Notification {

        UrgencyNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        void notify(String message) {
            msgSender.send(message);
        }
    }

    static class NormalNotification extends Notification {

        NormalNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        void notify(String message) {
            msgSender.send(message);
        }
    }

    static class TrivialNotification extends Notification {

        TrivialNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        void notify(String message) {
            msgSender.send(message);
        }
    }
}


// -------------------------------------MsgSender 相当于 "实现"----------------------------------
// ------------------------------------------ 独立开发 ------------------------------------------


/**
 * MsgSender 类相当于 "实现"
 */
interface MsgSender {

    void send(String message);


    // -------------------------------------MsgSender 实现    独立开发----------------------------------


    class TelephoneMsgSender implements MsgSender {
        private List<String> telephones;

        public TelephoneMsgSender(List<String> telephones) {
            this.telephones = telephones;
        }

        @Override
        public void send(String message) {
            //...
        }

    }

    class EmailMsgSender implements MsgSender {

        @Override
        public void send(String message) {

        }
    }

    class WechatMsgSender implements MsgSender {

        @Override
        public void send(String message) {

        }
    }
}



