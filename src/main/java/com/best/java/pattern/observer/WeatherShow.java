package com.best.java.pattern.observer;

public class WeatherShow implements Observer,DisplayElement {

	private float temp;

	private float humi;

	private float pressure;

	private WeatherData weatherData;

	WeatherShow(WeatherData weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println("temp is :" + temp);
		System.out.println("humi is :" + humi);
		System.out.println("pressure is :" + pressure);
	}

	@Override
	public void update(float temp, float humi, float pressure) {
		this.temp = temp;
		this.humi = humi;
		this.pressure = pressure;
		display();
	}
}
