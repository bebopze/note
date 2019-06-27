package com.junzijian.jdk.concurrent.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * 4.3 案例分析（三）：高性能队列Disruptor
 *
 * @author liuzhe
 * @date 2019/6/28
 */
public class DisruptorDemo {


    public static void main(String[] args) throws InterruptedException {

        test_1();

        test_2();

        test_3();

        test_4();

        test_5();
    }


    private static void test_1() throws InterruptedException {

        // 自定义 Event
        class LongEvent {

            private long value;

            public void set(long value) {
                this.value = value;
            }
        }

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
                        System.out.println("E: " + event));

        // 启动 Disruptor
        disruptor.start();

        // 获取 RingBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        // 生产 Event
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            // 生产者生产消息
            ringBuffer.publishEvent(
                    (event, sequence, buffer) -> event.set(buffer.getLong(0)),
                    bb
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


}
