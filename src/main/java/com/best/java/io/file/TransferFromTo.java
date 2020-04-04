package com.best.java.io.file;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 17:50
 * @Description:
 *
 * 支持在已有数据后面直接追加。sendfile的零拷贝方式
 */
public class TransferFromTo {
	public static void main(String[] args) {
		Path pathFrom = Paths.get("C:\\Users\\prometheus\\Desktop\\1.txt");
		Path pathTo = Paths.get("C:\\Users\\prometheus\\Desktop\\2.txt");
		try {
			FileChannel fileChanneF = FileChannel.open(pathFrom, StandardOpenOption.READ);
			FileChannel fileChanneT = FileChannel.open(pathTo, StandardOpenOption.WRITE);
			// 两种方式都可以
//			fileChanneF.transferTo(0, fileChanneF.size(), fileChanneT);
			fileChanneT.transferFrom(fileChanneF, fileChanneT.size(),fileChanneF.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
