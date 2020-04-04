package com.best.java.io.file;

import com.best.java.util.BytesUtil;
import com.best.java.util.BytesWrapper;

import java.io.File;
import java.io.FileInputStream;
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
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			String content = "世界需要工程师\n";
			String content2 = "世界真的需要工程师";
			BytesWrapper bytesWrapper = new BytesWrapper(content.getBytes(StandardCharsets.UTF_8));
			bytesWrapper.append(content2.getBytes(StandardCharsets.UTF_8));
			byteBuffer.put(bytesWrapper.builder());

			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				fc.write(byteBuffer);
			}
			byteBuffer.clear();//用完后把缓冲区还原，便于后续代码使用
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
