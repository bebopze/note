package com.junzijian.jdk.io;

import lombok.Cleanup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

///**
// *  NIO      -->  @see /note/docs/NIO与Netty编程-课程讲义.pdf
// *
// *   浅析Linux中的零拷贝技术 -->  https://www.jianshu.com/p/fad3339e3448
// *
// *  Java中的 NIO 是 New IO 的意思   -->  其实是 NIO（synchronous Non blocking IO） 加上 IO多路复用技术
// *
// *
// * https://juejin.im/post/5dbba5df6fb9a0204a08ae55
// * https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/BIO-NIO-AIO.md
// * https://www.iteye.com/blog/qindongliang-2018539
// *
// * @author bebopze
// * @date 2020/6/16
// */
public class NIO {


    public static void main(String[] args) throws IOException {

        test_NIO();
    }


    private static void test_NIO() throws IOException {

        NIOServer.main(null);

        IOClient.main(null);
    }


    /**
     * Client
     */
    static class IOClient {

        public static void main(String[] args) {

            System.out.println("IOClient start..........");


            // TODO 创建多个线程，模拟多个客户端连接服务端

            new Thread(() -> {

                try {

                    @Cleanup
                    Socket socket = new Socket("127.0.0.1", 3333);

                    while (true) {

                        try {
                            socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                            Thread.sleep(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }

    }


    /**
     * NIO Server   改造后的服务端
     */
    static class NIOServer {


        public static void main(String[] args) throws IOException {

            // 1. serverSelector负责轮询是否有新的连接

            // 服务端监测到新的连接之后，不再创建一个新的线程，
            // 而是直接将新连接绑定到clientSelector上，这样就不用 IO 模型中 1w 个 while 循环在死等
            Selector serverSelector = Selector.open();


            // 2. clientSelector负责轮询连接是否有数据可读
            Selector clientSelector = Selector.open();


            new Thread(() -> {

                try {

                    // 对应IO编程中服务端启动
                    ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                    listenerChannel.socket().bind(new InetSocketAddress(3333));
                    listenerChannel.configureBlocking(false);
                    listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);


                    while (true) {

                        // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                        if (serverSelector.select(1) > 0) {

                            Set<SelectionKey> set = serverSelector.selectedKeys();
                            Iterator<SelectionKey> keyIterator = set.iterator();

                            while (keyIterator.hasNext()) {

                                SelectionKey key = keyIterator.next();

                                if (key.isAcceptable()) {

                                    try {

                                        // (1) 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
                                        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                        clientChannel.configureBlocking(false);
                                        clientChannel.register(clientSelector, SelectionKey.OP_READ);

                                    } finally {

                                        keyIterator.remove();
                                    }

                                }

                            }
                        }
                    }

                } catch (IOException ignored) {
                }

            }).start();


            new Thread(() -> {

                try {

                    while (true) {

                        // (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                        if (clientSelector.select(1) > 0) {
                            Set<SelectionKey> set = clientSelector.selectedKeys();
                            Iterator<SelectionKey> keyIterator = set.iterator();

                            while (keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();

                                if (key.isReadable()) {

                                    try {

                                        SocketChannel clientChannel = (SocketChannel) key.channel();
                                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                        // (3) 面向 Buffer
                                        clientChannel.read(byteBuffer);
                                        byteBuffer.flip();

                                        System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());

                                    } finally {
                                        keyIterator.remove();
                                        key.interestOps(SelectionKey.OP_READ);
                                    }
                                }

                            }
                        }
                    }

                } catch (IOException ignored) {
                }

            }).start();

        }
    }
}
