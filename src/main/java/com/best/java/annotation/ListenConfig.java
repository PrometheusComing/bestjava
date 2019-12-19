package com.best.java.annotation;

		import java.lang.annotation.ElementType;
		import java.lang.annotation.Retention;
		import java.lang.annotation.RetentionPolicy;
		import java.lang.annotation.Target;

/**
 * @Author: xjxu3
 * @Date: 2019/12/11 10:12
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ListenConfig {
}
