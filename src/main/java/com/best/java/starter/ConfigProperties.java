package com.best.java.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 8:58
 * @Description:
 */
@ConfigurationProperties(prefix = "myauto.config")
public class ConfigProperties {

	private String Ip;

	private String hostName;

	private String describe;

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
