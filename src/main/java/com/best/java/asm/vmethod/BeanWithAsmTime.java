package com.best.java.asm.vmethod;

import com.best.java.asm.anno.AsmTime;
import org.springframework.stereotype.Component;

/**
 * @Author: xjxu3
 * @Date: 2020/1/9 17:25
 * @Description:
 */
@Component
public class BeanWithAsmTime {

	@AsmTime
	public void execute() {
		System.out.println("just do it");
	}
}
