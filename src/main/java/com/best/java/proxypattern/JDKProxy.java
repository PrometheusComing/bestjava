package com.best.java.proxypattern;

import javax.sound.midi.Soundbank;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: xjxu3
 * @Date: 2019/4/9 16:13
 * @Description:
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
