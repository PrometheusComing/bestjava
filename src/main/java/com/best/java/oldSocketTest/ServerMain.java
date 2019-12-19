package com.best.java.oldSocketTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public static void main(String...args) {
		// 服务端套接字
		ServerSocket serverSocket = null;
		// 接收连接过来的客户端socket引用
		Socket socket =null;
		while(true) {
			try {
			// 绑定服务端所在机器端口
				serverSocket = new ServerSocket(18010);
				socket = serverSocket.accept();
				System.out.println("获取到了客户端-：" + socket.getInetAddress() + "，启动任务。。。");
				new Thread(new HandleMesg(socket)).start();
		} catch (IOException e) {
			e.printStackTrace();
		} }
//		finally {
//			try {
//				if (serverSocket != null) {
//					serverSocket.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
}
