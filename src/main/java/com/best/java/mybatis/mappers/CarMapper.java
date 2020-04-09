package com.best.java.mybatis.mappers;

import com.best.java.mybatis.entity.Car;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:19
 * @Description:
 */
public interface CarMapper {
	@Select("SELECT id,name,color,person_id personId FROM car WHERE person_id = #{personId}")
	List<Car> findCarByPersonId(Long personId);
}
