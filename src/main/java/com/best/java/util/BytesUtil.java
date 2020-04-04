package com.best.java.util;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 10:11
 * @Description: 字节操作工具类
 */
public class BytesUtil {

	// 组合两个字节数组
	public static byte[] append(byte[] a, byte[] b) {
		if (a == null || a.length == 0) {
			return b;
		}
		if (b == null || b.length == 0) {
			return a;
		}
		byte[] result = new byte[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	// 字节数组加单个字节
	public static byte[] append(byte[] a, byte b) {
		if (a == null || a.length == 0) {
			return new byte[]{b};
		}
		byte[] result = new byte[a.length + 1];
		System.arraycopy(a, 0, result, 0, a.length);
		result[a.length] = b;
		return result;
	}
}
