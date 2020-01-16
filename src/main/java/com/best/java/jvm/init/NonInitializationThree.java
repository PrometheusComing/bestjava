package com.best.java.jvm.init;

/**
 * @Author: xjxu3
 * @Date: 2019/12/16 16:31
 * @Description: 被动引用，不会触发类加载
 * 类加载的初始化有且只有5种场景会发生，被称为类的主动引用
 * 1.遇到new,getstatic,putstatic,invokestatic这4条字节码指令，如果类没有初始化，就会先触发其初始化
 * 这4条指令常见于使用new实例化对象、读取或者设置一个类的静态字段（被final修饰且编译期把结果放入常量池的
 * 静态字段除外）以及调用一个类的静态方法的时候
 * 2.对类使用反射操作的时候
 * 3.初始化一个类的时候，其父类没有初始化过，会触发父类的初始化
 * 4.当虚拟机启动时，用户需要指定一个要执行的主类（包含main），虚拟机会先初始化这个主类
 * 5.动态语言支持时，java.lang.invoke.MethodHandle实例最后的解析结果中有REF_getStatic,REF_putStatic
 * REF_invokeStatic的方法句柄，且这个方法句柄的类没有初始化过
 *
 * 类的被动引用，3种场景举例
 * 1.通过子类引用父类的静态字段，不会引发子类的初始化（见PassiveReference）
 * 2.通过数组的定义引用类，不会触发初始化（见PassiveReference）
 * 3.常量在编译期间会存入调用类的常量池中，本质上并没用直接引用到定义常量的类，因此不会触发定义常量的
 * 类的初始化。其实编译后的字节码，NonInitializationThree都不持有Fieldref  // com/best/java/jvm/init/
 * ConstClass.HELLO_WORLD:Ljava/lang/String;（就是本例）
 */
public class NonInitializationThree {
	public static void main(String[] args) {
		System.out.println(ConstClass.HELLO_WORLD);
	}
}
