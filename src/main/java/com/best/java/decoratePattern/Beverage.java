package com.best.java.decoratePattern;

public abstract class Beverage {
	private String description = "Unknown Beverage";

	public void setDescription(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return this.description;
	}

	public abstract double cost();
}
