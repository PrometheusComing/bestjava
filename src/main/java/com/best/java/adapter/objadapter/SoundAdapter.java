package com.best.java.adapter.objadapter;

import com.best.java.adapter.classadapter.Player;
import com.best.java.adapter.classadapter.Sound;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 20:14
 * @Description:
 * 对象适配器：通过组合的方式，将对象纳入适配器使用
 *
 *适配器就是对两个接口或类进行转换适配便于使用
 *
 */
public class SoundAdapter implements Sound {

	public Player mp4;

	public SoundAdapter(Player mp4) {
		this.mp4 = mp4;
	}

	@Override
	public void action() {
		mp4.play();
	}

	@Override
	public void name() {
		System.out.println("it is sound");
	}
}
