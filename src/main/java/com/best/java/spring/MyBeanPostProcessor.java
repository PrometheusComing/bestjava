package com.best.java.spring;

import com.best.java.asm.anno.AsmTime;
import com.best.java.asm.vclass.AsmTimeClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @Author: xjxu3
 * @Date: 2020/1/9 16:17
 * @Description: 后置处理器，处理方法带有注解的bean，并且使用asm重新处理
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		try {
			Method[] methods = bean.getClass().getDeclaredMethods();
			boolean flag = false;
			for (Method method : methods) {
				if (method.getAnnotation(AsmTime.class) != null) {
					flag = true;
					break;
				}
			}
			if (flag) {
				ClassReader classReader;
				classReader = new ClassReader(bean.getClass().getName());
				ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				ClassVisitor classVisitor = new AsmTimeClassVisitor(Opcodes.ASM7, classWriter);
				classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
				byte[] bytes = classWriter.toByteArray();

				// 反射使用app加载器加载
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
				method.setAccessible(true);
				Class aClass = (Class) method.invoke(classLoader, bean.getClass().getName() + "_Tmp", bytes, 0, bytes.length);
				return aClass.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean.getClass().getName().startsWith("com.best.java")) {
			System.out.println("beanName:" + beanName);
		}
		return bean;
	}
}
