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
@ApiModel(value = "Address", description = "地址信息")
@JsonIgnoreProperties(value = {"javassistProxyFactory", "handler"})
public class Address {
	@ApiModelProperty(name = "id", value = "地址id")
	private Long id;
	@ApiModelProperty(name = "province", value = "省份")
	private String province;
	@ApiModelProperty(name = "city", value = "城市")
	private String city;
}
