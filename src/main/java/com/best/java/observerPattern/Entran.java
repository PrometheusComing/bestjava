package com.best.java.observerPattern;

public class Entran {
	public static void main (String...args) {
		WeatherData weatherData = new WeatherData();
		WeatherShow weatherShow = new WeatherShow(weatherData);
		weatherData.setChange(70.0f,78.0f,43.1f);
	}
}
