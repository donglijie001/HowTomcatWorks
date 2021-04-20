package com.dlj.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/20/21 2:23 PM
 * 创建ServerSocket，接收请求，并创建socket
 */
public class HttpConnector implements Runnable{
    // 是否停止服务端ServerSocket
    boolean stopped;
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    public void run() {
        System.out.println("ServerSocket run!");
        ServerSocket serverSocket=null;
        // 监听的端口号
        int port=8080;
        try {
            serverSocket=new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建ServerSocket失败！");
            System.exit(1);
        }
        while (!stopped){
            // 接收请求创建socket
            Socket socket=null;
            try {
                socket= serverSocket.accept();

            } catch (IOException e) {
                System.out.println("创建 socket 失败！");
                //即使出现异常也不停止服务器
                continue;
            }
            // 将创建的socket交给HttpProcessor去处理
            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);


        }
    }
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
