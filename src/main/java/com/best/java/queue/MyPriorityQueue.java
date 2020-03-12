package com.best.java.queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: xjxu3
 * @Date: 2020/3/12 12:37
 * @Description: 优先队列 （小根堆）
 */
public class MyPriorityQueue {
	private int[] array;
	private int size; // 当前队列长度

	public MyPriorityQueue(int initCap) {
		this.array = new int[initCap];
	}

	/**
	 * 出入数据
	 *
	 * @param value 要插入的数据,先插入到最后，然后慢慢上浮找位置
	 */
	public void enQueue(int value) {
		// 先判断队列长度是否超出范围，是则扩容
		if (size >= array.length) {
			reSize();
		}
		array[size++] = value;
		upAdjust();
	}

	/**
	 * 取出堆顶数据，将数组最后一个元素放到堆顶，本身置0后，下沉找位置。对于左右孩子都比自己小，则选择最小的替换。
	 *
	 * @return 堆顶数据
	 */
	public int deQueue() {
		int res = array[0];
		array[0] = array[--size];
		array[size] = 0;
		downAdjust();
		return res;
	}

	/**
	 * 扩容
	 */
	private void reSize() {
		int newSize = this.size * 2;
		this.array = Arrays.copyOf(this.array, newSize);
	}

	/**
	 * 上浮操作
	 */
	private void upAdjust() {
		int childrenIndex = size - 1;
		int parentIndex = (childrenIndex - 1) / 2;
		// 先保存刚插入的叶子节点的值，用于最后赋值
		int temp = array[childrenIndex];
		// 开始上浮操作
		while (childrenIndex > 0 && temp < array[parentIndex]) {
			// 直接单向赋值，无需转换
			array[childrenIndex] = array[parentIndex];
			childrenIndex = parentIndex;
			parentIndex = (childrenIndex - 1) / 2;
		}
		// 最后赋值
		array[childrenIndex] = temp;
	}

	/**
	 * 下沉操作
	 */
	private void downAdjust() {
		int parentIndex = 0;
		int temp = array[parentIndex];
		int length = size - 1;
		// 假设有左子节点，先求出左子节点的下标
		int childrenIndex = 2 * parentIndex + 1;
		// 当确实是有左子节点时
		while (childrenIndex <= length) {
			// 如果有右子节点且左子节点大于右子节点时，则将 childrenIndex 设置为右子节点的下标值
			if ((childrenIndex + 1) <= length && array[childrenIndex + 1] < array[childrenIndex]) {
				childrenIndex++;
			}
			// 如果无需下沉，则直接跳出循环
			if (temp <= array[childrenIndex]) {
				break;
			}
			// 接下来才是真正的下沉
			array[parentIndex] = array[childrenIndex];
			parentIndex = childrenIndex;
			childrenIndex = 2 * parentIndex + 1;
		}
		// 最后的赋值
		array[parentIndex] = temp;
	}

	@Override
	public String toString() {
		System.out.println(Arrays.toString(this.array));
		return null;
	}

	public static void main(String[] args) {
		MyPriorityQueue myPriorityQueue = new MyPriorityQueue(8);
		myPriorityQueue.enQueue(5);
		myPriorityQueue.enQueue(3);
		myPriorityQueue.enQueue(6);
		myPriorityQueue.enQueue(9);
		myPriorityQueue.enQueue(8);
		myPriorityQueue.enQueue(6);
		myPriorityQueue.enQueue(7);
		myPriorityQueue.enQueue(2);
		myPriorityQueue.enQueue(4);
		myPriorityQueue.enQueue(6);
		myPriorityQueue.enQueue(3);
		myPriorityQueue.toString();
		System.out.println("堆顶数据：" + myPriorityQueue.deQueue());
		myPriorityQueue.toString();

		// 线程安全的优先队列
		PriorityBlockingQueue queue = new PriorityBlockingQueue();

		// 线程不安全的优先队列
		Queue<Integer> queue1 = new PriorityQueue<>();

		// 自定义对象比较方式
		Queue<Object> queue2 = new PriorityQueue<>(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return o1.hashCode() - o2.hashCode();
			}
		});
	}
}
