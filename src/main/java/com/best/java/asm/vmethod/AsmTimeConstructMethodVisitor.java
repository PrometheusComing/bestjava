package com.best.java.asm.vmethod;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @Author: xjxu3
 * @Date: 2019/12/16 9:47
 * @Description:
 */
public class AsmTimeConstructMethodVisitor extends MethodVisitor implements Opcodes {


	private String father;

	public AsmTimeConstructMethodVisitor(int api, MethodVisitor methodVisitor,String father) {
		super(api, methodVisitor);
		this.father = father;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		// 改造当前这个方法的这个方法指令，要改造方法的哪个指令，可以用asm plugin确定
		super.visitMethodInsn(opcode, father, name, descriptor, isInterface);
	}
}
