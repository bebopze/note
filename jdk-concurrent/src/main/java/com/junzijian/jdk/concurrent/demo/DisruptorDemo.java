package com.junzijian.jdk.concurrent.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * 4.3 案例分析（三）：高性能队列Disruptor
 * <p>
 * <p>
 * Disruptor - 干扰器
 *
 * @author liuzhe
 * @date 2019/6/28
 */
public class DisruptorDemo {


    public static void main(String[] args) throws InterruptedException {

        // Disruptor 使用范例
        test_1();

        test_2();

        test_3();

        test_4();

        test_5();
    }


    /**
     * 下⾯的代码出⾃官⽅示例，我略做了⼀些修改，
     * 相较⽽⾔，Disruptor 的使⽤⽐Java SDK 提供BlockingQueue 要复杂⼀些，但是总体思路还是⼀致的，
     * <p>
     * 其⼤致情况如下：
     * 在 Disruptor 中，⽣产者⽣产的对象（也就是消费者消费的对象）称为	Event，使⽤ Disruptor
     * 必须⾃定义 Event，例如示例代码的⾃定义 Event 是 LongEvent；
     * <p>
     * 构建 Disruptor 对象除了要指定队列⼤⼩外，还需要传⼊⼀个EventFactory，
     * 示例代码中传⼊的是LongEvent::new；
     * <p>
     * 消费 Disruptor 中的 Event 需要通过 handleEventsWith() ⽅法注册⼀个事件处理器，
     * 发布 Event 则需要通过 publishEvent() ⽅法。
     *
     * @throws InterruptedException
     */
    private static void test_1() throws InterruptedException {

        // 指定 RingBuffer 大小,
        // 必须是 2 的 N 次方
        int bufferSize = 1024;

        // 构建 Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(
                LongEvent::new,
                bufferSize,
                DaemonThreadFactory.INSTANCE);

        // 注册事件处理器
        disruptor.handleEventsWith(
                (event, sequence, endOfBatch) ->
                        System.out.println("Event: " + event));

        // 启动 Disruptor
        disruptor.start();

        // 获取 RingBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 生产 Event
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);


        for (long l = 0; true; l++) {

            byteBuffer.putLong(0, l);

            // 生产者生产消息
            ringBuffer.publishEvent(
                    (event, sequence, buffer) -> event.set(buffer.getLong(0)),
                    byteBuffer
            );

            Thread.sleep(1000);
        }


    }

    private static void test_2() {
    }

    private static void test_3() {
    }

    private static void test_4() {
    }

    private static void test_5() {


    }


    // 自定义 Event
    static class LongEvent {

        private long value;

        public void set(long value) {
            this.value = value;
        }
    }
}
