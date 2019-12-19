package com.best.java.futureTest;

import java.util.concurrent.Callable;

public class RealData implements Callable<String> {

	private String pra;

	RealData(String pra) {
		this.pra = pra;
	}

	@Override
	public String call() throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0;i < 10;i++) {
			stringBuilder.append(pra);
		}
		Thread.sleep(10000);
		return stringBuilder.toString();
	}
}
