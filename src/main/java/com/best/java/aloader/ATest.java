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
			// 类加载器破坏了双亲委派就无法强转，因为强转使用的AEntity用app加载的且不执行初始化。此时有两个AEntity的class实例
			// 如果不破换双亲委派,父类最终是app加载的,就可以强转了
			AEntity aEntity = (AEntity)o ;
			// 子类可以正常使用接口
//			BService bService = (BService)o;
//			bService.sayHello();
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
