package com.best.java.StrategyPattern;


/**
 *  FlyAction接口就是封装飞行方式的策略，Duck相当于是上下文，用于封装策略
 *  VoiceAction类似
 *  该模式定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，
 *  且算法的变化不会影响使用算法的客户。策略模式属于对象行为模式，
 *  它通过对算法进行封装，把使用算法的责任和算法的实现分割开来，
 *  并委派给不同的对象对这些算法进行管理
 *  场景如：商场打折，对不同商品的策略选择，有的打折，有的返利，也可以用来某些场景
 *  替代if else
 */
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
