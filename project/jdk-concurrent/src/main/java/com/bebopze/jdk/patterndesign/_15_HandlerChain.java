package com.bebopze.jdk.patterndesign;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 15. 职责链模式
 *
 * @author bebopze
 * @date 2020/8/12
 */
public class _15_HandlerChain {


    // 核心：      处理链


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        // 1、经典实现
        test_1();


        // 2、模板方法模式 优化
        test_2();


        // 3、数组存储
        test_3();


        // 4、不终止
        test_4();


        // 案例：过滤/替换 敏感词
        test__demo();
    }


    private static void test__demo() {

        Handler_Demo__WordFilter.SensitiveWordFilterChain filterChain = new Handler_Demo__WordFilter.SensitiveWordFilterChain();

        filterChain.addFilter(new Handler_Demo__WordFilter.AdsWordFilter());
        filterChain.addFilter(new Handler_Demo__WordFilter.SexyWordFilter());
        filterChain.addFilter(new Handler_Demo__WordFilter.PoliticalWordFilter());

        boolean legal = filterChain.filter(new Handler_Demo__WordFilter.Content(1L, "广告、ghs、政策"));
        if (!legal) {
            // 不发表
        } else {
            // 发表
        }

    }


    private static void test_1() {

        HandlerChain1.HandlerChain chain = new HandlerChain1.HandlerChain();

        chain.addHandler(new HandlerChain1.HandlerA());
        chain.addHandler(new HandlerChain1.HandlerB());

        chain.handle();
    }

    private static void test_2() {

        HandlerChain2.HandlerChain chain = new HandlerChain2.HandlerChain();

        chain.addHandler(new HandlerChain2.HandlerA());
        chain.addHandler(new HandlerChain2.HandlerB());

        chain.handle();
    }

    private static void test_3() {


        HandlerChain3.HandlerChain chain = new HandlerChain3.HandlerChain();

        chain.addHandler(new HandlerChain3.HandlerA());
        chain.addHandler(new HandlerChain3.HandlerB());

        chain.handle();
    }

    private static void test_4() {


        HandlerChain4.HandlerChain chain = new HandlerChain4.HandlerChain();

        chain.addHandler(new HandlerChain4.HandlerA());
        chain.addHandler(new HandlerChain4.HandlerB());

        chain.handle();
    }

}


// ----------------------------------- 1、经典实现 ---------------------------------------

// 用链表存储处理器


class HandlerChain1 {

    static abstract class Handler {
        protected Handler successor = null;

        public void setSuccessor(Handler successor) {
            this.successor = successor;
        }

        public abstract void handle();
    }

    /**
     * Handler A
     */
    static class HandlerA extends Handler {

        @Override
        public void handle() {
            boolean handled = false;

            System.out.println("Handler A --------- 1");

            if (!handled && successor != null) {
                // next handler chain
                successor.handle();
            }
        }
    }

    /**
     * Handler B
     */
    static class HandlerB extends Handler {

        @Override
        public void handle() {
            boolean handled = false;

            System.out.println("Handler B --------- 1");

            if (!handled && successor != null) {
                // next handler chain
                successor.handle();
            }
        }
    }


    /**
     * handler chain        - 用 链表 存储处理器
     */
    static class HandlerChain {
        private Handler head = null;
        private Handler tail = null;

        public void addHandler(Handler handler) {
            handler.setSuccessor(null);

            if (head == null) {
                head = handler;
                tail = handler;
                return;
            }

            tail.setSuccessor(handler);
            tail = handler;
        }

        public void handle() {
            if (head != null) {
                head.handle();
            }
        }
    }
}


// ---------------------------- 2、模板方法模式 优化--------------------------


class HandlerChain2 {


    /**
     * - 模板方法handle()：
     * -    1、将 通用流程   successor.handle();   封装在模板方法内部
     * -    2、将 自定义扩展内容  抽象成 doHandle()  给子类实现
     */
    static abstract class Handler {
        protected Handler successor = null;

        public void setSuccessor(Handler successor) {
            this.successor = successor;
        }

        /**
         * 模板方法
         */
        public final void handle() {

            // 子类实现
            boolean handled = doHandle();

            // 通用流程
            if (successor != null && !handled) {
                successor.handle();
            }
        }

        /**
         * 可变部分 -> 扩展       ===>  强制子类实现
         *
         * @return
         */
        protected abstract boolean doHandle();
    }

    static class HandlerA extends Handler {

        @Override
        protected boolean doHandle() {
            boolean handled = false;

            System.out.println("Handler A --------- 2");

            return handled;
        }
    }

    static class HandlerB extends Handler {

        @Override
        protected boolean doHandle() {
            boolean handled = false;

            System.out.println("Handler B --------- 2");

            return handled;
        }
    }


    /**
     * handler chain        - 用 链表 存储处理器
     */
    static class HandlerChain {
        private Handler head = null;
        private Handler tail = null;

        public void addHandler(Handler handler) {
            handler.setSuccessor(null);

            if (head == null) {
                head = handler;
                tail = handler;
                return;
            }

            tail.setSuccessor(handler);
            tail = handler;
        }

        public void handle() {
            if (head != null) {
                head.handle();
            }
        }
    }
}


// ---------------------------- 3、用 数组存储 处理器 --------------------------

class HandlerChain3 {

    interface IHandler {
        boolean handle();
    }

    static class HandlerA implements IHandler {
        @Override
        public boolean handle() {
            boolean handled = false;
            System.out.println("Handler A --------- 3");
            return handled;
        }
    }

    static class HandlerB implements IHandler {
        @Override
        public boolean handle() {
            boolean handled = false;
            System.out.println("Handler B --------- 3");
            return handled;
        }
    }

    /**
     * 处理链          - 用 数组存储 处理器
     */
    static class HandlerChain {
        private List<IHandler> handlers = new ArrayList<>();

        public void addHandler(IHandler handler) {
            this.handlers.add(handler);
        }

        public void handle() {
            for (IHandler handler : handlers) {
                boolean handled = handler.handle();
                if (handled) {
                    break;
                }
            }
        }
    }
}


// --------------------------------- 4、请求被所有的处理器都处理一遍，不中途终止 --------------------------------------------

// 职责链模式还有一种变体，那就是请求会被所有的处理器都处理一遍，不存在中途终止的情况


class HandlerChain4 {

    static abstract class Handler {
        protected Handler successor = null;

        public void setSuccessor(Handler successor) {
            this.successor = successor;
        }

        /**
         * 模板方法
         */
        public final void handle() {
            doHandle();
            if (successor != null) {
                successor.handle();
            }
        }

        /**
         * 扩展方法
         */
        protected abstract void doHandle();
    }

    public static class HandlerA extends Handler {
        @Override
        protected void doHandle() {
            System.out.println("Handler A --------- 4");
        }
    }

    public static class HandlerB extends Handler {
        @Override
        protected void doHandle() {
            System.out.println("Handler B --------- 4");
        }
    }

    public static class HandlerChain {
        private Handler head = null;
        private Handler tail = null;

        public void addHandler(Handler handler) {
            handler.setSuccessor(null);

            if (head == null) {
                head = handler;
                tail = handler;
                return;
            }

            tail.setSuccessor(handler);
            tail = handler;
        }

        public void handle() {
            if (head != null) {
                head.handle();
            }
        }
    }
}


// --------------------------------- 5、应用：过滤/替换 敏感词 --------------------------------------------


class Handler_Demo__WordFilter {

    public interface SensitiveWordFilter {
        boolean doFilter(Content content);
    }


    static class AdsWordFilter implements SensitiveWordFilter {
        @Override
        public boolean doFilter(Content content) {
            boolean legal = true;
            System.out.println("-----------不要发广告");
            return legal;
        }
    }

    static class SexyWordFilter implements SensitiveWordFilter {
        @Override
        public boolean doFilter(Content content) {
            boolean legal = true;
            System.out.println("-----------不要开车");
            return legal;
        }
    }

    static class PoliticalWordFilter implements SensitiveWordFilter {
        @Override
        public boolean doFilter(Content content) {
            boolean legal = true;
            System.out.println("-----------不要言政");
            return legal;
        }
    }


    static class SensitiveWordFilterChain {
        private List<SensitiveWordFilter> filters = new ArrayList<>();

        public void addFilter(SensitiveWordFilter filter) {
            this.filters.add(filter);
        }

        // return true if content doesn't contain sensitive words.
        public boolean filter(Content content) {
            for (SensitiveWordFilter filter : filters) {
                if (!filter.doFilter(content)) {
                    return false;
                }
            }
            return true;
        }
    }


    @Data
    @AllArgsConstructor
    static class Content {
        Long userId;
        String content;
    }
}
