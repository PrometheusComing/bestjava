package com.best.java.proxypattern;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xjxu3
 * @Date: 2019/4/9 16:44
 * @Description:
 */
public class CGProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("大家晚上好");
		Object returnValue = method.invoke(target, objects);
		System.out.println("再见");
		return returnValue;
	}

	private Object target;

	public CGProxy(Object target) {
		this.target = target;
	}

	public static void main(String[] args) {
		Singer tar = new Singer();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(tar.getClass());
		enhancer.setCallback(new CGProxy(tar));
		Singer proxyObj = (Singer) enhancer.create();
		proxyObj.sing();
	}
}
