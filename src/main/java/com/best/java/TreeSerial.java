package com.best.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xjxu3
 * @Date: 2020/7/8 19:33
 * @Description:
 */
public class TreeSerial {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}

	public static void main(String[] args) {
		TreeNode root1 = new TreeNode(1);
		TreeNode root2 = new TreeNode(2);
		TreeNode root3 = new TreeNode(3);
		TreeNode root4 = new TreeNode(4);
		TreeNode root5 = new TreeNode(5);
		root1.left = root2;
		root1.right = root3;
		root3.left=root4;
		root3.right = root5;
		TreeSerial t = new TreeSerial();
		System.out.println(t.serial(root1));
		String s = t.serial(root1);
		TreeNode deNode = t.deSerial(s);
		System.out.println(123);
	}

	public String serial(TreeNode root) {
		if (root == null) {
			return "null";
		}
		return getVal(root);
	}
	public String getVal(TreeNode node) {
		String result = "";
		if (node != null) {
			result += node.val + ",";
			result += getVal(node.left);
			result += getVal(node.right);
		} else {
			result = "null,";
		}
		return result;
	}

	public TreeNode deSerial(String source) {
		if (source == null || "null,".equals(source)) {
			return null;
		}
		String[] strs = source.split(",");
		TreeNode root = new TreeNode(Integer.valueOf(strs[0]));
		Map<String, Integer> map = new HashMap<>();
		map.put("index", 1);
		root.left = getNode(strs, map);
		root.right = getNode(strs, map);
		return root;
	}

	public TreeNode getNode(String[] strings,Map<String, Integer> map) {
		int index = map.get("index");
		if (index >= strings.length) {
			map.put("index",index + 1);
			return null;
		}
		if ("null".equals(strings[index])) {
			map.put("index",index + 1);
			return null;
		}
		TreeNode current = new TreeNode(Integer.valueOf(strings[index]));
		map.put("index",index + 1);
		current.left = getNode(strings,map);
		current.right = getNode(strings,map);
		return current;
	}
}
