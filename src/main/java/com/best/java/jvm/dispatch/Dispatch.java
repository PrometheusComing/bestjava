package com.best.java.jvm.dispatch;

/**
 * @Author: xjxu3
 * @Date: 2020/1/13 16:46
 * @Description:
 */

public class Dispatch {

	static class QQ {
	}

	static class _360 {
	}

	public static class Father {
		public void hardChoice(QQ arg) {
			System.out.println("father choose qq");
		}

		public void hardChoice(_360 arg) {
			System.out.println("father choose 360");
		}
	}

	public static class Son extends Father {
		public void hardChoice(QQ arg) {
			System.out.println("son choose qq");
		}

		public void hardChoice(_360 arg) {
			System.out.println("son choose 360");
		}
	}

	public static void main(String[] args) {
		Father father = new Father();
		Father son = new Son();
		father.hardChoice(new _360());
		son.hardChoice(new QQ());
	}

}