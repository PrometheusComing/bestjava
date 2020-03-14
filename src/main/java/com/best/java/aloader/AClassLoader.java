package com.best.java.aloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 17:14
 * @Description:
 */
public class AClassLoader extends ClassLoader {


	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String[] na = name.split("\\.");
		Path path = Paths.get("C:\\Users\\prometheus\\Desktop\\work\\bestjava\\target\\classes\\com\\best\\java\\aloader\\"
				+ na[na.length-1] + ".class");
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
			return defineClass(name, bytes,0,bytes.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 部分破坏了双亲委派
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if ("com.best.java.aloader.AEntity".equals(name)) {
			return this.findClass(name);
		}
		return super.loadClass(name);
	}
}
