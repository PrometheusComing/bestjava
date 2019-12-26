package com.best.java;

import com.best.java.asm.AsmPersonService;
import com.best.java.controller.TestController;
import com.best.java.service.RequestService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @Author: xjxu3
 * @Date: 2019/12/16 11:42
 * @Description:
 */
@Component
public class BootRunner implements ApplicationRunner, ApplicationContextAware {


	private ApplicationContext app;

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		//将applicationContext转换为ConfigurableApplicationContext
//		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)app;
//
//		// 获取bean工厂并转换为DefaultListableBeanFactory
//		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
//
//		// 通过BeanDefinitionBuilder创建bean定义
//		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TestController.class);
//
//		// 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
////        beanDefinitionBuilder.addPropertyReference("testService", "testService");
//
//		// 注册bean
//		defaultListableBeanFactory.registerBeanDefinition("testController", beanDefinitionBuilder.getRawBeanDefinition());
//
//
//		TestController userController = (TestController) app.getBean("testController");
//
//		System.out.println("get you " + userController);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.app = applicationContext;
	}
}
