package com.best.java.pattern.status;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 20:58
 * @Description:
 *
 * 　环境(Context)角色，也成上下文：定义客户端所感兴趣的接口，并且保留一个具体状态类的实例。这个具体状态类的实例给出此环境对象的现有状态。
 *
 * 　抽象状态(State)角色：定义一个接口，用以封装环境（Context）对象的一个特定的状态所对应的行为。
 *
 * 　具体状态(ConcreteState)角色：每一个具体状态类都实现了环境（Context）的一个状态所对应的行为
 *
 *   也就是下面这个Context的state会根据Context的现有状态发生变化，见demo
 */
public class Context {
	State state;

	public Context(State state) { //定义Context的初始状态
		super();
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		System.out.println("当前状态为" + state);
	}

	public void request() {
		state.Handle(this); //对请求做处理并且指向下一个状态
	}
}
