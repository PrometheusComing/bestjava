package com.best.java.pattern.observer;

public class Entran {
	public static void main (String...args) {
		WeatherSubject weatherSubject = new WeatherSubject();
		WeatherObserver weatherObserver = new WeatherObserver();
		weatherSubject.registerObserver(weatherObserver);
		weatherSubject.setChange(70.0f,78.0f,43.1f);
	}
}
