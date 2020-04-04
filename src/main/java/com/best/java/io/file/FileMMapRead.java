package com.best.java.io.file;

import com.best.java.util.BytesUtil;
import com.best.java.util.BytesWrapper;
import sun.nio.ch.FileChannelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 9:23
 * @Description:
 */
public class FileMMapRead {
	public static void main(String[] args) {
		try {
			File file = new File("C:\\Users\\prometheus\\Desktop\\1.txt");
			FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
			// 映射的起始位置，及长度不要把模式设置成可写，那样结果是要读的文件的数据都被清空。包括上面的open方法也要设置为读
			MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 50, fc.size()-70);
			BytesWrapper bytesWrapper = new BytesWrapper();
			byte[] bytes;
			if (mappedByteBuffer != null) {
				bytes = new byte[(int) (fc.size()-70)];
//				mappedByteBuffer.position(20);
				mappedByteBuffer.get(bytes);
				bytesWrapper.append(bytes);
				System.out.println(new String(bytesWrapper.builder(), StandardCharsets.UTF_8));
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
