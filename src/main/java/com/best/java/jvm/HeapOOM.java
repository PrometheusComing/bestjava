package com.best.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2020/1/14 9:45
 * @Description: 注意：JVM 生成 Heap Dump 的时候，虚拟机是暂停一切服务的。如果是线上系统执行 Heap Dump 时需要注意。
 *  -Xms5M -Xmx5M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:\test.hprof -XX:+PrintGCDetails
 *  -Xloggc:../logs/gc.log 日志文件的输出路径
 */
public class HeapOOM {
	static class OOMObject{

	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		while (true) {
			System.out.println(111);
			list.add(new OOMObject());
		}
	}
}
