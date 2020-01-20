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
 * 3.常用场景示例分析，使用serviceA,methodA,serviceB,methodB进行说明
 *
 *  事务传播行为：
 *  Propagation.REQUIRED：当前无事务则创建新事务，当前有事务则加入事务，是默认级别
 *  Propagation.REQUIRES_NEW:无论当前是否有事务，自己创建一个新事务，并且挂起当前事务
 *  Propagation.NESTED：嵌套事务，作为当前事务的嵌套事务，相当于子事务
 *
 *  1.methodA，methodB都是REQUIRED级别
 *    methodB的异常自己不捕获的情况下，不管A有没有捕获，A,B都会回滚（因为是同一个事务）
 *    methodB无异常，methodA发生异常，不捕获的情况下，A，B都会回滚（因为是同一个事务）
 *  2.methodA是REQUIRED级别，methodB是REQUIRES_NEW级别
 *    methodB的异常自己不捕获，B将进行回滚，methodB的异常methodA不捕获，A也回滚，如果捕获了，A不回滚（因为是2个事务）
 *    methodA的异常，将造成A回滚，对B没有任何影响（因为是2个事务）
 *  3.methodA是REQUIRED级别，methodB是NESTED级别
 *    methodB的异常自己不捕获，B将进行回滚，methodB的异常methodA不捕获，A也回滚，如果捕获了，A不回滚（和上面一样）
 *    methodA的异常，将造成A回滚，并且B也要回滚
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
