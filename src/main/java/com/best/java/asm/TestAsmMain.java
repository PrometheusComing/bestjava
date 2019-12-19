package com.best.java.asm;

import com.alibaba.fastjson.JSON;
import com.best.java.asm.vclass.AsmTimeClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

/**
 * @Author: xjxu3
 * @Date: 2019/12/12 9:41
 * @Description:
 */
@SuppressWarnings(value = "unchecked")
public class TestAsmMain {
	public static void main(String[] args) {
		try {
			ClassReader classReader = new ClassReader("com.best.java.asm.AsmPersonService");
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassVisitor classVisitor = new AsmTimeClassVisitor(Opcodes.ASM7,classWriter);
			classReader.accept(classVisitor,ClassReader.SKIP_DEBUG);

			byte[] bytes = classWriter.toByteArray();
			FileOutputStream fos = new FileOutputStream("C:\\Users\\prometheus\\Desktop\\AsmPersonService_Tmp.class");
			fos.write(bytes);
			fos.close();

			Class cl = Class.forName("com.best.java.asm.AsmPersonService");

			Object o = cl.newInstance();
			// 方法一，用反射使用
//			ByteClassLoader byteClassLoader = new ByteClassLoader(bytes);
//			Class clazz = byteClassLoader.findClass("com.best.java.asm.AsmPersonService");
//			Object o = clazz.newInstance();
//			Method method = clazz.getMethod("show",String.class);
//			System.out.println(method.invoke(o,"hh"));
//			System.out.println(byteClassLoader.getParent().toString());

			// 方法二，利用asm重写类名作为子类，使用父类引用后使用
			ByteClassLoader byteClassLoader = new ByteClassLoader(bytes);
			Class clazz = byteClassLoader.findClass("com.best.java.asm.AsmPersonService_Tmp");
			AsmPersonService asmPersonService = (AsmPersonService) clazz.newInstance();
			asmPersonService.show("mm");
			System.out.println(asmPersonService.getClass().getClassLoader());
			System.out.println(o.getClass().getClassLoader().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
