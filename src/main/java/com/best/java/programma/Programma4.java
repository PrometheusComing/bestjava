package com.best.java.programma;

/**
 * @Author: xjxu3
 * @Date: 2019/4/28 9:40
 * @Description: 算法
 */
public class Programma4 {
	public static void main(String[] args) {
		test();
	}
	public static void test() {
		int n = 4;
		int[][] num = new int[n][n];
		int count = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				num[i][j] = count++;
			}
		}
		output(num, 0, n - 1);
	}

	public static void output(int[][] num, int start, int end) {
		if (start > end || end < 0) return;
		for (int i = start; i <= end; i++) {
			System.out.println(num[start][i]);
		}
		for (int i = start + 1; i <= end; i++) {
			System.out.println(num[i][end]);
		}
		for (int i = end - 1; i >= start; i--) {
			System.out.println(num[end][i]);
		}
		for (int i = end - 1; i > start; i--) {
			System.out.println(num[i][start]);
		}
		output(num, start + 1, end - 1);
	}
}
