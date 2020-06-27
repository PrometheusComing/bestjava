package com.best.java.mybatis.mappers;

import com.best.java.mybatis.entity.LogEntity;
import org.apache.ibatis.annotations.Insert;

/**
 * @Author: xjxu3
 * @Date: 2020/6/16 11:04
 * @Description:
 */
public interface LogMapper {
	@Insert("insert into log(id,operation) values(#{id},#{operation})")
	int addLog(LogEntity logEntity);
}
