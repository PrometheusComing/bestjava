package com.best.java.pattern.observer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class WeatherSubject implements Subject {

	private List<Observer> list;

	private float temp;

	private float humi;

	private float pressure;

	WeatherSubject() {
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
		list.forEach(t->t.update(this));
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
