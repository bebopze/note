package com.bebopze.jdk.io;

import lombok.Cleanup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

///**
// *  NIO      -->  @see /note/docs/NIO与Netty编程-课程讲义.pdf
// *
// *   浅析Linux中的零拷贝技术 -->  https://www.jianshu.com/p/fad3339e3448
// *
// *
// *  NIO -->  IO多路复用       // 轮询获取 IO就绪（非阻塞）    +      同步调用 read/write
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


        test_NIO_SC();
    }


    /**
     * NIO 本质上还是 同步的                        - https://blog.csdn.net/qq_26198963/article/details/83832566
     *
     * @throws IOException
     */
    private static void test_NIO() throws IOException {


        // socket -> 套接字


        // ----------------------------------------------------------------


        // TCP协议的 网络通信 传统实现方式 是通过
        //
        //      套接字编程 Socket 和 ServerSocket


        // NIO实现 TCP网络通信 需要用到 Channel 接口的两个实现类：
        //
        //      SocketChannel 和 ServerSocketChannel


        // ------------------------选择器（Selector）------------------------
        //
        //      想要实现 非阻塞的IO，必须要先弄懂 选择器
        //
        //  Selector 抽象类
        //
        //      可通过调用此类的 open() 创建选择器
        //      该方法将 使用系统的默认选择器提供者 创建新的选择器


        // 1个 selector   ->   N个 channel
        Selector selector = Selector.open();


        // ------------------------套接字 通道（Socket Channel）------------------------

        // 将 通道设置为非阻塞 之后，需要将 通道注册到选择器 中，注册的同时需要 指定一个选择键的类型（SelectionKey）


        // 选择键（SelectionKey）可以认为是一种 标记  ===>  标记通道 的 类型和状态
        //
        //      OP_ACCEPT：   用于套接字  接受操作  的操作集位    ->     isAcceptable()     测试此键的通道 是否已准备好 接受新的套接字连接
        //      OP_CONNECT：  用于套接字  连接操作  的操作集位    ->     isConnectable()	测试此键的通道 是否已完成   其套接字连接操作
        //      OP_READ：     用于       读取操作  的操作集位    ->     isReadable()	    测试此键的通道 是否已准备好 进行读取
        //      OP_WRITE：    用于       写入操作  的操作集位    ->     isWritable()	    测试此键的通道 是否已准备好 进行写入


        ServerSocketChannel channel_accept = ServerSocketChannel.open();
        // 非阻塞模式
        channel_accept.configureBlocking(false);
        channel_accept.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocketChannel channel_connect = ServerSocketChannel.open();
        channel_connect.configureBlocking(false);
        channel_connect.register(selector, SelectionKey.OP_CONNECT);

        ServerSocketChannel channel_read = ServerSocketChannel.open();
        channel_read.configureBlocking(false);
        channel_read.register(selector, SelectionKey.OP_READ);

        ServerSocketChannel channel_write = ServerSocketChannel.open();
        channel_write.configureBlocking(false);
        channel_write.register(selector, SelectionKey.OP_WRITE);


        // 不断获取 就绪的事件，所以是死循环
        while (true) {

            // 判断是否有就绪的事件
            int readyChannels = selector.select();
            // IO操作准备就绪的通道  大于0                     就绪  ===>  selector.select() > 0
            if (readyChannels == 0) {
                continue;
            }

            // 获取就绪的事件
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();

                // 处理对应的事件
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel

                    // 测试此键的通道 是否已准备好 接受新的套接字连接

                } else if (key.isConnectable()) {
                    // a connection was established with a remote server

                    // 测试此键的通道 是否已完成   其套接字连接操作

                } else if (key.isReadable()) {
                    // a channel is ready for reading

                    // ...当前线程 同步调用 read()


                } else if (key.isWritable()) {
                    // a channel is ready for writing

                    // ...当前线程 同步调用 write()

                }

                keyIterator.remove();
            }
        }
    }


    // -----------------------------------------------------------------------------------------------------------------


    private static void test_NIO_SC() throws IOException {

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

            // 1. serverSelector 负责轮询 是否有新的连接

            // 服务端监测到新的连接之后，不再创建一个新的线程，
            // 而是直接将新连接绑定到clientSelector上，这样就不用 IO 模型中 1w 个 while 循环在死等
            Selector serverSelector = Selector.open();


            // 2. clientSelector 负责轮询连接 是否有数据可读
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

                } catch (IOException e) {
                    e.fillInStackTrace();
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
