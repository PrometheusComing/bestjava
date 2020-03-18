package com.best.java.pattern.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xjxu3
 * @Date: 2019/4/9 17:36
 * @Description:
 */
public class EnhancerDemo {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(EnhancerDemo.class);
		enhancer.setCallback(new MethodInterceptorImpl());

		EnhancerDemo demo = (EnhancerDemo) enhancer.create();

		demo.test();

		System.out.println(demo);
	}

	public void test() {
		System.out.println("EnhancerDemo test()");
	}

	private static class MethodInterceptorImpl implements MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args,
								MethodProxy proxy) throws Throwable {
			System.err.println("Before invoke " + method);
			Object result = proxy.invokeSuper(obj, args);
			System.err.println("After invoke" + method);
			return result;
		}

	}
}
