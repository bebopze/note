package com.bebopze.jdk.io;

import lombok.Cleanup;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * BIO  -->  https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/BIO-NIO-AIO.md
 *
 * @author bebopze
 * @date 2020/6/16
 */
public class BIO {


    public static void main(String[] args) throws IOException {

        test_BIO();
    }


    private static void test_BIO() throws IOException {

        IOClient.main(null);

        IOServer.main(null);
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
     * Server
     */
    static class IOServer {

        public static void main(String[] args) throws IOException {

            System.out.println("IOServer start..........");


            // TODO 服务端处理客户端连接请求

            @Cleanup
            ServerSocket serverSocket = new ServerSocket(3333);


            // 接收到客户端连接请求之后，
            // 为每个客户端 创建一个新的线程，进行链路处理

            new Thread(() -> {

                while (true) {

                    try {
                        // 阻塞方法获取新的连接
                        Socket socket = serverSocket.accept();


                        // 每一个新的连接都创建一个线程，负责读取数据
                        new_thread__4__read_socket(socket);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }

        /**
         * 每一个新的连接都创建一个线程，负责读取数据
         *
         * @param socket
         */
        private static void new_thread__4__read_socket(Socket socket) {

            new Thread(() -> {

                try {
                    int len;
                    byte[] data = new byte[1024];
                    InputStream inputStream = socket.getInputStream();
                    // 按字节流方式读取数据
                    while ((len = inputStream.read(data)) != -1) {
                        System.out.println(new String(data, 0, len));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }

    }


}

