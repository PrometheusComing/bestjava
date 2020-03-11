package com.best.java.tree.balance;

/**
 * @Author: xjxu3
 * @Date: 2020/3/11 9:35
 * @Description: 树节点
 */
public class AvlNode<T extends CompareAble> {

	// 节点数据
	private T data;

	// 深度
	private int depth;

	// 平衡因子
	private int balance;

	// 左孩子节点
	private AvlNode<T> left;

	// 右孩子节点
	private AvlNode<T> right;

	// 父节点
	private AvlNode<T> parent;


	public AvlNode(T data) {
		this.data = data;
		this.depth = 1;
		this.balance = 0;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public AvlNode<T> getLeft() {
		return left;
	}

	public void setLeft(AvlNode<T> left) {
		this.left = left;
	}

	public AvlNode<T> getRight() {
		return right;
	}

	public void setRight(AvlNode<T> right) {
		this.right = right;
	}

	public AvlNode<T> getParent() {
		return parent;
	}

	public void setParent(AvlNode<T> parent) {
		this.parent = parent;
	}
}
