package com.bebopze.jdk.concurrent.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author bebopze
 * @date 2019/6/28
 */
public class NettyReactorDemo {

    public static void main(String[] args) {

        test_1();


        // 用 Netty 实现 Echo 程序服务端
        test_2();


        test_3();
    }

    private static void test_1() {

//        void Reactor::handle_events(){
//            // 通过同步事件多路选择器提供的
//            //select() 方法监听网络事件
//            select(handlers);
//            // 处理网络事件
//            for(h in handlers){
//                h.handle_event();
//            }
//        }
//        // 在主程序中启动事件循环
//        while (true) {
//            handle_events();
//        }

    }


    /**
     * 用 Netty 实现 Echo 程序服务端
     */
    private static void test_2() {

        // 事件处理器
        final EchoServerHandler serverHandler = new EchoServerHandler();

        //boss 线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        //worker 线程组        默认情况下，Netty会创建 2*CPU核数 个 EventLoop
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            // bind 服务端端口
            ChannelFuture f = b.bind(9090).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            // 终止工作线程组
            workerGroup.shutdownGracefully();
            // 终止 boss 线程组
            bossGroup.shutdownGracefully();
        }


    }

    private static void test_3() {
    }


    // socket 连接处理器
    static class EchoServerHandler extends ChannelInboundHandlerAdapter {

        // 处理读事件
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ctx.write(msg);
        }

        // 处理读完成事件
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        // 处理异常事件
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
