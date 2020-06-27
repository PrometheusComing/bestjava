package com.best.java.mybatis.service;

import com.best.java.mybatis.entity.LogEntity;
import com.best.java.mybatis.mappers.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xjxu3
 * @Date: 2020/6/16 11:01
 * @Description:
 */
@Service
public class LogService {
	@Autowired
	private LogMapper logMapper;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	@Transactional
	public int addLog(LogEntity logEntity) {
		return logMapper.addLog(logEntity);
	}
}
