package com.best.java.pattern.command;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:24
 * @Description:
 */
public class TVController implements Controller {

	private Machine machine;

	public TVController(Machine machine) {
		this.machine = machine;
	}



	@Override
	public void open() {
		machine.open();
	}

	@Override
	public void close() {
		machine.close();
	}

	@Override
	public void switchIt() {
		machine.switchIt();
	}
}
