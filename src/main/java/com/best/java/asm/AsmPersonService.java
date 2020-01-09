package com.best.java.asm;

import com.best.java.asm.anno.AsmChangeFiled;
import com.best.java.asm.anno.AsmTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: xjxu3
 * @Date: 2019/12/12 9:43
 * @Description:
 */
@Component
public class AsmPersonService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(AsmPersonService.class);

	@AsmChangeFiled
	private int thresholdYoung;

	private String name;

	@AsmTime
	public void show(String string) {
		System.out.println("hello");
//		LOGGER.info("my name is " + name);
//		LOGGER.info("default young age is  " + thresholdYoung);
	}

	public int getThresholdYoung() {
		return thresholdYoung;
	}

	public void setThresholdYoung(int threshold_young) {
		this.thresholdYoung = threshold_young;
	}

	@AsmTime
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
