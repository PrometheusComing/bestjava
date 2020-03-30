package com.best.java.annotation;

/**
 * @Author: xjxu3
 * @Date: 2020/3/21 11:16
 * @Description:
 */
public @interface OpLog {

	// 操作类型
	EnumOperation op() default EnumOperation.OTHER;

	// 操作模块
	String opModul() default "";

}
