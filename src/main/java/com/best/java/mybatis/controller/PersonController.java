package com.best.java.mybatis.controller;

import com.best.java.mybatis.entity.Address;
import com.best.java.mybatis.entity.Person;
import com.best.java.mybatis.entity.PersonVO;
import com.best.java.mybatis.service.PersonService;
import com.best.java.pattern.command.People;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:13
 * @Description:
 */
@Api(value = "用户接口", tags = {"1.0.0"})
@RestController
public class PersonController {

	@Autowired
	private PersonService personService;


	@ApiOperation(value = "用户查询", notes = "根据ID查询用户信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "long")
//	多个参数时，@ApiImplicitParams({ @ApiImplicitParam(paramType = "form", dataType = "Long", name = "id", value = "信息id", required = true) })
	@RequestMapping(value = "/getPersonById", method = RequestMethod.GET)
	public Person getPerson(@RequestParam(value = "id", required = true) long id) {
		return personService.getPersonById(id);
	}

	// 使用懒加载
	@ApiOperation(value = "用户详细信息查询", notes = "根据ID查询用户信息、地址信息、车辆信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "long")
	@RequestMapping(value = "/getPersonVOLazyById", method = RequestMethod.GET)
	public PersonVO getPersonVOLazy(@RequestParam(value = "id", required = true) long id) {
		PersonVO personVO = personService.getPersonVOById(id);
		Address address = personVO.getAddress();
		System.out.println(address == null ? "null" : address.getCity());
		System.out.println(personVO.getId());
		return personVO;
	}

	// 不使用懒加载,直接返回的话会序列化，导致加载，所以这里返回null来测试
	// 所以RestController和它是冲突的，解决方法有，但懒得搞
	@RequestMapping(value = "/getPersonVOById", method = RequestMethod.GET)
	public PersonVO getPersonVO(@RequestParam(value = "id", required = true) long id) {
		PersonVO personVO = personService.getPersonVOById(id);
		return null;
	}


	@ApiOperation(value = "所有用户详细信息查询", notes = "查询所有用户信息、地址信息、车辆信息")
	@RequestMapping(value = "/getAllPerson", method = RequestMethod.GET)
	public List<PersonVO> getAllPerson() {
		return personService.getAllPerson();
	}
}
