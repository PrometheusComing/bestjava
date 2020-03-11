package com.best.java.tree.balance;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: xjxu3
 * @Date: 2020/3/11 10:19
 * @Description:
 */
@Getter
@Setter
public class User extends CompareAble<User> {

	private String userId;

	private String userName;

	private int age;

	// 余额
	private int account;

	@Override
	public int compareTo(User o) {
		return (this.age - o.getAge());
	}
}
