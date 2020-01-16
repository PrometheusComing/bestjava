package com.best.java.jvm.dispatch;

/**
 * @Author: xjxu3
 * @Date: 2020/1/16 13:59
 * @Description: 动态分派：典型应用就是方法重写
 */
public class DynamicDispatch {
	static abstract class Human {
		public abstract void sayHello();
	}

	static class Man extends Human {
		@Override
		public void sayHello() {
			System.out.println("man say hello");
		}
	}

	static class Woman extends Human {
		@Override
		public void sayHello() {
			System.out.println("woman say hello");
		}
	}
//  public static void main(java.lang.String[]);
//    descriptor: ([Ljava/lang/String;)V
//    flags: ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=3, args_size=1
//         0: new           #2                  // class com/best/java/jvm/dispatch/DynamicDispatch$Man
//         3: dup
//         4: invokespecial #3                  // Method com/best/java/jvm/dispatch/DynamicDispatch$Man."<init>":()V
//         7: astore_1
//         8: new           #4                  // class com/best/java/jvm/dispatch/DynamicDispatch$Woman
//        11: dup
//        12: invokespecial #5                  // Method com/best/java/jvm/dispatch/DynamicDispatch$Woman."<init>":()V
//        15: astore_2
//        16: aload_1
//        17: invokevirtual #6                  // Method com/best/java/jvm/dispatch/DynamicDispatch$Human.sayHello:()V
//        20: aload_2
//        21: invokevirtual #6                  // Method com/best/java/jvm/dispatch/DynamicDispatch$Human.sayHello:()V
//        24: new           #4                  // class com/best/java/jvm/dispatch/DynamicDispatch$Woman
//        27: dup
//        28: invokespecial #5                  // Method com/best/java/jvm/dispatch/DynamicDispatch$Woman."<init>":()V
//        31: astore_1
//        32: aload_1
//        33: invokevirtual #6                  // Method com/best/java/jvm/dispatch/DynamicDispatch$Human.sayHello:()V
//        36: return
//      LineNumberTable:
//        line 28: 0
//        line 29: 8
//        line 30: 16
//        line 31: 20
//        line 32: 24
//        line 33: 32
//        line 34: 36
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      37     0  args   [Ljava/lang/String;
//            8      29     1   man   Lcom/best/java/jvm/dispatch/DynamicDispatch$Human;
//           16      21     2 woman   Lcom/best/java/jvm/dispatch/DynamicDispatch$Human;
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		man.sayHello();
		woman.sayHello();
		man = new Woman();
		man.sayHello();
	}
}
