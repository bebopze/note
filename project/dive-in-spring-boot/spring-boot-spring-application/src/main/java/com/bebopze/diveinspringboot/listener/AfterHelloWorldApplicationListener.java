package com.bebopze.diveinspringboot.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

/**
 * After HelloWorld {@link ApplicationListener} 监听 {@link ContextRefreshedEvent}
 *
 * @author bebopze
 * @since 2018/5/17
 */
public class AfterHelloWorldApplicationListener implements ApplicationListener<ContextRefreshedEvent>,Ordered {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("AfterHelloWorld : " + event.getApplicationContext().getId()
                + " , timestamp : " + event.getTimestamp());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
