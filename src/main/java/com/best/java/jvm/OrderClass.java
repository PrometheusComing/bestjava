package com.best.java.jvm;

/**
 * @Author: xjxu3
 * @Date: 2020/1/15 10:58
 * @Description: 观察字节码指令，com.best.java.finall.TestTryCatch里也有指令
 * 静态解析：class文件中的大量符号引用，字节码中的方法调用指令就以这些符号引用作为参数。这些符号引用一部分会在类加载阶段或者
 * 第一次使用的时候转化为直接引用，这种转化叫静态解析
 * 动态连接：另外一部分每次运行的期间转化为直接引用，这个转化叫动态连接
 *
 * 方法调用分为：解析（一定是静态），分派（静态单分派，静态多分派，动态单分派，动态多分派）
 *
 * 方法调用目的就是确定被调用的方法的版本（调用哪个方法）
 *
 * 5条方法调用指令：
 * 1.invokestatic:调用静态方法
 * 2.invokespecial：只能调用三类方法：实例构造器<init>方法；私有方法；super.method()。因为这三类方法的调用对象在编译时就可以确定。
 * 3.invokevirtual：调用虚方法，是一种动态分派的调用指令：也就是引用的类型并不能决定方法属于哪个类型。
 * 4.invokeinterface:调用接口方法，会在运行时确定一个实现此接口的对象
 * 5.invokedynamic:先在运行时动态解析出调用点限定符所引用的方法，然后再执行该方法。前面4条指令的分派逻辑在虚拟机内部固化，而该指令
 * 分派逻辑由用户设定的引导方法决定。
 *
 *invokestatic invokespecial指令调用的方法都可以在解析阶段确定方法版本，符合的方法有静态方法，实例构造器<init>方法，私有方法
 * 父类方法4种，他们在解析阶段会将符号引用直接转化为直接引用，又被叫做非虚方法。其他都被叫做虚方法（除去final方法）
 *
 * final方法也属于非虚方法，不过用的是invokevirtual指令
 */
public class OrderClass {

	private int m;

	public int inc() {
		return m + 1;
	}

	public int inc2() {
		int x;
		int y;
		try {
			x = 1;
			y = 2;
			return x + y;

		} catch (Exception e) {
			x = 2;
			return x;
		} finally {
			x = 3;
		}
	}
//public int inc2();
//	descriptor: ()I
//	flags: ACC_PUBLIC
//	Code:
//	stack=2, locals=6（总共用到6个slot，0~5）, args_size=1
//		   0: iconst_1 // 将1压入操作数栈栈顶
//         1: istore_1 // 将栈顶出栈并存入slot为1的变量x中，即x等于1
//         2: iconst_2 // 将2压入栈顶
//         3: istore_2 // 将栈顶出栈并存入slot为2的变量y中，即y等于2
//         4: iload_1  // 将slot 1里面的值1，压入栈顶
//         5: iload_2  // 将slot 2里面的值2，压入栈顶
//         6: iadd     // 将操作数栈中的两个数出栈并执行加法后，结果压入栈顶，此时操作数栈只有一个值，即x+y等于3
//         7: istore_3 // 将栈顶出栈并保存到slot 3中，其实这里的slot 3被重用了，用于存放returnValue
//         8: iconst_3 // 将3压入栈顶
//         9: istore_1 // 将3出栈并存入slot 1的变量x中，此时x等于3（此处就是finall代码）
//        10: iload_3  // 将slot 3里面的值3（就是returnValue）压入栈顶
//        11: ireturn  // 将栈顶的值3，进行返回整型操作
//        12: astore_3         // catch语句，将栈顶的值出栈，并存入slot为3的异常引用中，就是给exception的e赋值（JVM会将Exception异常对象的引用入栈）
//        13: iconst_2         // 将2压入栈顶
//        14: istore_1         // 将栈顶的值2出栈并存入slot 1中，即x等于2
//        15: iload_1          // 将slot 1的值2入栈
//        16: istore        4  // 将栈顶的值2出栈并存入slot 4
//		  18: iconst_3         // 将3压入栈顶
//        19: istore_1         // 将栈顶的值3出栈并存入slot 1，此时x等于3（就是finally）
//        20: iload         4  // 将slot 4的值2入栈
//		  22: ireturn          // 将栈顶的值2，进行返回整型操作
//        23: astore        5     // catch中出现异常，将栈顶的值出栈并存入slot 5的引用中
//		  25: iconst_3            // 将3入栈
//        26: istore_1            // 将栈顶的值3出栈并存入slot 1，此时x等于3
//        27: aload         5     // 将slot 5的值2入栈
//		  29: athrow              // 将异常抛出
//	Exception table:// 异常表
//	from    to  target type
//    0     8    12   Class java/lang/Exception  //try中出现Exception或者其子类的异常，跳转到12，即catch语句块执行
//    0     8    23   any                        //try中出现不属于Exception或者其子类的异常，跳转到23，即finally执行
//   12    18    23   any                        //如果catch中出现了异常，跳转到23，即finally执行
//   23    25    23   any
//	LocalVariableTable:
//	Start  Length  Slot  Name   Signature
//   2      10     1     x   I
//   4       8     2     y   I
//  13      10     3     e   Ljava/lang/Exception;
//  15       8     1     x   I
//   0      30     0  this   Lcom/best/java/jvm/OrderClass;
//  27       3     1     x   I
//	StackMapTable: number_of_entries = 2
//	frame_type = 76 /* same_locals_1_stack_item */
//	stack = [ class java/lang/Exception ]
//	frame_type = 74 /* same_locals_1_stack_item */
//	stack = [ class java/lang/Throwable ]

	public static void main(String[] args) {

	}

}
