package com.best.java.pattern.factory;

public class NYPizzStore extends PizzStore {

	@Override
	protected Pizz createPizz(String str) {
		return new NYPizz();
	}
}
