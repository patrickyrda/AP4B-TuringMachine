package model;
import java.util.List;
import java.util.ArrayList;

public class Subject {
	
	private List<Observer> observers = new ArrayList<>();
	
	public void addObserver(Observer obs) {
		observers.add(obs);
	}
	public void removeObserver(Observer obs) {
		observers.remove(obs);
	}
	public void notifyObservers(String msg) {
		for (Observer obs : observers) {
			obs.update(msg);
		}
	}
}
