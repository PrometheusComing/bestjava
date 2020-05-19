package com.best.java.pattern.command;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 11:44
 * @Description:
 */
public class PlayCommand implements Command {

	private AudioPlayer myAudio;

	public PlayCommand(AudioPlayer audioPlayer) {
		myAudio = audioPlayer;
	}

	/**
	 * 执行方法
	 */
	@Override
	public void execute() {
		myAudio.play();
	}

}
