package com.best.java.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xjxu3
 * @Date: 2020/4/10 11:05
 * @Description:
 */
@RestController
@RequestMapping("/feign")
public class FeignTestController {

	@Autowired
	private FeignService feignService;

	@PostMapping("/getFeign")
	public String getFeign(@RequestParam("msg") String msg) {
		return feignService.getHiFeign(msg);
	}
}
