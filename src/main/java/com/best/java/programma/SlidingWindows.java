package com.best.java.programma;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: xjxu3
 * @Date: 2020/2/3 9:51
 * @Description: 无重复字符的最长子串，如abcabcbb返回3，pwwkew是3，pwke是子序列不是子串
 * 使用滑动窗口算法，窗口开始位置start，结束位置end，窗口从最开始的位置开始
 * 将每个字符放入map中，key是字符，value是下一个不重复字符的起始位置（就是发现map里有这个字符了，
 * 说明重复了，开始窗口下一个位置的计算，窗口的start为到这里）
 */
public class SlidingWindows {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		Map<Character, Integer> map = new HashMap<>();
		int max = 0;
		for (int start = 0, end = 0; end < s.length(); end++) {
			// 当前字符
			char currentCharacter = s.charAt(end);
			//如果在map里，说明遇到重复了，重新计算start的位置
			if (map.containsKey(currentCharacter)) {
				start = Math.max(map.get(currentCharacter), start);
			}
			// 计算当前最大长度
			max = Math.max(max, end - start + 1);
			// 放入当前字符或者覆盖当前字符，value是下一个不重复的起始，用于参与start的计算
			map.put(currentCharacter, end + 1);
		}
		System.out.println(max);
	}
}
