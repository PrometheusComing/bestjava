package com.best.java.futuretest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 *     private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
 *     private static final int COUNT_BITS = Integer.SIZE - 3;  就是29
 *     private static final int CAPACITY   = (1 << COUNT_BITS) - 1; 高8位是 0001 1111 后面全是1，其实就是线程数量最大值（前3位是状态）
 *
 *     private static final int RUNNING    = -1 << COUNT_BITS;
 *     private static final int SHUTDOWN   =  0 << COUNT_BITS;
 *     private static final int STOP       =  1 << COUNT_BITS;
 *     private static final int TIDYING    =  2 << COUNT_BITS;
 *     private static final int TERMINATED =  3 << COUNT_BITS;
 *
 *     private static int runStateOf(int c)     { return c & ~CAPACITY; }  计算状态，最大值取反后&
 *     private static int workerCountOf(int c)  { return c & CAPACITY; } 计算线程数量，最大值&
 *     private static int ctlOf(int rs, int wc) { return rs | wc; }
 *
 *     一个int的32位,用前3位表示线程池状态，后29位表示线程数量
 *     高3位是111，后面全是0.
 *     所以RUNNING状态就是-1左移29位，E000 0000(高8位是1110 0000)；ctlOf(RUNNING, 0)后依然是E000 0000;所以线程池刚建立的状态下，
 *     ctl刚开始就是E000 0000;
 *
 *     SHUTDOWN就是0左移29位，是0000 0000
 *     STOP是2000 0000(高8位是0010 0000)
 *     TIDYING是4000 0000(高8位是0100 0000)
 *     TERMINATED是6000 0000(高8位是0110 0000)
 *
 */
public class FutureMain {
	public static void main(String...args) throws InterruptedException,ExecutionException{
		FutureTask<String> futureTask = new FutureTask<>(new RealData("a-"));
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(futureTask);
		System.out.println("请求完毕");
		Thread.sleep(1000);
		System.out.println("数据是： " + futureTask.get());
	}
}
