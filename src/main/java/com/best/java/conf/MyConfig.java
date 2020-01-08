package com.best.java.conf;

import com.best.java.asm.AsmPersonService;
import com.best.java.asm.ByteClassLoader;
import com.best.java.asm.vclass.AsmTimeClassVisitor;
import com.best.java.father.Child;
import com.best.java.father.Father;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: xjxu3
 * @Date: 2019/12/19 14:10
 * @Description:
 */
@Configuration
public class MyConfig {

	@Bean
	public AsmPersonService asmPersonService_Tmp() {
		ClassReader classReader;
		try {
			classReader = new ClassReader("com.best.java.asm.AsmPersonService");
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassVisitor classVisitor = new AsmTimeClassVisitor(Opcodes.ASM7,classWriter);
			classReader.accept(classVisitor,ClassReader.SKIP_DEBUG);
			byte[] bytes = classWriter.toByteArray();


			FileOutputStream fos = new FileOutputStream("C:\\Users\\prometheus\\Desktop\\AsmPersonService.class");
			fos.write(bytes);
			fos.close();

			// 反射使用app加载器加载
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
			method.setAccessible(true);
			Class aClass = (Class) method.invoke(classLoader, "com.best.java.asm.AsmPersonService_Tmp", bytes, 0, bytes.length);
			AsmPersonService asmPersonService = (AsmPersonService) aClass.newInstance();
			asmPersonService.show("mm");

			// 自定义加载器的写法，不能注入bean
//			ByteClassLoader byteClassLoader = new ByteClassLoader(bytes);
//			Class clazz = byteClassLoader.findClass("com.best.java.asm.AsmPersonService_Tmp");
//			AsmPersonService asmPersonService = (AsmPersonService) clazz.newInstance();
//			asmPersonService.show("mm");
			return asmPersonService;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new AsmPersonService();
	}

}
