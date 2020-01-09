package com.best.java.asm.vmethod;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @Author: xjxu3
 * @Date: 2019/12/12 11:41
 * @Description:
 */
public class AsmTimeMethodVisitor extends MethodVisitor implements Opcodes {

	private boolean dealFlag = false;

	public AsmTimeMethodVisitor(int api, MethodVisitor methodVisitor) {
		super(api, methodVisitor);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		if ("Lcom/best/java/asm/anno/AsmTime;".equals(descriptor)) {
			dealFlag = true;
		}
		return super.visitAnnotation(descriptor, visible);
	}

	@Override
	public void visitCode() {
		//此方法在访问方法的头部时被访问到，仅被访问一次
		if (dealFlag) {
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
			mv.visitVarInsn(LSTORE, 1);
		}
		super.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		//此方法可以获取方法中每一条指令的操作类型，被访问多次
		if (dealFlag) {
			if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
					|| opcode == Opcodes.ATHROW) {
				mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
				mv.visitVarInsn(LLOAD, 1);
				mv.visitInsn(LSUB);
				mv.visitVarInsn(LSTORE, 3);
				mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
				mv.visitInsn(DUP);
				mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
				mv.visitLdcInsn("== method cost time = ");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitVarInsn(LLOAD, 3);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
				mv.visitLdcInsn(" ===");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
			}
		}
		super.visitInsn(opcode);
	}
}
