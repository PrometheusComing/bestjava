package com.best.java.factoryPattern;

public class NYPizzStore extends PizzStore {

	@Override
	protected Pizz createPizz(String str) {
		return new NYPizz();
	}
}
