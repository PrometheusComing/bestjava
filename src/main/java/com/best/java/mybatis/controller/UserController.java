package com.best.java.mybatis.controller;

import com.best.java.mybatis.entity.User;
import com.best.java.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xjxu3
 * @Date: 2020/1/20 15:24
 * @Description:
 * 事务总结：
 * 1.只能应用于public方法
 * 2.事务开启 ，或者是基于接口的 或者是基于类的代理被创建。它为我们的每个class生成一个代理对象。只有在代理对象之间进行调用时，
 * 可以触发切面逻辑，而同一个类之间使用的是原对象。所以在同一个类中一个方法调用另一个有事务的方法，事务是不会起作用的。
 * spring建议加在具体实现类上
 * 3.常用场景示例分析
 *
 */
@RestController
@RequestMapping("/best/java")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getUserById",method = RequestMethod.POST)
	public User getUser(@RequestParam(value = "id",required = true) int id) {
		return userService.getUserById(id);
	}

	@RequestMapping(value = "/addUser",method = RequestMethod.POST)
	public int addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@RequestMapping(value = "/addUserTest",method = RequestMethod.POST)
	public int testTransactional(@RequestBody User user){
		return userService.addUserTest(user);
	}
}
