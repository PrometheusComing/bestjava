package com.best.java.starter;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 8:57
 * @Description:
 */
public class AutoConfigService {

	private ConfigProperties cp;

	public AutoConfigService(ConfigProperties configProperties){
		this.cp = configProperties;
	}


	public void start() {
		System.out.println("ip: "  + cp.getIp() + " hostName:" + cp.getHostName() + " start");
		System.out.println(cp.getDescribe());
	}
}
