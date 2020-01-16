package com.best.java.string;

/**
 * @Author: xjxu3
 * @Date: 2019/10/8 16:15
 * @Description:
 */
public class StringTest {

	private String value;

	StringTest(String value) {
		this.value = value;
	}

	public static void change(String str,char[] ch) {
		str = "test ok";
		ch[0] = 'g';
	}

	public  static void change(String s,StringTest stringTest) {
		s = "11";
		stringTest = new StringTest("22");
	}
	public static void main(String[] args) {
		char[] ch = {'a','b','c'};
		String string = new String("1");
		StringTest stringTest = new StringTest("2");
//		change(string,stringTest);
		change(string,ch);
		System.out.println(string);
		System.out.println(stringTest.value);
		System.out.println(ch);
//		String s = "AB";
//		String s5 = s + "CD";
//		String s6 = getSt() + "CD";
		//		String s1 = "ABCD";
//		String s2 = "A" + "B" + "C" + "D";
//		String s3 = "AB" + "CD";
//		String s4 = new String("ABCD");
//
//		final String ss = "AB";
//		String s7 = ss + "CD";
	}
}
