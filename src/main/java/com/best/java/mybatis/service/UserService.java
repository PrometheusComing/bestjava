package com.best.java.mybatis.service;

import com.best.java.mybatis.mappers.UserMapper;
import com.best.java.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xjxu3
 * @Date: 2020/1/20 15:24
 * @Description:
 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService2 userService2;

	public User getUserById(int id){
		return userMapper.getUserByid(id);
	}

	public int addUser(User user){
		return userMapper.addUser(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int addUserTest(User user){
		int result = userMapper.addUser(user);
		user.setId(user.getId() + 1000);
		user.setName(user.getName() + "test2");

		try {
			userService2.addUserTest2(user);
		} catch (RuntimeException e) {
			System.out.println("嵌套事务失败回滚");
			System.out.println("执行其他操作，不影响当前事务");
		}
//		throw new RuntimeException();
		return 1;
	}
}
