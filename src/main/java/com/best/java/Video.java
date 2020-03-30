package com.best.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

/**
 * @Author: xjxu3
 * @Date: 2020/3/29 11:36
 * @Description:
 */
public class Video {


	//https://cache1.361lu.com/video/20181225/bca081bd3303e4f8f2100f94d5f20334/GQ/
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String uuid = UUID.randomUUID().toString();
		String urlString = scanner.next();
		String suffix = "video";
		String num = "000";
//		for (int i = 0;;i ++ ) {
			int number = Integer.parseInt(num);
			num = String.format("%03d", number);
			String target = "video" + num + ".ts";

			String urlStr = urlString + "/" + target;
			urlStr = "http://jeffe.cs.illinois.edu/teaching/algorithms/book/Algorithms-JeffE.pdf";
			URL url = null;
			try {
				url = new URL(urlStr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			// 设置超时间为100秒
//			conn.setConnectTimeout(100 * 1000);
//			// 防止屏蔽程序抓取而返回403错误
//			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				// 得到输入流
				InputStream inputStream = conn.getInputStream();
				// 获取自己数组
				byte[] buffer = new byte[1024];
				int len = 0;
				// 文件保存位置
				File saveDir = new File("C:\\Users\\prometheus\\Desktop\\game\\catch");
				if (!saveDir.exists()) {
					saveDir.mkdirs();
				}
				System.out.println("下载 " + target + "...");
//				File file = new File(saveDir + File.separator + uuid + target);
				File file = new File(saveDir + File.separator + "JeffE.pdf");
				FileOutputStream fos = new FileOutputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while ((len = inputStream.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				bos.close();
				if (fos != null) {
					fos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


			number = Integer.parseInt(num) + 1;
			num = String.format("%03d", number);

//		}

	}
}
