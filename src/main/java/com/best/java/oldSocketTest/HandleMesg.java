package com.best.java.oldSocketTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleMesg implements Runnable{

	private Socket socketClient;

	HandleMesg(Socket socketClient) {
		this.socketClient = socketClient;
	}

	@Override
	public void run() {
		BufferedReader bf = null;
		PrintWriter pw = null;
		try {
			bf = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			pw = new PrintWriter(socketClient.getOutputStream(),true);
			String str = null;
			while ((str = bf.readLine()) != null) {
				System.out.println("收到客户端消息：" + str);
				System.out.println("处理中。。。");
				pw.println("这是你要的结果");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bf != null) {
					bf.close();
				}
				if (pw != null) {
					pw.close();
				}
//				if (socketClient != null) {
//					socketClient.close();
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
