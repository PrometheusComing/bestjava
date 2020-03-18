package com.best.java.pattern.observer;

public interface Subject {

	void registerObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers();
}
