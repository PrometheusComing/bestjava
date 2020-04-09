package com.best.java.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:13
 * @Description: 实体类，对应数据库表
 */
@Getter
@Setter
@JsonIgnoreProperties(value = {"javassistProxyFactory", "handler"})
@ApiModel(value = "Person", description = "用户信息")
public class Person {

	@ApiModelProperty(name = "id", value = "用户id")
	private Long id;

	@ApiModelProperty(name = "name", value = "用户名")
	private String name;

	@ApiModelProperty(name = "addressId", value = "地址id")
	private Long addressId;

}
