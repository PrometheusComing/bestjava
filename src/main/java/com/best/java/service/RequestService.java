package com.best.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: xjxu3
 * @Date: 2019/12/10 1:29
 * @Description:
 */
@Service
public class RequestService {
	private final static Logger logger = LoggerFactory.getLogger(RequestService.class);

	public String getHello() {
		logger.info("it is service");
		return "service";
	}
}
