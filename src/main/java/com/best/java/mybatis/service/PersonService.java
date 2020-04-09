package com.best.java.mybatis.service;

import com.best.java.mybatis.entity.Person;
import com.best.java.mybatis.entity.PersonVO;
import com.best.java.mybatis.mappers.AddressMapper;
import com.best.java.mybatis.mappers.CarMapper;
import com.best.java.mybatis.mappers.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:36
 * @Description:
 */
@Service
public class PersonService {

	@Autowired
	private CarMapper carMapper;

	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private AddressMapper addressMapper;

	public Person getPersonById(long id) {
		return personMapper.findPersonById(id);
	}

	public PersonVO getPersonVOById(long id) {
		return personMapper.findPersonVOById(id);
	}

	public List<PersonVO> getAllPerson() {
		return personMapper.getAllPerson();
	}
}
