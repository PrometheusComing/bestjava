package com.best.java.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: xjxu3
 * @Date: 2020/4/26 22:46
 * @Description:
 */
public class CountTask extends RecursiveTask<Integer> {
	// 阈值
	private static final int THRESHOLD = 2;
	private static final long serialVersionUID = -7932134511510113752L;
	private int start;
	private int end;

	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		// 如果任务足够小就计算任务
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 如果任务大于阈值，就分裂成两个子任务计算
			int middle = (start + end) / 2;
			CountTask leftTask = new CountTask(start, middle);
			CountTask rightTask = new CountTask(middle + 1, end);
			// 执行子任务
			leftTask.fork();
			rightTask.fork();
			// 等待子任务执行完，并得到其结果
			int leftResult = leftTask.join();
			int rightResult = rightTask.join();
			// 合并子任务
			sum = leftResult + rightResult;
		}
		return sum;
	}

	public static void main(String[] args) throws Exception {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		// 生成一个计算任务，负责计算1+2+3+4...100
		CountTask task = new CountTask(1, 100);
		// 执行一个任务
		Future<Integer> result = forkJoinPool.submit(task);
		System.out.println(result.get());
	}
}
