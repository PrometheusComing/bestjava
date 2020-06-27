package com.best.java.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: xjxu3
 * @Date: 2020/6/16 11:02
 * @Description:
 */
@Getter
@Setter
@JsonIgnoreProperties(value = {"javassistProxyFactory", "handler"})
public class LogEntity {
	@ApiModelProperty(name = "id", value = "userId")
	private int id;
	//用户id
	@ApiModelProperty(name = "operation", value = "操作")
	private String operation;
}