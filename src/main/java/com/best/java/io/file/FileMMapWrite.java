package com.best.java.io.file;

import sun.nio.ch.FileChannelImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 11:15
 * @Description: fore();缓冲区是READ_WRITE模式下，此方法对缓冲区内容的修改强行写入文件
 *
 * 目前不知道怎么在已有数据文件基础上追加.目前发现模式为“读写”,只要映射了，已有数据文件就会被清除的
 * 可以尝试将if (mappedByteBuffer != null)都注释掉，再运行。会发现已有数据都清除了
 */
public class FileMMapWrite {

	private final static String CONTENT = "Zero copy implemented by MappedByteBuffer";

	public static void main(String[] args) {
		Path path = Paths.get("C:\\Users\\prometheus\\Desktop\\2.txt");
		byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);
		try {
			FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ,
					StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
//			 映射的起始位置，及长度
			MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0,
					bytes.length);
			if (mappedByteBuffer != null) {
				for (int i = 0; i < 2; i++) {
					mappedByteBuffer.put(bytes);
					mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, bytes.length,
							"追加的内容".getBytes(StandardCharsets.UTF_8).length);
					bytes = "追加的内容".getBytes(StandardCharsets.UTF_8);
				}
//				mappedByteBuffer.force();
			}
			// 在关闭资源时执行以下代码释放内存
			Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
			m.setAccessible(true);
			m.invoke(FileChannelImpl.class, mappedByteBuffer);
		} catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
