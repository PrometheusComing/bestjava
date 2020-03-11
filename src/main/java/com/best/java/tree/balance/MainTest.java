package com.best.java.tree.balance;

/**
 * @Author: xjxu3
 * @Date: 2020/3/11 11:52
 * @Description:
 */
public class MainTest {
	public static void main(String[] args) {
		User user = new User();
		User user2 = new User();
		user2.setAge(20);
		AvlTree<User> userTree = new AvlTree<>(user);
		userTree.insert(user2);
		System.out.println(111);
	}
}
