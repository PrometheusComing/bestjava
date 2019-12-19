package com.best.java.asm;

/**
 * @Author: xjxu3
 * @Date: 2019/12/12 14:26
 * @Description: 父类加载器并不是真的父类继承而是组合，app，ext的父类是url，app的父类加载器是ext
 * findClass()方法是在loadClass()方法中被调用的，当loadClass()方法中父加载器加载失败后，则会调用自己的findClass()方法来完成类加载
 * 所以不想违背双亲委派模型，则只需要重写findclass方法即可，如果想违背双亲委派模型，则还需要重写loadclass方法，不过也可以直接调用findClass
 */
public class ByteClassLoader extends ClassLoader {

	private byte[] bytes;

	public ByteClassLoader(byte[] bytes) {
		super();
		this.bytes = bytes;
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		if(bytes ==null) {
			throw new ClassNotFoundException();
		}
		return defineClass(name, bytes,0,bytes.length);
	}
}
