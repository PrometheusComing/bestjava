package com.best.java.annotation;

/**
 * @Author: xjxu3
 * @Date: 2019/12/9 15:13
 * @Description:
 */
public class PasswordUtils {

	@MyAnno(id="47",description="Passwords must contain at least one numeric")
	public boolean validatePassword(String password) {
		return (password.matches("\\w*\\d\\w*"));
	}

	@MyAnno(id ="48")
	public String encryptPassword(String password) {
		return new StringBuilder(password).reverse().toString();
	}
}
