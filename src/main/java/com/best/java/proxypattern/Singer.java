package com.best.java.proxypattern;

/**
 * @Author: xjxu3
 * @Date: 2019/4/9 16:08
 * @Description:
 */

public class Singer implements Isinger{

	private String name;
	private String home;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public void sing() {
		System.out.println("现在由我为大家唱一首歌");
	}

	public void finish() {
		System.out.println("结束演唱会，再见");
	}

	public void dancer(String dancerName) {
		System.out.println("唱完歌后我会跳" + dancerName + "舞");
	}
}
