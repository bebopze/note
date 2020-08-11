package com.bebopze.jdk.callback;

/**
 * -
 *
 * @author bebopze
 * @date 2020/8/11
 */
public class CallbackTemplate {


    public void process(ICallback iCallback) {

        // ...
        System.out.println("Callback Template --> process ...");


        iCallback.callback();
    }
}
