package com.best.java.jvm.dispatch;

/**
 * @Author: xjxu3
 * @Date: 2020/1/16 13:59
 * @Description: 动态分派：典型应用就是方法重写
 * 方法调用者的确定要使用实际类型，即man和woman，虽然两条invokevirtual指令的参数都是
 * com/best/java/jvm/dispatch/DynamicDispatch$Human.sayHello:()V，但实际最终执行
 * 的目标方法是不同的，原因在于invokevirtual的多态查找过程：
 * 1.找到操作数栈栈顶元素指向的对象的实际类型，如main方法里，man的实际类型是Man，静态类型是Human
 * 2.如果在实际类型Man里找到常量池中描述符和简单名称都相符的方法(注意，不是在Man常量池里找，是Man的方法里)，则进行访问权限校验，校验通过则查找完成，返回该方法直接引用
 * 3.否则按照继承关系从下往上依次对其父类进行第二步
 *
 * 这种运行期间根据实际类型确定方法调用版本的过程就是动态分派
 * java只有动态单分派但是有静态多分派和静态单分派，JDK 7 及之前
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
	//DynamicDispatch字节码
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


