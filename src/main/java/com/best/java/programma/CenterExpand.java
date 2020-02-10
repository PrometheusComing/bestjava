package com.best.java.programma;

import java.util.Scanner;

/**
 * @Author: xjxu3
 * @Date: 2020/2/10 17:06
 * @Description: 中心拓展算法：最长回文字符串
 */
public class CenterExpand {
	// 使用中心扩展算法
	public static String getRevert(String s) {
		// 单个字符肯定是回文串，直接返回s
		if (s.length() < 2) {
			return s;
		}
		int maxLength = 0;
		int center = 0;
		for (int i = 0; i < s.length(); i++) {
			// 最长回文串长度为奇数
			int begin = centerExpand(s, i, i);
			// 最长回文串长度为偶数
			int end = centerExpand(s, i, i + 1);

			if (maxLength < Math.max(begin, end)) {
				// 以center为中心
				center = i;
				// 最长回文串长度
				maxLength = Math.max(begin, end);
			}
		}
		// 如果我们的回文串的长度为偶数，那么中心左边的长度会比右边的长度小1
		return s.substring(center - (maxLength - 1) / 2, center + maxLength / 2 + 1);
	}

	static int centerExpand(String s, int begin, int end) {
		// 记下左边
		int left = begin;
		// 记下右边
		int right = end;
		// 向左右两边拓展开，直到不相等
		while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}
		// 返回以begin,end为基准，同时向左向右扩展后能够得到的最长回文串长度
		return right - left - 1;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		System.out.println(getRevert(s));
	}
}
