package com.best.java.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: xjxu3
 * @Date: 2020/4/9 10:14
 * @Description:
 */
@Getter
@Setter
@ApiModel(value = "Car", description = "车辆信息")
@JsonIgnoreProperties(value = {"javassistProxyFactory", "handler"})
public class Car {
	@ApiModelProperty(name = "cars", value = "车辆id")
	private Long id;
	@ApiModelProperty(name = "cars", value = "车辆颜色")
	private String color;
	@ApiModelProperty(name = "cars", value = "车辆名称")
	private String name;
	//用户id
	@ApiModelProperty(name = "cars", value = "所属用户id")
	private Long personId;
}
