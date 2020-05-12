package com.best.java.pattern.observer;

public class WeatherObserver implements Observer,DisplayElement {

	private float temp;

	private float humi;

	private float pressure;

	@Override
	public void display() {
		System.out.println("temp is :" + temp);
		System.out.println("humi is :" + humi);
		System.out.println("pressure is :" + pressure);
	}

	@Override
	public void update(Subject subject) {
		WeatherSubject weather = (WeatherSubject) subject;
		this.temp = weather.getTemp();
		this.humi = weather.getHumi();
		this.pressure = weather.getPressure();
		display();
	}
}
