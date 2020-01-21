package com.best.java.proxypattern;

import javax.sound.midi.Soundbank;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: xjxu3
 * @Date: 2019/4/9 16:13
 * @Description:
 *
 * 关键在于ProxyGenerator.generateProxyClass类创建一个$proxy0对象
 * 该对象通过继承Proxy并且构造器注入InvocationHandler()来操作目标对象，而要操作目标对象的方法
 * 只能通过继承或者实现，java不支持多继承，因此必须要求目标对象实现接口，然后$proxy0对象也实现该
 * 接口，从而操作目标对象的方法
 *
 */
public class JDKProxy {
	public static void main(String[] args) {
		Singer target = new Singer();
		Isinger proxy = (Isinger) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if (method.getName().equals("sing")) {
							System.out.print("hello,大家好.");
							Object returnValue = method.invoke(target, args);
							System.out.println("谢谢啦!");
							return returnValue;

						} else {
							Object returnValue = method.invoke(target, args);
							return returnValue;
						}

					}
				});
		proxy.sing();
		proxy.finish();
		proxy.dancer("桑巴");
	}
}
