package com.best.java.fanxing;

import java.util.Date;

/**
 * @Author: xjxu3
 * @Date: 2019/12/4 14:15
 * @Description:
 */

class DateInter extends Pair<Date> {
	@Override
	public void setValue(Date value) {
		super.setValue(value);
	}

	@Override
	public Date getValue() {
		return new Date();
	}
}
