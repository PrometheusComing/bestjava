package com.best.java.finall;

public class TestTryCatch {
	public static void main(String[] args)
	{
		TestTryCatch test = new TestTryCatch();
		System.out.println(test.fun());
	}

	public int fun()
	{
		int i = 10;
		try
		{
			//doing something

			return i;
		}catch(Exception e){
			return i;
		}finally{
			i = 20;
		}
	}

	public String fun1()
	{
		String s = "Hello";
		try
		{
			//doing something
			s = "ll";

			return s;
		}catch(Exception e){
			return s;
		}finally{
			s= "pp";
		}
	}

	public StringBuilder fun2()
	{
		StringBuilder s = new StringBuilder("Hello");
		try
		{
			//doing something
			s.append("Word");

			return s;
		}catch(Exception e){
			return s;
		}finally{
			s.append("finally");
		}
	}
}


