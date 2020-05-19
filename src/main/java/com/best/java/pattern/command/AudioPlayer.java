package com.best.java.pattern.command;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 11:43
 * @Description:
 */
public class AudioPlayer {

	public void play(){
		System.out.println("播放...");
	}

	public void rewind(){
		System.out.println("倒带...");
	}

	public void stop(){
		System.out.println("停止...");
	}
}