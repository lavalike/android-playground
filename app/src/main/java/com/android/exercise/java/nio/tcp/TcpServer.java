package com.android.exercise.java.nio.tcp;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TcpServer
 * Created by wangzhen on 2019/2/28.
 */
public class TcpServer {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws IOException {
        System.out.println("Socket Server服务已经启动");
        TcpServer tcpServer = new TcpServer();
//        tcpServer.byExecutors();
//        tcpServer.byServerSocket();
        tcpServer.bySocketChannel();
    }

    private void byExecutors() throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8081);
        try {
            while (true) {
                final Socket accept = serverSocket.accept();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = accept.getInputStream();
                            byte[] bytes = new byte[1024];
                            int read = inputStream.read(bytes);
                            String s = new String(bytes, 0, read);
                            System.out.println("Server收到内容：" + s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }


    private void byServerSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        try {
            while (true) {
                final Socket accept = serverSocket.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = accept.getInputStream();
                            byte[] bytes = new byte[1024];
                            int read = inputStream.read(bytes);
                            String s = new String(bytes, 0, read);
                            System.out.println("Server收到内容：" + s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bySocketChannel() throws IOException {
        //创建通道
        ServerSocketChannel channel = ServerSocketChannel.open();
        //切换异步非阻塞
        channel.configureBlocking(false);
        //绑定连接
        channel.bind(new InetSocketAddress(8081));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器，并且指定监听接受事件
        channel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询式获取选择"已经准备就绪"事件
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //获取准备就绪的事件
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    //若"准备就绪"，获取客户端连接
                    SocketChannel accept = channel.accept();
                    //设置阻塞模式
                    accept.configureBlocking(false);
                    //将通道注册到选择器上
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(allocate)) > 0) {
                        allocate.flip();
                        System.out.println("Server收到内容：" + new String(allocate.array(), 0, len));
                        allocate.clear();
                    }
                }
                iterator.remove();
            }
        }
        channel.close();
    }
}
