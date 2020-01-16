package com.best.java.jvm.dispatch;

/**
 * @Author: xjxu3
 * @Date: 2020/1/13 16:30
 * @Description: 静态分派：典型应用就是方法重载
 * 静态分派发生在编译阶段,因此确定静态分派的动作实际上不是由虚拟机来执行的。
 * 另外,编译器虽然能确定出方法的重载版本,但在很多情况下这个重载版本并不是“唯一的”,往往只能确定一个“更加合适的”版本
 *
 * Human man = new Man();
 * 把Human叫做静态类型，Man叫做实际类型。静态类型和实际类型都有可能变化，如下
 * 实际类型变化：man = new Woman();
 * 静态类型变化：sr.sayHello((Man)man);
 * main中，方法调用者sr已经确定的情况下，使用哪个版本的重载，取决于传参的数量和数据类型，而编译器在重载时是根据静态类型而不是实际
 * 类型进行判断的。所以本例中虚拟机选择了sayHello(Human guy)作为调用目标，并把这个方法的符号引用写到了invokevirtual的参数中
 */
public class StaticDispatch {

	static abstract class Human {
	}

	static class Man extends Human {
	}

	static class Woman extends Human {
	}

	public void sayHello(Human guy) {
		System.out.println("hello,guy!");
	}

	public void sayHello(Man guy) {
		System.out.println("hello,gentleman!");
	}

	public void sayHello(Woman guy) {
		System.out.println("hello,lady!");
	}

//	public static void main(java.lang.String[]);
//	descriptor: ([Ljava/lang/String;)V
//	flags: ACC_PUBLIC, ACC_STATIC
//	Code:
//	stack=2, locals=4, args_size=1
//			0: new           #7                  // class com/best/java/jvm/dispatch/StaticDispatch$Man
//			3: dup
//          4: invokespecial #8                  // Method com/best/java/jvm/dispatch/StaticDispatch$Man."<init>":()V
//			7: astore_1
//          8: new           #9                  // class com/best/java/jvm/dispatch/StaticDispatch$Woman
//		   11: dup
//         12: invokespecial #10                 // Method com/best/java/jvm/dispatch/StaticDispatch$Woman."<init>":()V
//		   15: astore_2
//         16: new           #11                 // class com/best/java/jvm/dispatch/StaticDispatch
//		   19: dup
//         20: invokespecial #12                 // Method "<init>":()V
//		   23: astore_3
//         24: aload_3
//         25: aload_1
//         26: invokevirtual #13                 // Method sayHello:(Lcom/best/java/jvm/dispatch/StaticDispatch$Human;)V
//		   29: aload_3
//         30: aload_2
//         31: invokevirtual #13                 // Method sayHello:(Lcom/best/java/jvm/dispatch/StaticDispatch$Human;)V
//		   34: return
//	LocalVariableTable:
//	Start Length  Slot  Name   Signature
//    0      35     0  args   [Ljava/lang/String;
//    8      27     1   man   Lcom/best/java/jvm/dispatch/StaticDispatch$Human;  因为Human man = new Man();
//   16      19     2 woman   Lcom/best/java/jvm/dispatch/StaticDispatch$Human;
//   24      11     3    sr   Lcom/best/java/jvm/dispatch/StaticDispatch;
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		StaticDispatch sr = new StaticDispatch();
		sr.sayHello(man);
		sr.sayHello(woman);
	}

}

