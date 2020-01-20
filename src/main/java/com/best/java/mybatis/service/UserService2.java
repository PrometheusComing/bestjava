package com.best.java.mybatis.service;

import com.best.java.mybatis.entity.User;
import com.best.java.mybatis.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xjxu3
 * @Date: 2020/1/20 17:11
 * @Description:
 */
@Service
public class UserService2 {

	@Autowired
	private UserMapper userMapper;

	@Transactional(propagation = Propagation.NESTED)
	public int addUserTest2(User user) {
		userMapper.addUser(user);
//		throw new RuntimeException();
		return 1;
	}
}
