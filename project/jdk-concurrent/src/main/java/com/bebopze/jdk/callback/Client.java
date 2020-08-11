package com.bebopze.jdk.callback;

/**
 * -
 *
 * @author bebopze
 * @date 2020/8/11
 */
public class Client {


    public static void main(String[] args) {

        custom_callback();
    }


    public static void custom_callback() {

        CallbackTemplate callbackTemplate = new CallbackTemplate();

        callbackTemplate.process(new ICallback() {

            @Override
            public void callback() {

                System.out.println("扩展-------自定义 callback()函数   回调内容 ...");
            }
        });
    }

}
