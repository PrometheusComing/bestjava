package com.best.java.io.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: xjxu3
 * @Date: 2020/1/26 20:32
 * @Description:
 */
public class ClientSocket2 {
	public static void main(String[] args) {
		try {
			byte[] buffer = new byte[1024];

			// 建立连接
			Socket socket = new Socket("127.0.0.1",8080);
			String message = null;
			Scanner sc = new Scanner(System.in);
			message = sc.next();
			socket.getOutputStream().write(message.getBytes());
			System.out.println("等待服务端返回消息");
			socket.getInputStream().read(buffer);
			String content = new String(buffer);
			System.out.println("接收到服务器数据:" + content);
			socket.close();
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
