package com.best.java.io.file;

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
 * @Date: 2020/4/4 10:56
 * @Description:
 *
 * 可以使用transferTo()和transferFrom()。底层是sendfile零拷贝技术
 * transferTo()：通过 FileChannel 把文件里面的源数据写入一个 WritableByteChannel 的目的通道。
 * transferFrom()：把一个源通道 ReadableByteChannel 中的数据读取到当前 FileChannel 的文件里面。
 */
public class FileReadWrite {
	public static void main(String[] args) {
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(new File("C:\\Users\\prometheus\\Desktop\\1.txt"));
			// 第二个参数为true，表示追加写
			fos = new FileOutputStream(new File("C:\\Users\\prometheus\\Desktop\\2.txt"),true);

			FileChannel ff = fis.getChannel();
			FileChannel ft = fos.getChannel();

			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

			// 先读取
			int readBytesSize = ff.read(byteBuffer);
			byte[] bytes;
			BytesWrapper bytesWrapper = new BytesWrapper();
			while (readBytesSize > -1) {
				bytes = new byte[readBytesSize];
				byteBuffer.flip();
				byteBuffer.get(bytes);
				bytesWrapper.append(bytes);
				readBytesSize = ff.read(byteBuffer);
			}
			byteBuffer.clear();//用完后把缓冲区还原，便于后续写操作继续使用它
			// 开始写
			byteBuffer.put(bytesWrapper.builder());
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				ft.write(byteBuffer);
			}
			System.out.println(new String(bytesWrapper.builder(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
