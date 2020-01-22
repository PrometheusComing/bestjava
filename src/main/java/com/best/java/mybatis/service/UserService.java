package com.best.java.mybatis.service;

import com.alibaba.druid.util.StringUtils;
import com.best.java.mybatis.mappers.UserMapper;
import com.best.java.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author: xjxu3
 * @Date: 2020/1/20 15:24
 * @Description:
 */
@Service
public class UserService {

	//JDK8 新format，线程安全
	public static DateTimeFormatter FOR_MAT =  DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

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
			System.out.println("嵌套事务或新事务失败回滚");
			System.out.println("执行其他操作，不影响当前事务");
		}
//		throw new RuntimeException();
		return 1;
	}

	public String uploadFile(MultipartFile multipartFile) throws IOException {
		String way = "C:\\Users\\prometheus\\Desktop";
		String timeString =FOR_MAT.format(LocalDateTime.now());
		StringBuilder newFileName = new StringBuilder(UUID.randomUUID().toString()).append("-").append(timeString);
		if (multipartFile.getOriginalFilename().lastIndexOf(".") > -1) {
			// 添加文件后缀
			newFileName.append(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
		}
		Path destination = Paths.get(way + "/" + newFileName.toString());
//		Files.createFile(destination);
//		Files.write(destination,multipartFile.getBytes());
		multipartFile.transferTo(destination.toFile());
		return newFileName.toString();
	}
}
