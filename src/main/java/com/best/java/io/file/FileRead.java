package com.best.java.io.file;

import com.best.java.util.BytesUtil;
import com.best.java.util.BytesWrapper;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 9:23
 * @Description: byteBuffer看成一个缓冲数组，对于从channel读取数据，possion是下一个放入数据（从channel读）的位置，limit是能读到的最大位置，
 * 所以possion肯定小于等于limit
 * <p>
 * flip()这个方法做两件非常重要的事：
 * <p>
 * 1）它将 limit 设置为当前 position；
 * <p>
 * 2）它将 position 设置为 0。
 * <p>
 * Clear 做两种非常重要的事情：
 * <p>
 * 1）它将 limit 设置为与 capacity 相同；
 * <p>
 * 2）它设置 position 为 0。
 *
 * 从缓冲数组里取出字节，也就是byteBuffer.get（把数据从缓冲区拿出放到代码中的数组）和channel.write（把数据从缓冲区拿出放到channel）,
 * 两个方法也都会使position变化
 * 这两个方法只能获取缓冲数组中position到limit之间的数据，所以自己要明白当前position到limit之间的数据是否是自己需要的数据，如果不是，可以
 * 调用flip或其他方法调整。比如，capacity为12，position为0，limit为12的初始缓冲数组，调用byteBuffer.put 8个字节后，position变为 7，
 * 此时如果直接使用channel.write，写入到channel里的就只能是position 7到limit 12之间的字节，所以要先调用flip，将position变为0，limit变为
 * 7，那么再调用channel.write，写入到channel里的就是position 0到limit 7之间的正确字节了。
 *
 * 往缓冲数组里放入字节，也就是也就是byteBuffer.put（把数据从代码中的数组拿出放到缓冲区）和channel.read（把数据从channel拿出放到缓冲区）,
 * 两个方法也都会使position变化
 * 这两个方法只能写入缓冲数组中position到limit之间的数据，如果put方法放入超出范围的数据，就会BufferOverflowException
 */
public class FileRead {
	public static void main(String[] args) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File("C:\\Users\\prometheus\\Desktop\\1.txt"));
			FileChannel fc = fis.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(12);

			int readBytesSize = fc.read(byteBuffer);
			byte[] bytes;
			byte[] result = new byte[0];
			BytesWrapper bytesWrapper = new BytesWrapper();
			while (readBytesSize > -1) {
				System.out.println("readBytesSize = " + readBytesSize);
				bytes = new byte[readBytesSize];
				byteBuffer.flip();
				byteBuffer.get(bytes);
				result = BytesUtil.append(result, bytes);
				bytesWrapper.append(bytes);
				byteBuffer.clear();
				readBytesSize = fc.read(byteBuffer);
			}
			byteBuffer.clear();//用完后把缓冲区还原，便于后续代码使用
			System.out.println(new String(result, StandardCharsets.UTF_8));
			System.out.println(new String(bytesWrapper.builder(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
