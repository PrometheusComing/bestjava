package com.best.java.cloud;

import org.springframework.stereotype.Component;

/**
 * @Author: xjxu3
 * @Date: 2020/4/10 13:59
 * @Description:
 */
@Component
public class FeignServiceFallback implements FeignService{
	@Override
	public String getHiFeign(String msg) {
		return "default fall back hi feign";
	}
}
