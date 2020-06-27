package com.best.java;

//import com.best.java.aop.LogInterceptor;
import com.best.java.aop.LogInterceptor;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
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
@MapperScan("com.best.java.mybatis.mappers")
@EnableEurekaClient
@EnableFeignClients
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
		registry.addInterceptor(applicationContext.getBean(LogInterceptor.class)).addPathPatterns("/**")
				.excludePathPatterns("/toIndex","/index");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//访问http://localhost:18081/toIndex就跳转到index页面了
		registry.addViewController("/toIndex").setViewName("index");
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String os = System.getProperty("os.name");

		//如果是Windows系统
		if (os.toLowerCase().startsWith("win")) {
			registry.addResourceHandler("/app_file/**")
					.addResourceLocations("file:" + "C:/user");
		} else {  //linux 和mac
			registry.addResourceHandler("/app_file/**")
					.addResourceLocations("file:" + "/user/app/") ;
		}
		//将请求中的/my换成classpath下的/my/去获取资源.其实就是映射资源的真正路径来获取资源
//		registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
//		registry.addResourceHandler("/photo/**").addResourceLocations("file:C:\\Users\\prometheus\\Desktop\\");
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
     //可以对返回数据进行封装规整，ResponseBodyWrapHandler自己实现
//		handlers.add(new ResponseBodyWrapHandler());
	}
}
