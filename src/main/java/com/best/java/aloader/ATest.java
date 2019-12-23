package com.best.java.aloader;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 17:14
 * @Description: 子类自定义加载器加载，父类默认使用子类的加载器
 */
public class ATest {
	public static void main(String[] args) {
		AClassLoader classLoader = new AClassLoader();
		try {
			Class c = classLoader.findClass("com.best.java.aloader.BEntity");
			System.out.println(c.getClassLoader());
			Object o = c.newInstance();
			AEntity aEntity = (AEntity)o ;
//			if (o instanceof AEntity) {
//				System.out.println(111);
//			} else if (o instanceof BEntity) {
//				System.out.println(222);
//			} else {
//				System.out.println(333);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		AClassLoader classLoader = new AClassLoader();
//		try {
//			Class c = classLoader.findClass("com.best.java.aloader.AEntity");
//			System.out.println(c.getClassLoader());
//			Object o = c.newInstance();
//			AEntity aEntity = (AEntity)o ;
//			aEntity.setAge(1);
//			System.out.println(aEntity.getAge());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public static void main(String[] args) {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		try {
//			Class c = classLoader.loadClass("com.best.java.aaa.AEntity");
//				System.out.println(c.getClassLoader());
//
//			AEntity aEntity = (AEntity) c.newInstance();
//			aEntity.setAge(1);
//			System.out.println(aEntity.getAge());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
