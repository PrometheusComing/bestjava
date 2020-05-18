package com.best.java.pattern.status;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 20:58
 * @Description:
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
		System.out.println("当前状态为"+state);
	}
	public void request(){
		state.Handle(this); //对请求做处理并且指向下一个状态
	}
}
