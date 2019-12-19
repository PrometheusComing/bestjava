package com.best.java.commandpattern;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:19
 * @Description:
 */
public class People {

	public static void main(String[] args) {
		FridgeMachine fridgeMachine = new FridgeMachine();
		TVController tvController = new TVController(fridgeMachine);
		tvController.open();
		tvController.switchIt();
		tvController.close();
		TVMachine tvMachine = TVMachine.builder()
				.brand("samung")
				.price(1000)
				.producePlace("china").build();

//
		TVSonTest tvSonTest = TVSonTest.builder().myself("").brand("huawei").build();
//		tvSonTest.setBrand("");

//		tvMachine.setBrand("samung");
//		tvMachine.setPrice(2000);
//		tvMachine.setProducePlace("china");
		System.out.println(tvMachine.toString());

		tvController = new TVController(tvMachine);
		tvController.open();
		tvController.switchIt();
		tvController.close();

	}
}
