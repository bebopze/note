package com.bebopze.jdk.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.concurrent.*;

/**
 * AIO      ->   Async IO
 * -
 * -
 * -   AIO   -->  IO多路复用  + 异步read/write    // 轮询获取 IO就绪（非阻塞）    +      异步调用 read/write  （交给 OS 异步完成）
 *
 * @author bebopze
 * @date 2020/6/16
 */
public class AIO {

    private static final Charset charset = Charset.forName("US-ASCII");

    private static final CharsetEncoder encoder = charset.newEncoder();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws Exception {


        // channel Group
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
        //
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress("0.0.0.0", 8013));

        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {

                // 接受下一个连接
                server.accept(null, this);

                try {
                    String now = new Date().toString();
                    ByteBuffer buffer = encoder.encode(CharBuffer.wrap(now + "\r\n"));

//                    //callback or
//                    result.write(buffer, null, new CompletionHandler<Integer,Void>(){
//                        @Override
//                        public void completed(Integer result, Void attachment) {
//
//                        }
//
//                        @Override
//                        public void failed(Throwable exc, Void attachment) {
//
//                        }
//                    });


                    // AIO操作，异步执行
                    Future<Integer> f = result.write(buffer);

                    // 获取别人的执行结果
                    f.get();

                    System.out.println("sent to client: " + now);

                    result.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e, Void attachment) {
                e.printStackTrace();
            }
        });

        channelGroup.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

}
