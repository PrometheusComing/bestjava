package com.best.java.mybatis.controller;

import com.alibaba.druid.util.StringUtils;
import com.best.java.mybatis.entity.User;
import com.best.java.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
 *
 *    p.s.关于乱码
 *    1、如果提交方式为post，想不乱码，只需要在服务器端设置request对象的编码即可，客户端以哪种编码提交的，服务器端的request
 *    对象就以对应的编码接收，比如客户端是以UTF-8编码提交的，那么服务器端request对象就以UTF-8编码接收
 *    (request.setCharacterEncoding("UTF-8"))，springmvc里的CharacterEncodingFilter可以做到
 *
 *    2、如果提交方式为get，设置request对象的编码是无效的，request对象还是以默认的ISO8859-1编码接收数据，因此要想不乱码，
 *    只能在接收到数据后再手工转换或者修改tomcat的server.xml(当然tomcat8以后，默认编码就是utf-8了)
 */
@RestController
@RequestMapping("/best/java")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getUserById",method = RequestMethod.POST)
	// id放在url后面key value形式或者直接放在body里都行,但是只能获取Content-Type: application/x-www-form-urlencoded
	// 如果使用postman里的raw，且Content-Type为application/json请求,放在body里是无法取出的
	public User getUser(@RequestParam(value = "id",required = true) int id) {
		return userService.getUserById(id);
	}

	// @PathVariable 和@RequestParam都是获取参数，前者是占位符的方式，后者是key value的方式
	@RequestMapping(value = "/{userId}",method = RequestMethod.GET)
	public User getUserByPathValue(@PathVariable(value = "userId") int id) {
		return userService.getUserById(id);
	}

	// 上传文件
	@PostMapping("/uploadPhoto")
	public String returnPhoto(MultipartHttpServletRequest mhs) throws IOException{
		MultipartFile multipartFile = mhs.getFile("file");
		return userService.uploadFile(multipartFile);
	}

	@RequestMapping(value = "/addUser",method = RequestMethod.POST)
	// 测试参数转化，这种情况id只能放在url后面，body里是user的json
	// 使用@RequestBody,请求中需要设置Content-Type为application/json
	public int addUser(@RequestParam(value = "id",required = true) int id,@RequestBody User user) {
		System.out.println(id);
		return userService.addUser(user);
	}


	@RequestMapping(value = "/addUserTest",method = RequestMethod.POST)
	public int testTransactional(@RequestBody User user){
		return userService.addUserTest(user);
	}
}
