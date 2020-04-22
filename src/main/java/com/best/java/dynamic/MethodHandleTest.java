package com.best.java.dynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @Author: xjxu3
 * @Date: 2020/4/22 10:27
 * @Description:  实现java的动态类型调用，无论obj是哪种类型，都可以正确的调用
 */
public class MethodHandleTest {
	static class ClassA {
		public void println(String s) {
			System.out.println(s);
		}
	}

	public static void main(String[] args) throws Throwable{
		Object obj = System.out;
		getPrintMH(obj).invokeExact("123");
		obj = new ClassA();
		getPrintMH(obj).invokeExact("456");
	}

	private static MethodHandle getPrintMH(Object receive) throws Throwable {
		MethodType methodType = MethodType.methodType(void.class, String.class);
		Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
		field.setAccessible(true);
		MethodHandles.Lookup lookup = (MethodHandles.Lookup) field.get(null);
		// 由于实例方法需要默认传入this作为第一个隐含参数，所以这里用bindTo方法实现。
		MethodHandle methodHandle = lookup.findVirtual(receive.getClass(), "println", methodType).bindTo(receive);
		return methodHandle;
	}
}
