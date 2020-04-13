package com.best.java.cloud;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: xjxu3
 * @Date: 2020/4/10 14:01
 * @Description:
 */
@Component
public class FeignServiceFallbackFactory implements FallbackFactory<FeignService> {
	@Override
	public FeignService create(Throwable throwable) {
		return new FeignService() {
			@Override
			public String getHiFeign(String msg) {
				return "default fall factory hi feign";
			}
		};
	}
}
