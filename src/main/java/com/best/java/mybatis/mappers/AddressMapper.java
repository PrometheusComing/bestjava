package com.best.java.mybatis.mappers;

import com.best.java.mybatis.entity.Address;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:19
 * @Description:
 */
public interface AddressMapper {

	@Select("SELECT id,province,city FROM address WHERE id = #{id}")
	Address findAddressById(Long id);
}
