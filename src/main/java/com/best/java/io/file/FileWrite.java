package com.best.java.io.file;

import com.best.java.util.BytesWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 9:23
 * @Description:
 */
public class FileWrite {
	public static void main(String[] args) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("C:\\Users\\prometheus\\Desktop\\2.txt"));
			FileChannel fc = fos.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(12);
			String content = "世界需要工程师\n";
			String content2 = "世界真的需要工程师";
			BytesWrapper bytesWrapper = new BytesWrapper(content.getBytes(StandardCharsets.UTF_8));
			bytesWrapper.append(content2.getBytes(StandardCharsets.UTF_8));

			// 开始写
			int length = byteBuffer.capacity();
			// 要写出的内容
			byte[] results = bytesWrapper.builder();
			// 偏移量
			int offset = 0;
			// 剩余要写出的内容的长度
			int remainLength = results.length;
			while (remainLength > 0) {
				// 剩余的长度比byteBuffer大，则写出byteBuffer的长度，否则写出剩余长度
				System.out.println("remainLength = " + remainLength);
				// 从results数组的offset开始，截取Math.min(remainLength, length)的长度，放入byteBuffer
				byteBuffer.put(results,offset, Math.min(remainLength, length));
				byteBuffer.flip();
				// 判断posion 和limit之间有没有元素
				while (byteBuffer.hasRemaining()) {
					fc.write(byteBuffer);
				}
				byteBuffer.clear();
				// 偏移量步进
				offset = offset + length;
				remainLength = remainLength - length;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} try {
			if (fos != null) {
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
