package com.best.java.observerPattern;

public interface Subject {

	void registerObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers();
}
