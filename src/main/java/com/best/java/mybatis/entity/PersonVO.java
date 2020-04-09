package com.best.java.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:48
 * @Description: VO类
 */
@Getter
@Setter
@JsonIgnoreProperties(value = {"javassistProxyFactory", "handler"})
@ApiModel(value = "PersonVO", description = "用户信息VO")
public class PersonVO extends Person {

	//用户拥有的车，和用户是一对多的关系
	@ApiModelProperty(name = "cars", value = "车辆信息集合")
	private List<Car> cars;
	//地址信息，和用户是一对一的关系
	@ApiModelProperty(name = "address", value = "地址信息")
	private Address address;
}
