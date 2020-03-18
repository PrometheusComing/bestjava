package com.best.java.pattern.singleton;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 创建型：用于创建对象，为设计类实例化新对象提供指南
 * 结构型：用于处理类或对象的组合，对类如何设计以形成更大的结构提供指南
 * 行为型：用于描述类或对象的交互以及职责的分配，对类之间交互以及分配职责的方式提供指南
 *
 * 创建型：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式
 * 结构型：适配器模式、装饰器模式、代理模式、外观模式、桥接模式、组合模式、享元模式
 * 行为型：策略模式、模板方法模式、观察者模式、迭代子模式、责任链模式、命令模式、备忘录模式、状态模式、访问者模式、中介者模式、解释器模式
 */
public class StaticSingleton {
	private StaticSingleton () {
		System.out.println("StaticSingleton is create");
	}
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}
	public static StaticSingleton getInstance () {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		//使用unsafe破坏单例
		StaticSingleton s1 = StaticSingleton.getInstance();
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			Unsafe unsafe = (Unsafe) field.get(null);
//			Unsafe unsafe = Unsafe.getUnsafe();
			StaticSingleton s2 = (StaticSingleton)unsafe.allocateInstance(StaticSingleton.class);
			System.out.println(s1 == s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
