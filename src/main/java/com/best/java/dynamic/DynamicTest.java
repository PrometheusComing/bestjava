package com.best.java.dynamic;


import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @Author: xjxu3
 * @Date: 2020/3/30 9:19
 * @Description:
 */
public class DynamicTest {
	static class Grand {
		void see() {
			System.out.println("i see grand");
		}
	}

	static class Parents extends Grand {
		void see() {
			System.out.println("i see Parents");
		}
	}

	static class Sunzi extends Parents {
		void see() {
			try {
				// 这个是目标方法，如void see()
				MethodType methodType = MethodType.methodType(void.class);// 多个参数，第一个是返回值参数类型，后续是方法具体参数
				Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
				// PUBLIC_LOOKUP不行
//				Field field = MethodHandles.Lookup.class.getDeclaredField("PUBLIC_LOOKUP");
				field.setAccessible(true);
				//field.get,字段不是静态字段的话,要传入反射类的对象.如果传null是会报
				//java.lang.NullPointerException
				//
				//但是如果字段是静态字段的话,传入任何对象都是可以的,包括null
				MethodHandles.Lookup lookup = (MethodHandles.Lookup) field.get(null);
				// 寻找目标方法see()的执行对象，用methodHandle引用
				MethodHandle methodHandle = lookup.findSpecial(Grand.class, "see", methodType, Grand.class);
				// 找到后调用方法
				methodHandle.invoke(this);
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Sunzi().see();
	}
}
