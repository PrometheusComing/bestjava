package com.best.java.aloader;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 17:56
 * @Description:
 *
 * 接口的加载和类的加载有些不同，接口初始化过程有且仅有的一种：当一个类在初始化时，要求其父类全部都已经初始化过了，
 * 但是在一个接口初始化时，并不要求父接口完全完成了初始化，只有在真正使用到父接口的时候才会初始化(如引用接口中定义的常量
 */
public class BEntity extends AEntity implements BService {

	static {
		System.out.println("BEntity class load : " + BEntity.class.getClassLoader());
	}

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public void sayHello() {
		System.out.println("hello BService");
	}
}
