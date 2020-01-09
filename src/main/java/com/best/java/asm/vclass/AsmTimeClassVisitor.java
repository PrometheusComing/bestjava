package com.best.java.asm.vclass;

import com.best.java.asm.vmethod.AsmTimeConstructMethodVisitor;
import com.best.java.asm.vmethod.AsmTimeMethodVisitor;
import org.objectweb.asm.*;


/**
 * @Author: xjxu3
 * @Date: 2019/12/12 11:19
 * @Description:
 */
public class AsmTimeClassVisitor extends ClassVisitor implements Opcodes {

	private String myClassName;

	public AsmTimeClassVisitor(int api, ClassVisitor classVisitor) {
		super(api, classVisitor);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		//将自己构造成子类，继承原有的类
		this.myClassName = name;
		// 新增一个方法
//		MethodVisitor mw = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "add", "([Ljava/lang/String;)V", null, null);
//		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//		mw.visitLdcInsn("this is add method print!");
//		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false);
//		mw.visitInsn(RETURN);
//		mw.visitMaxs(0, 0);
//		mw.visitEnd();
		super.visit(version, access, name + "_Tmp", signature, name, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
		return super.visitField(access, name, descriptor, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//		System.out.println("method name:" + name + " descriptor :" + descriptor);

		// 对构造方法修改，使用原有类作为父类
		if ("<init>".equals(name)) {
			if (cv != null) {
				MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
				return new AsmTimeConstructMethodVisitor(Opcodes.ASM7, mv,myClassName);
			}
		}
		// 针对被注解的方法
		if (cv != null) {
			MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
			return new AsmTimeMethodVisitor(Opcodes.ASM7, methodVisitor);
		}
		return null;
	}
}
