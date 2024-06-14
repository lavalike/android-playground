package com.android.playground.java.nio.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * TcpClient
 * Created by wangzhen on 2019/2/28.
 */
public class TcpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Socket Client服务已经启动");
        TcpClient client = new TcpClient();
//        client.bySocket();
        client.byChannel();
    }

    private void bySocket() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8081);
        OutputStream outputStream = socket.getOutputStream();
        String msg = "王震";
        outputStream.write(msg.getBytes());
        System.out.println("Client发送：" + msg);
        socket.close();
    }

    private void byChannel() throws IOException {
        //创建通道
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8081));
        //切换异步非阻塞
        channel.configureBlocking(false);
        //指定缓冲区大小
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        while (scanner.hasNext()) {
            System.out.println("请输入：");
            String next = scanner.next();
            allocate.put((new Date().toString() + "  -->  " + next).getBytes());
            //切换到读取模式
            allocate.flip();
            channel.write(allocate);
            allocate.clear();
        }
        channel.close();
    }
}
