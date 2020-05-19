package com.best.java.pattern.prototype;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 10:18
 * @Description:
 * 1.在需要一个类的大量对象的时候，使用原型模式是最佳选择，因为原型模式是在内存中对这个对象进行拷贝，要比直接new这个对象性能要好很多，在这种情况下，需要的对象越多，原型模式体现出的优点越明显。
 *
 * 2.如果一个对象的初始化需要很多其他对象的数据准备或其他资源的繁琐计算，那么可以使用原型模式。
 *
 * 3.当需要一个对象的大量公共信息，少量字段进行个性化设置的时候，也可以使用原型模式拷贝出现有对象的副本进行加工处理
 */
public class Resume implements Cloneable {
	private String name;
	private String position;
	private int salary;

	public Resume() {
		System.out.println("clone方法默认浅拷贝，也就是内部的数组和引用对象不会拷贝，其他的原始基本类型和String类型会被拷贝，构造只执行一次");
		System.out.println("深拷贝，只需要自己在clone里把引用对象在创建一次或者也实现clone方法并调用就行");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Resume salary(int salary) {
		this.salary = salary;
		return this;
	}

	public Resume position(String position) {
		this.position = position;
		return this;
	}

	public Resume name(String name) {
		this.name = name;
		return this;
	}

	@Override
	protected Resume clone() {
		Resume resume;
		try {
			resume = (Resume) super.clone();
		} catch (CloneNotSupportedException e) {
			resume = new Resume();
			resume.setName(this.name);
			resume.setPosition(this.position);
			resume.setSalary(this.salary);

		}
		return resume;
	}

	@Override
	public String toString() {
		return "Resume{" +
				"name='" + name + '\'' +
				", position='" + position + '\'' +
				", salary=" + salary +
				'}';
	}

	public static void main(String[] args) {
		Resume resume = new Resume();
		resume.setName("小杰");
		resume.setPosition("杭州");
		resume.setSalary(20);
		Resume resume1 = resume.clone().salary(18);
		Resume resume2 = resume.clone().position("上海");
		Resume resume3 = resume.clone().salary(24).position("上海");
		System.out.println(resume.toString());
		System.out.println(resume1.toString());
		System.out.println(resume2.toString());
		System.out.println(resume3.toString());

	}
}
