package com.best.java.pattern.status;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 20:59
 * @Description:
 */
public class ConcreteStateA extends State{

	@Override
	public void Handle(Context context) {
		context.setState(new ConcreteStateB()); //设置A的下一个状态是B

	}

}
