package com.best.java.StrategyPattern;

public abstract class Duck {

	private String name;

	private FlyAction flyAction;

	private VoiceAction voiceAction;

	Duck (String name) {
		this.name = name;
	}

	public abstract void diaplay();

	public void performFly() {
		flyAction.fly();
	}

	public void performVoice() {
		voiceAction.voice();
	}

	public FlyAction getFlyAction() {
		return flyAction;
	}

	public void setFlyAction(FlyAction flyAction) {
		this.flyAction = flyAction;
	}

	public VoiceAction getVoiceAction() {
		return voiceAction;
	}

	public void setVoiceAction(VoiceAction voiceAction) {
		this.voiceAction = voiceAction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
