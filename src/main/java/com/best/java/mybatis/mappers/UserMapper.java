package com.best.java.mybatis.mappers;

import com.best.java.mybatis.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author: xjxu3
 * @Date: 2020/1/20 15:13
 * @Description:
 */
public interface UserMapper {

	@Select("select id,name,age,del_flag from user where id = #{id}")
	@Results(id = "learnMap", value = {
			@Result(column = "id", property = "id", javaType = int.class),
			@Result(property = "name", column = "name", javaType = String.class),
			@Result(property = "age", column = "age", javaType = int.class),
			@Result(property = "delFlag", column = "del_flag", javaType = int.class)
	})
	User getUserByid(@Param(value = "id") int id);

	@Insert("insert into user(id,name,age,del_flag) values(#{id},#{name},#{age},#{delFlag})")
	int addUser(User user);
}
