package com.best.java.tree.balance;

/**
 * @Author: xjxu3
 * @Date: 2020/3/11 10:31
 * @Description:
 */
public class AvlTree<T extends CompareAble> {

	private AvlNode<T> root;

	public AvlTree(T o) {
		if (o == null) {
			throw new NullPointerException();
		}
		this.root = new AvlNode<T>(o);
	}

	// 暴露的插入数据,重复数据不予插入
	public boolean insert(T data) {
		if (data == null) {
			return false;
		}
		return insertNode(root, data);
	}

	// 数据包装后作为节点插入
	@SuppressWarnings("unchecked")
	private boolean insertNode(AvlNode<T> current, T data) {
		// 比根节点大，走右边插入
		if (data.compareTo(current.getData()) > 0) {
			if (current.getRight() != null) {
				insertNode(current.getRight(), data);
			} else {
				current.setRight(new AvlNode<T>(data));
			}
		} else if (data.compareTo(current) < 0) {
			// 比跟节点小，走左边插入
			if (current.getLeft() != null) {
				insertNode(current.getLeft(), data);
			} else {
				current.setLeft(new AvlNode<T>(data));
			}
		} else {
			// 已存在就返回false
			return false;
		}
		// 插入后计算平衡
		current.setBalance(calculateBalance(current));
		// 左子树高，需要右旋
		if (current.getBalance() >= 2) {
			// 右子孙高，先左旋
			if (current.getLeft().getBalance() == -1) {
				leftSpin(current.getLeft());
			}
			rightSpin(current);
		}
		// 右子树高，需要左旋
		if (current.getBalance() <= -2) {
			// 左子孙高，先右旋
			if(current.getRight().getBalance() == 1) {
				rightSpin(current.getRight());
			}
			leftSpin(current);
		}
		// 重新计算平衡
		current.setBalance(calculateBalance(current));
		// 计算深度
		current.setDepth(calculateDepth(current));
		return true;
	}

	private void leftSpin(AvlNode<T> node) {
		AvlNode<T> parent = node.getParent();
		AvlNode<T> rightSon = node.getRight();
		AvlNode<T> rightSonLeftSon = rightSon.getLeft();
		rightSon.setParent(parent);
		if (parent != null) {
			// 当前节点是父亲左孩子
			if (node == parent.getLeft()) {
				parent.setLeft(rightSon);
			} else if (node == parent.getRight()) {
				// 当前节点是父亲右孩子
				parent.setRight(rightSon);
			}
		}
		rightSon.setLeft(node);
		node.setParent(rightSon);
		node.setRight(rightSonLeftSon);
		if (rightSonLeftSon != null) {
			rightSonLeftSon.setParent(node);
		}
		// 重新计算深度，平衡
		node.setDepth(calculateDepth(node));
		node.setBalance(calculateBalance(node));
		rightSon.setDepth(calculateDepth(rightSon));
		rightSon.setBalance(calculateBalance(rightSon));
	}

	private void rightSpin(AvlNode<T> node) {
		AvlNode<T> parent = node.getParent();
		AvlNode<T> leftSon = node.getLeft();
		AvlNode<T> leftSonRightSon = leftSon.getRight();
		// 当前节点左孩子替代当前节点，并且当前节点成为左孩子的右孩子节点
		// 左孩子节点选择新父亲节点
		leftSon.setParent(parent);
		// 父亲节点不空，将当前节点的位置用左孩子节点替换
		if (parent != null) {
			// 当前节点是父亲左孩子
			if (node == parent.getLeft()) {
				parent.setLeft(leftSon);
			} else if (node == parent.getRight()) {
				// 当前节点是父亲右孩子
				parent.setRight(leftSon);
			}
		}
		// 再把当前节点变为左孩子节点的右孩子节点
		leftSon.setRight(node);
		node.setParent(leftSon);

		// 当前节点左孩子的右孩子，悬挂到当前节点的左边
		node.setLeft(leftSonRightSon);
		if (leftSonRightSon != null) {
			leftSonRightSon.setParent(node);
		}
		// 重新计算深度，平衡
		node.setDepth(calculateDepth(node));
		node.setBalance(calculateBalance(node));

		leftSon.setDepth(calculateDepth(leftSon));
		leftSon.setBalance(calculateBalance(leftSon));

	}

	private int calculateBalance(AvlNode<T> node) {
		int leftDep = 0;
		int rightDep = 0;
		if (node.getLeft() != null) {
			leftDep = node.getLeft().getDepth();
		}
		if (node.getRight() != null) {
			rightDep = node.getRight().getDepth();
		}
		return leftDep - rightDep;
	}

	private int calculateDepth(AvlNode<T> node) {
		int dep = 0;
		if (node.getLeft() != null) {
			dep = node.getLeft().getDepth();
		}
		if (node.getRight() != null && (dep < node.getRight().getDepth())) {
			dep = node.getRight().getDepth();
		}
		// 比子树中较大的树多一层
		dep ++;
		return dep;
	}
}
