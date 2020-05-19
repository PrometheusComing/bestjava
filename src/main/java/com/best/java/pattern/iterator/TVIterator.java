package com.best.java.pattern.iterator;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 10:58
 * @Description:
 */
public interface TVIterator {
	void setChannel(int i);      //设置初始频道
	Object currentChannel();     //返回当前频道名
	void next();                 //下一个频道
	void prevoius();             //上一个频道
	boolean isLast();            //是否为最后一个频道
	boolean isFirst();           //是否为第一个频道
}
