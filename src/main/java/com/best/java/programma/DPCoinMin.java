package com.best.java.programma;

/**
 * @Author: xjxu3
 * @Date: 2020/5/25 21:42
 * @Description: 你有三种硬币，分别面值2元，5元和7元，每种硬币都有足够多。买一本书需要27元。
 * 如何用最少的硬币组合正好付清，不需要对方找钱？
 *
 *
 */
public class DPCoinMin {
	public static void main(String[] args) {
		int[] A = new int[] {2,5,7};
		int m = 27;
		System.out.println(coinChange(A,m));
	}

	public static int coinChange(int[] A, int M) {
		// A = [2,5,7]
		// M = 27
		int[] f = new int[M + 1];
		int n = A.length; // 硬币的种类
		// 初始化, 0个硬币
		f[0] = 0;
		// f[1], f[2], ... , f[27] = Integer.MAX_VALUE
		for (int i = 1; i <= M; i++) {
			f[i] = Integer.MAX_VALUE;
		}
		// 目的是求出f[M]，但分成子问题后，每个f都要计算
		// 所以可以看成书的价格是1到27的每个情况
		for (int i = 1; i <= M; i++) {

			// f[i] = min{f[i-A[0]]+1, ... , f[i-A[n-1]]+1}
			// 从小到大开始算，可以用纸笔写以下f[0],f[1],f[2]等等等的情况，推算处理
			// 使用第j个硬币 A[j]
			for (int j = 0; j < n; ++j) {
				if (i >= A[j] && f[i - A[j]] != Integer.MAX_VALUE) {
					// 3种硬币下得出的f[i]，取最小的
					// 如f[7] = min{f[5]+1,f[2]+1,f[0]+1}，对应i等于7，A[j]为2，5，7三种情况
					f[i] = Math.min(f[i - A[j]] + 1, f[i]);
				}
			}
		}
		if (f[M] == Integer.MAX_VALUE) {
			return -1;
		}
		return f[M];
	}
}
