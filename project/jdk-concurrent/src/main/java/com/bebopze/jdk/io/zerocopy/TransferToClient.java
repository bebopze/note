package com.bebopze.jdk.io.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

import static com.bebopze.jdk.patterndesign._07_Decorator.getNoteAbsolutePath;

/**
 * 零拷贝  - Client                    // FileChannel  ->  transferTo()
 *
 * @author bebopze
 * @date 2020/09/15
 */
public class TransferToClient {


    public static void main(String[] args) throws IOException {

        TransferToClient transferToClient = new TransferToClient();

        transferToClient.testSendFile();
    }


    public void testSendFile() throws IOException {

        String host = "localhost";
        int port = 9026;

        SocketAddress sad = new InetSocketAddress(host, port);

        SocketChannel sc = SocketChannel.open();
        sc.connect(sad);
        sc.configureBlocking(true);


        String fname = getNoteAbsolutePath() + "/text/设计模式.txt";

        long fsize = 183678375L;
        long sendzise = 4094;

        // FileProposerExample.stuffFile(fname, fsize);
        FileChannel fc = new FileInputStream(fname).getChannel();
        long start = System.currentTimeMillis();
        long nsent = 0, curnset = 0;
        curnset = fc.transferTo(0, fsize, sc);

        System.out.println("total bytes transferred--" + curnset + " and time taken in MS--" + (System.currentTimeMillis() - start));
        //fc.close();
    }

}
