package com.best.java.mybatis.aop;

import com.best.java.mybatis.anno.MyLog;
import com.best.java.mybatis.entity.LogEntity;
import com.best.java.mybatis.entity.User;
import com.best.java.mybatis.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: xjxu3
 * @Date: 2020/6/16 11:08
 * @Description:
 */
@Aspect
@Service
public class LogAspect {

	@Autowired
	private LogService logService;

	// @annotation,@target.@within有区别，可能会有坑
	@Pointcut("@annotation(com.best.java.mybatis.anno.MyLog)")
	public void methodIntercept() {
	}

//	@Around(value = "methodIntercept() && @annotation(log)")
//	public Object doAround(ProceedingJoinPoint pjp, MyLog log) throws Throwable {
//		System.out.println("around before");
//
//		Object o = pjp.proceed();
//		System.out.println("around after");
//		Object[] objects = pjp.getArgs();
//		User user = (User) objects[0];
//		LogEntity logEntity = new LogEntity();
//		logEntity.setId(user.getId());
//		logEntity.setOperation(log.operation());
//		logService.addLog(logEntity);
//
//		return o;
//	}

	//当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
	@After(value = "methodIntercept()&& @annotation(log)")
	public void afterAction(JoinPoint pjp, MyLog log) {

		System.out.println("it is after");
		Object[] objects = pjp.getArgs();
		User user = (User) objects[0];
		LogEntity logEntity = new LogEntity();
		logEntity.setId(user.getId());
		logEntity.setOperation(log.operation());
		logService.addLog(logEntity);
	}

	public static void main(String[] args) {
		String s = "11,null,2,3,";
		String[] ss = s.split(",");
		System.out.println(111);
	}
}
