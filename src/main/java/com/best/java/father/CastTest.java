package com.best.java.father;

/**
 * @Author: xjxu3
 * @Date: 2019/12/4 11:49
 * @Description:
 */
public class CastTest {

	//clinit里是类变量赋值动作和static代码块的集合，按序执行，new执行完成后，name还没初始化，是null
	//这里可以看成在初始化阶段调用clinit，而clinit是由static的代码按顺序执行的
	private static CastTest test = new CastTest() ;

//	private static final String name = new String("string_name");
	private static final String name = "string_name";

	static {
		System.out.println(888);
	}

	{
		System.out.println(123);
	}

	private String testName;

	private CastTest() {
		System.out.println(name);
		testName = name;
	}

	public static void main(String[] args) {
		System.out.println(test.testName); // 输出结果为: null
	}

	//	public static void main(String[] args) {
//		Father father = new Child();
//		father.setName("baba");
//		father.setRela("father");
//		Child child = new Child();
//		child.setAge(12);
//		child.setName("erzi");
//		child.setRela("son");
//		Father father1 = child;
//		Child child1 = (Child) father;
//		System.out.println(father1.getName());
//		System.out.println(child1.getName());
//
//	}
}
