package com.best.java.controller;

import com.best.java.PrintTime;
import com.best.java.annotation.MyAnno;
import com.best.java.asm.AsmPersonService;
import com.best.java.domain.Animal;
import com.best.java.father.Father;
import com.best.java.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xjxu3
 * @Date: 2019/12/9 19:59
 * @Description:
 */
@RestController
@RequestMapping("/best/java")
@MyAnno(id="1")
public class TestController {

	@Autowired
	private RequestService requestService;

//	@Autowired
//	private AsmPersonService asmPersonService_Tmp;


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
		String service = requestService.getHello();
		logger.info("userId: {} ,userName: {} request helloUser",UserId,userName);
		return service;
	}

//	@RequestMapping(value = "/helloAsm",method = RequestMethod.GET)
//	public String helloAsm() {
//		asmPersonService_Tmp.show("ll");
//		return "helloAsm";
//	}

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
