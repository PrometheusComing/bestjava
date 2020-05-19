package com.best.java.pattern.command;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 11:45
 * @Description:
 */
public class StopCommand implements Command {
	private AudioPlayer myAudio;

	public StopCommand(AudioPlayer audioPlayer) {
		myAudio = audioPlayer;
	}

	@Override
	public void execute() {
		myAudio.stop();
	}

}