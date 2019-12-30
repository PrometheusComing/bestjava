package com.best.java;

import com.best.java.aop.LogInterceptor;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2019/12/9 19:57
 * @Description:
 */
//@EnableAspectJAutoProxy
@SpringBootApplication
public class BootApplication  implements WebMvcConfigurer, ApplicationContextAware {

	private static String agentPath = "C:\\Users\\prometheus\\Desktop\\work\\agentjava\\target\\agent-java-1.0-SNAPSHOT.jar";


	private ApplicationContext applicationContext;

	public static void main(String[] args) {
//		List<VirtualMachineDescriptor> list = VirtualMachine.list();
//		list.forEach(t -> {
//			System.out.println("VirtualMachineDescriptor:" + t.displayName());
//			if (t.displayName().endsWith("com.best.java.BootApplication")) {
//				VirtualMachine virtualMachine = null;
//				try {
//					virtualMachine = VirtualMachine.attach(t.id());
//					virtualMachine.loadAgent(agentPath);
//					virtualMachine.detach();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		SpringApplication.run(BootApplication.class, args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(applicationContext.getBean(LogInterceptor.class)).addPathPatterns("/**");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

	}
}
