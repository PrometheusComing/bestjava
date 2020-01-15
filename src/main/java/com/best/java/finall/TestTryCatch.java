package com.best.java.finall;

import com.sun.tools.javadoc.Start;
import javassist.bytecode.StackMapTable;

public class TestTryCatch {
	public static void main(String[] args) {
		TestTryCatch test = new TestTryCatch();
		System.out.println(test.fun3());
	}

	public int fun() {
		int i = 10;
		try {
			//doing something

			return i;
		} catch (Exception e) {
			return i;
		} finally {
			i = 20;
		}
	}

	public String fun1() {
		String s = "Hello";
		try {
			//doing something
			s = "ll";

			return s;
		} catch (Exception e) {
			return s;
		} finally {
			s = "pp";
		}
	}

	public String fun2() {
		String s = new String("hello");
		try {
			//doing something
			s = "ll";

			return s;
		} catch (Exception e) {
			return s;
		} finally {
			s = "pp";
		}
	}

	public StringBuilder fun3() {
		StringBuilder s = new StringBuilder("Hello");
		try {
			//doing something
			s.append("Word");

			return s;
		} catch (Exception e) {
			return s;
		} finally {
			s.append("finally");
		}
	}
//	public java.lang.StringBuilder fun3();
//	descriptor: ()Ljava/lang/StringBuilder;
//	flags: ACC_PUBLIC
//	Code:
//	stack=3, locals=5, args_size=1
//			0: new           #14                 // class java/lang/StringBuilder 创建对象，并将其引用入栈，s
//			3: dup                               // 复制一份引用入栈（现在操作数栈里有2份引用,s,s1）
//          4: ldc           #8                  // String Hello 加载字符串hello入栈
//			6: invokespecial #15                 // Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V 调用构造方法，消耗字符串和一份引用s1
//			9: astore_1                          // 将栈顶的引用s出栈，存到slot 1（现在操作数栈是空的）
//         10: aload_1                           // 将slot 1的引用s加载到栈顶
//         11: ldc           #16                 // String Word 加载字符串word入栈
//		   13: invokevirtual #17                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder; 调用append
//	                                                方法，消耗字符串和引用s,出栈帧后将返回值压入当前栈帧里的操作数栈（现在栈顶是s）
//		   16: pop                               // 舍弃当前栈顶元素s
//         17: aload_1                           // 将slot 1的引用s加载到栈顶（slot1 里的s）
//         18: astore_2                          // 栈顶出栈，将引用s存到slot 2（slot 2里也存一个，作为returnValue）
//         19: aload_1                           // 将slot 1的引用s加载到栈顶
//         20: ldc           #18                 // String finally 将字符串finally入栈
//		   22: invokevirtual #17                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//	                                                调用append方法，消耗字符串和引用,出栈帧后将返回值压入当前栈帧里的操作数栈（现在栈顶是s）
//		   25: pop                               // 舍弃当前栈顶元素s
//         26: aload_2                           // 将slot 2中的引用入栈
//         27: areturn                           // 将栈顶出栈，返回引用
//         28: astore_2
//         29: aload_1
//         30: astore_3
//         31: aload_1
//         32: ldc           #18                 // String finally
//		   34: invokevirtual #17                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//		   37: pop
//         38: aload_3
//         39: areturn
//         40: astore        4
//		   42: aload_1
//         43: ldc           #18                 // String finally
//		   45: invokevirtual #17                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//		   48: pop
//         49: aload         4
//		   51: athrow
//	Exception table:
//	from    to  target type
//    10    19    28   Class java/lang/Exception
//    10    19    40   any
//    28    31    40   any
//    40    42    40   any
//	LocalVariableTable:
//	Start Length  Slot  Name   Signature
//   29      11     2     e   Ljava/lang/Exception;
//    0      52     0  this   Lcom/best/java/finall/TestTryCatch;
//   10      42     1     s   Ljava/lang/StringBuilder;
//	StackMapTable: number_of_entries = 2
//	frame_type = 255 /* full_frame */
//	offset_delta = 28
//	locals = [ class com/best/java/finall/TestTryCatch, class java/lang/StringBuilder ]
//	stack = [ class java/lang/Exception ]
//	frame_type = 75 /* same_locals_1_stack_item */
//	stack = [ class java/lang/Throwable ]
}


