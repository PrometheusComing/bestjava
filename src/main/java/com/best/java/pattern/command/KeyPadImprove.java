package com.best.java.pattern.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 14:43
 * @Description: 改进的KeyPad
 */
public class KeyPadImprove {

	private Map<String,Command> commandMap;

	public KeyPadImprove() {
		commandMap = new HashMap<>();
	}

	public void add(String name, Command command) {
		commandMap.putIfAbsent(name, command);
	}

	public void execute(String name) {
		commandMap.get(name).execute();
	}
}
