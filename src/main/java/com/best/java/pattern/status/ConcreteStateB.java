package com.best.java.pattern.status;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 20:59
 * @Description:
 */
class ConcreteStateB extends State{

	@Override
	public void Handle(Context context) {
		context.setState(new ConcreteStateA()); //设置B的下一个状态是A
	}

}

