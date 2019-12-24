package com.best.java;

import com.best.java.asm.AsmPersonService;
import com.best.java.controller.TestController;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2019/12/16 11:42
 * @Description:
 */
@Component
public class BootRunner implements ApplicationRunner, ApplicationContextAware {

	private static String agentPath = "C:\\Users\\prometheus\\Desktop\\work\\agentjava\\target\\agent-java-1.0-SNAPSHOT.jar";


	private ApplicationContext app;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<VirtualMachineDescriptor> list = VirtualMachine.list();
		list.forEach(t -> {
			System.out.println(t.displayName());
			if (t.displayName().endsWith("com.best.java.BootApplication")) {
				VirtualMachine virtualMachine = null;
				try {
					virtualMachine = VirtualMachine.attach(t.id());
					virtualMachine.loadAgent(agentPath);
					virtualMachine.detach();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}});

//		//将applicationContext转换为ConfigurableApplicationContext
//		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)app;
//
//		// 获取bean工厂并转换为DefaultListableBeanFactory
//		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
//
//		// 通过BeanDefinitionBuilder创建bean定义
//		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AsmPersonService.class);
//
//		// 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
////        beanDefinitionBuilder.addPropertyReference("testService", "testService");
//
//		// 注册bean
//		defaultListableBeanFactory.registerBeanDefinition("AsmPersonService_Tmp", beanDefinitionBuilder.getRawBeanDefinition());
//
//
//		TestController userController = (TestController) app.getBean("testController");
//
//		System.out.println(userController);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.app = applicationContext;
	}
}
