package com.best.java.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @Author: xjxu3
 * @Date: 2019/12/9 22:50
 * @Description:
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

	private final static String REQUEST_ID = "requestId";
	private final static String LOCAL_IP = "localIP";

	private static String LOCALIP;

	static {
		try {
			LOCALIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			LOCALIP = "127.0.0.1";
		}
	}


	private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String ip = request.getRemoteAddr();
		String uuid = UUID.randomUUID().toString();
		MDC.put(REQUEST_ID,uuid);
		MDC.put(LOCAL_IP,LOCALIP);
		logger.info("requestId is {},ip is {}",uuid,ip);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String uuid = MDC.get(REQUEST_ID);
		String result = response.toString();
		logger.info("requestId is {},result is {}",uuid,request);
		MDC.remove(REQUEST_ID);
        MDC.remove(LOCAL_IP);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
