package com.best.java.controller;

import com.best.java.asm.AsmPersonService;
import com.best.java.asm.vmethod.BeanWithAsmTime;
import com.best.java.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2019/12/9 19:59
 * @Description:
 */
@RestController
@RequestMapping("/best/java")
//@MyAnno(id="1")
public class TestController {

	@Autowired
	private RequestService requestService;

	@Autowired
	private AsmPersonService asmPersonService_Tmp;

	@Autowired
	private BeanWithAsmTime beanWithAsmTime;

	@Autowired
	LoadBalancerClient loadBalancerClient;


	@Autowired
	private DiscoveryClient discoveryClient;


	private Logger logger = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/helloWithAnimal",method = RequestMethod.POST)
	public String hello(Animal animal) {
		try {
			System.out.println(animal.getName());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello";
	}

	@RequestMapping(value = "/hello",method = RequestMethod.POST)
	public String helloUser(@RequestParam String userName, @RequestParam String UserId) {
		// 负载均衡
		ServiceInstance serviceInstance =  loadBalancerClient.choose("best-java");
		logger.info("loadBalancerClient get ip: {},port:{}",serviceInstance.getHost(),serviceInstance.getPort());
		List<ServiceInstance> list = discoveryClient.getInstances("best-java");
		list.forEach(t -> {
			logger.info("discoveryClient get ip: {},port:{}",t.getHost(),t.getPort());
		});
		String service = requestService.getHello();
		logger.info("userId: {} ,userName: {} request helloUser",UserId,userName);
		return service;
	}

	@RequestMapping(value = "/helloAsm",method = RequestMethod.GET)
	public String helloAsm() {
		asmPersonService_Tmp.show("ll");
		return "helloAsm";
	}

	@RequestMapping(value = "/helloBeanAsm",method = RequestMethod.GET)
	public String helloBeanAsm() {
		beanWithAsmTime.execute();
		return "helloBeanAsm";
	}

	@RequestMapping(value = "/agent",method = RequestMethod.GET)
//	@PrintTime
	public String agent() {
		try {
			System.out.println("agent processing");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "agent finish";
	}
}
