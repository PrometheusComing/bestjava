package com.best.java.cloud;

import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xjxu3
 * @Date: 2020/4/10 11:07
 * @Description:
 */
// 指定当前feign配置。也可以在配置文件指定，更好
@FeignClient(name = "feign-test", configuration = FeignConfig.class,fallback = FeignServiceFallback.class)
public interface FeignService {

	@PostMapping("/feign/test/getHi")
	public String getHiFeign(@RequestParam("msg") String msg);
}

class FeignConfig {
	@Bean
	public Logger.Level logger() {
		return Logger.Level.FULL;
	}
}