package com.bebopze.jdk.io.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 零拷贝  - Server
 *
 * @author bebopze
 * @date 2020/09/15
 */
public class TransferToServer {


    public static void main(String[] args) {
        TransferToServer dns = new TransferToServer();
        dns.mySetup();
        dns.readData();
    }


    // -----------------------------------------------------------------------------------------------------------------


    ServerSocketChannel listener = null;

    protected void mySetup() {

        InetSocketAddress listenAddr = new InetSocketAddress(9026);

        try {
            listener = ServerSocketChannel.open();
            ServerSocket ss = listener.socket();
            ss.setReuseAddress(true);
            ss.bind(listenAddr);

            System.out.println("Listening on port : " + listenAddr.toString());

        } catch (IOException e) {

            System.out.println("Failed to bind, is port : " + listenAddr.toString()
                    + " already in use ? Error Msg : " + e.getMessage());

            e.printStackTrace();
        }
    }


    private void readData() {

        ByteBuffer dst = ByteBuffer.allocate(4096);

        try {

            while (true) {
                SocketChannel conn = listener.accept();
                System.out.println("Accepted : " + conn);
                conn.configureBlocking(true);

                int nRead = 0;
                while (nRead != -1) {
                    try {
                        nRead = conn.read(dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                        nRead = -1;
                    }
                    dst.rewind();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
