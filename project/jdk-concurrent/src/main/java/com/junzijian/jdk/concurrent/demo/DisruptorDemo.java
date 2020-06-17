package com.junzijian.jdk.concurrent.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.lmax.disruptor.util.Util;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.LockSupport;

/**
 * 4.3 案例分析（三）：高性能队列Disruptor
 * <p>
 * <p>
 * Disruptor - 干扰器
 *
 * @author bebop
 * @date 2019/6/28
 */
public class DisruptorDemo {


    public static void main(String[] args) throws InterruptedException {

        // Disruptor 使用范例
        test_1();

        // Disruptor 内部的 RingBuffer 也是⽤数组实现
        test_2();

        // 缓存航 填充技术
        test_3();

        // Disruptor ⽣产者⼊队操作的 核⼼代码
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

    /**
     * 下⾯我们再看看 Disruptor 是如何处理的。
     * Disruptor 内部的 RingBuffer 也是⽤数组实现的，
     * 但是这个数组中的所有元素在初始化时是⼀次性全部创建的，
     * 所以这些元素的内存地址⼤概率是连续的，
     * 相关的代码如下所示。
     */
    private static void test_2() {

//        for (int i = 0; i < bufferSize; i++) {
//            // entries[] 就是 RingBuffer 内部的数组
//            // eventFactory 就是前面示例代码中传入的 LongEvent::new
//            entries[BUFFER_PAD + i] = eventFactory.newInstance();
//        }

    }

    /**
     * Disruptor 中很多对象，例如 RingBuffer、RingBuffer 内部的数组 都⽤到了这种 填充技术 来避免伪共享。
     */
    private static void test_3() {

        // 前：填充 56 字节
        class LhsPadding {
            long p1, p2, p3, p4, p5, p6, p7;
        }
        class Value extends LhsPadding {
            volatile long value;
        }

        // 后：填充 56 字节
        class RhsPadding extends Value {
            long p9, p10, p11, p12, p13, p14, p15;
        }
        class Sequence extends RhsPadding {
            // 省略实现
        }
    }

    /**
     * Disruptor ⽣产者⼊队操作的 核⼼代码
     * <p>
     * 看上去很复杂，其实逻辑很简单：
     * 如果没有⾜够的空余位置，就出让 CPU 使⽤权，然后重新计算；
     * 反之则⽤	CAS 设置⼊队索引。
     */
    private static void test_4() {

//        // 生产者获取 n 个写入位置
//        do {
//
//            //cursor 类似于入队索引，指的是上次生产到这里
//            current = cursor.get();
//
//            // 目标是在生产 n 个
//            next = current + n;
//
//            // 减掉一个循环
//            long wrapPoint = next - bufferSize;
//
//            // 获取上一次的最小消费位置
//            long cachedGatingSequence = gatingSequenceCache.get();
//
//            // 没有足够的空余位置
//            if (wrapPoint > cachedGatingSequence || cachedGatingSequence > current) {
//
//                // 重新计算所有消费者里面的最小值位置
//                long gatingSequence = Util.getMinimumSequence(gatingSequences, current);
//
//                // 仍然没有足够的空余位置，出让 CPU 使用权，重新执行下一循环
//                if (wrapPoint > gatingSequence) {
//                    LockSupport.parkNanos(1);
//                    continue;
//                }
//                // 从新设置上一次的最小消费位置
//                gatingSequenceCache.set(gatingSequence);
//
//            } else if (cursor.compareAndSet(current, next)) {
//
//                // 获取写入位置成功，跳出循环
//                break;
//            }
//
//        } while (true);

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
