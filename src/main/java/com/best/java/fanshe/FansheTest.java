package com.best.java.fanshe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;

/**
 * @Author: xjxu3
 * @Date: 2019/12/5 11:29
 * @Description:
 */
public class FansheTest {



	public static void main(String[] args) {
		int i = 10;
		System.out.println(String.class.getClassLoader());
		System.out.println(FansheTest.class.getClassLoader());
		try {
//			Car car = new Car("benchi", "baise", 200);
			Car c = FansheTest.initByDefaultConst();
			System.out.println(c.getBrand());
			System.out.println(c.getColor());
			System.out.println(c.getMaxSpeed());
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public static Car initByDefaultConst() throws Throwable {

		//①通过类装载器获取Car类对象
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println(loader.toString());
		// loadClass不执行初始化
//		Class clazz = loader.loadClass("com.best.java.fanshe.Car");
		Class clazz = Class.forName("com.best.java.fanshe.Car");
		//②获取类的默认构造器对象并通过它实例化Car
		Constructor cons = clazz.getDeclaredConstructor();

//		Constructor cons = clazz.getDeclaredConstructor(String.class,String.class,int.class);
		cons.setAccessible(true);
		Car car = (Car) cons.newInstance();

//		Car car = (Car) cons.newInstance("baoma","baise",120);

		//③通过反射方法设置属性
		Method setBrand = clazz.getMethod("setBrand", String.class);
		setBrand.invoke(car, "红旗CA72");
		Method setColor = clazz.getMethod("setColor", String.class);
		setColor.invoke(car, "黑色");
		Method setMaxSpeed = clazz.getMethod("setMaxSpeed", int.class);
		setMaxSpeed.invoke(car, 200);
		return car;
	}
}
class MySecurityManager extends SecurityManager {
	public void checkPermission(Permission perm) {
		if (perm.getName().equals("suppressAccessChecks")) {
			throw new SecurityException("Can not change the permission dude.!");
		}
	}
}
