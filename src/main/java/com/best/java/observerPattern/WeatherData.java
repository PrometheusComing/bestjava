package com.best.java.observerPattern;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {

	private List<Observer> list;

	private float temp;

	private float humi;

	private float pressure;

	WeatherData () {
		list = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer observer) {
		list.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		int index = list.indexOf(observer);
		if (index >= 0) {
			list.remove(index);
		}
	}

	@Override
	public void notifyObservers() {
		list.forEach(t->t.update(temp,humi,pressure));
	}

	public void changeHappen() {
		notifyObservers();
	}

	public void setChange(float temp,float humi,float pressure) {
		this.temp = temp;
		this.humi = humi;
		this.pressure = pressure;
		changeHappen();
	}
}
