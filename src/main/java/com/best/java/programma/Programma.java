package com.best.java.programma;

import java.util.Scanner;

/**
 * @Author: xjxu3
 * @Date: 2019/4/26 11:54
 * @Description: 回文字符串
 */
public class Programma {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("输入字符串：");
		String string = scanner.next();
		int size = string.length();
		outer:
		// 外循环,i为当前串长度
		for (int i = size; i > 0; i--) {
			// 从最长串开始，内循环的次数
			int times = size - i + 1;
			// 内循环
			for (int j = 0; j < times; j++) {
				int start = j;
				int end = start + i;
				String here = string.substring(start, end);
				String result = check(here);
				if (result != null) {
					System.out.println("回文串长度：" + result.length());
					System.out.println("回文串：" + result);
					break outer;
				}
			}
		}
	}

	private static String check(String sourcce) {
		if (sourcce == null || "".equals(sourcce)) return null;
		char[] chars = sourcce.toCharArray();
		int length = chars.length;
		for (int i = 0; i < length / 2; i++) {
			if (chars[i] != chars[length - 1 - i]) {
				return null;
			}
		}
		return sourcce;
	}
}
