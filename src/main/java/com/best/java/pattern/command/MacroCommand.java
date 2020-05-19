package com.best.java.pattern.command;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 11:45
 * @Description:
 */
public interface MacroCommand extends Command {
	/**
	 * 宏命令聚集的管理方法
	 * 可以添加一个成员命令
	 */
	public void add(Command cmd);

	/**
	 * 宏命令聚集的管理方法
	 * 可以删除一个成员命令
	 */
	public void remove(Command cmd);
}
