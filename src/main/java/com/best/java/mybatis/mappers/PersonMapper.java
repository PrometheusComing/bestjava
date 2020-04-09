package com.best.java.mybatis.mappers;

import com.best.java.mybatis.entity.Person;
import com.best.java.mybatis.entity.PersonVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:19
 * @Description:
 */
public interface PersonMapper {

	@Select("SELECT id,nick_name name,address_id addressId FROM person where id = #{id}")
	Person findPersonById(Long id);

	@Select("SELECT id,nick_name name,address_id addressId FROM person where id = #{id}")
	@Results({
			// 一个column对应一个property，且名称一致的时候要不要@Result都行。但是查出来的column和property不一致，或者一个
			// column对应多个property时，就一定要写@Result。这里查出来的addressId对应了两个property,不使用@Result的话，会导致
			// 字段丢失。比如去除@Result(column="addressId",property="addressId"),那结果就是PersonVO里的id为null
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "addressId", property = "addressId"),
			@Result(column = "addressId", property = "address",
					one = @One(select = "com.best.java.mybatis.mappers.AddressMapper.findAddressById", fetchType = FetchType.LAZY)),
			@Result(column = "id", property = "cars",
					many = @Many(select = "com.best.java.mybatis.mappers.CarMapper.findCarByPersonId", fetchType = FetchType.LAZY))

	})
		// results里的column对应select里查出来的字段
	PersonVO findPersonVOById(Long id);

	@Select("SELECT id,nick_name name,address_id addressId FROM person")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "addressId", property = "addressId"),
			@Result(column = "addressId", property = "address",
					one = @One(select = "com.best.java.mybatis.mappers.AddressMapper.findAddressById", fetchType = FetchType.LAZY)),
			@Result(column = "id", property = "cars",
					many = @Many(select = "com.best.java.mybatis.mappers.CarMapper.findCarByPersonId", fetchType = FetchType.LAZY))
	})
		// results里的column对应select里查出来的字段
	List<PersonVO> getAllPerson();
}
