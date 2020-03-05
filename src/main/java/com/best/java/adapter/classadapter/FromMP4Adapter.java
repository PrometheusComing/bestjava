package com.best.java.adapter.classadapter;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 20:09
 * @Description:
 * 类适配器：
 * action里的操作，已经实现了，可以通过适配器继承类的方式直接获取paly方法，
 * 并且也可以为MP4新增name方法
 *
 */
public class FromMP4Adapter extends MP4 implements Sound {

	@Override
	public void action() {
		this.play();
	}

	@Override
	public void name() {
		System.out.println("it is sound");
	}
}
