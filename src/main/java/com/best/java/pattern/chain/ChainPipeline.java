package com.best.java.pattern.chain;

/**
 * @Author: xjxu3
 * @Date: 2020/5/12 15:17
 * @Description:
 */
public class ChainPipeline {

	ChainHandler head;

	ChainHandler tail;

	public ChainPipeline addHandler(ChainHandler current) {
		if (tail != null) {
			tail.next = current;
			tail = current;
		} else {
			head = tail = current;
		}
		return this;
	}

	public void execute() {
		if (head != null) {
			head.handler();
		}
	}
}
