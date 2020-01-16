package com.best.java.jvm.dispatch;

/**
 * @Author: xjxu3
 * @Date: 2020/1/13 16:46
 * @Description: 综合考虑
 * 宗量：方法调用者和方法参数，都叫宗量
 * 先是编译期间的静态分派，此时编译器将根据静态类型和方法参数进行选择，结果两条指令（invokevirtual）分别选择了Father.hardChoice(_360)
 * 和Father.hardChoice(QQ)，将符号引用分别给两条指令（invokevirtual）作为参数。方法调用者和参数都影响选择，所以属于静态多分派
 *
 * 然后是运行期间，动态分派的过程，在执行invokevirtual指令时（比如是son.hardChoice(new QQ());）,编译期间已经确定
 * 目标方法签名必须是hardChoice(QQ),在当前实际类型Son中，找到hardChoice(QQ arg) ,直接调用后结束。只有方法调用者有影响，所以
 * 属于动态单分派
 * p.s. 动态类型语言：类型检查过程主要在运行期而非编译器。java,c++都是静态类型语言
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
