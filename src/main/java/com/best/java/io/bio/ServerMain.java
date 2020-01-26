package com.best.java.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 服务端监听，单线程下，一次只能监听一个客户端，当这个客户端没有发送消息时，服务端阻塞在read方法，无法响应新的客户端的连接
 * 所以有了注释部分的多线程版本，也就是一个连接使用一个线程去处理
 */
public class ServerMain {
		public static void main(String... args) {
		byte[] buffer = new byte[1024];
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			System.out.println("服务器已启动并监听8080端口");
			while (true) {
				System.out.println();
				System.out.println("服务器正在等待连接...");
				Socket socket = serverSocket.accept();
				System.out.println("服务器已接收到连接请求...");
				System.out.println();
				System.out.println("服务器正在等待数据...");
				socket.getInputStream().read(buffer);
				System.out.println("服务器已经接收到数据");
				System.out.println();
				String content = new String(buffer);
				System.out.println("接收到的数据:" + content);
				System.out.println("服务器将要返回数据");
				String message = null;
				Scanner sc = new Scanner(System.in);
				message = sc.next();
				socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		byte[] buffer = new byte[1024];
//		try {
//			ServerSocket serverSocket = new ServerSocket(8080);
//			System.out.println("服务器已启动并监听8080端口");
//			while (true) {
//				System.out.println();
//				System.out.println("服务器正在等待连接...");
//				Socket socket = serverSocket.accept();
//				new Thread(() -> {
//					System.out.println("服务器已接收到连接请求...");
//					System.out.println();
//					System.out.println("服务器正在等待数据...");
//					try {
//						socket.getInputStream().read(buffer);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("服务器已经接收到数据");
//					System.out.println();
//					String content = new String(buffer);
//					System.out.println("接收到的数据:" + content);
//				}).start();
//
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

