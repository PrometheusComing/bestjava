package com.best.java.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2019/12/9 15:15
 * @Description:
 */
public class TestMyAnno {
	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47, 48, 49, 50);
		trackUseCases(useCases, PasswordUtils.class);
	}

	public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
		for (Method m : cl.getDeclaredMethods()) {
			//获得注解的对象
			MyAnno uc = m.getAnnotation(MyAnno.class);
			if (uc != null) {
				System.out.println("Found Use Case:" + uc.id() + " "
						+ uc.description());
				useCases.remove(new Integer(uc.id()));
			}
		}
		for (int i : useCases) {
			System.out.println("Warning: Missing use case-" + i);
		}
	}
}
