package com.best.java.oldSocketTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader bf = null;
		PrintWriter pw = null;
		try {
			socket = new Socket(InetAddress.getByName("192.168.3.140"),18010);
			pw = new PrintWriter(socket.getOutputStream());
			pw.println("我是客户端：" + socket.getInetAddress());
			pw.flush();
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("收到了服务器结果：");
			System.out.println(bf.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pw != null) {
					pw.close();
				}
				if(bf != null) {
					bf.close();
				}
				// 客户端不发消息就要关闭，否则会报错connect reset
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
