package pse.adp.abstraction;

import java.util.LinkedList;

public class Observable {
	
	protected LinkedList<Observer> observers;
	
	protected Observable(){
		observers = new LinkedList<Observer>();
		
	}
	
	protected void notifyEvent(Event ev){
		synchronized (observers){
			for (Observer obs: observers){
				obs.notifyEvent(ev);
			}
		}
	}

	public void addObserver(Observer obs){
		synchronized (observers){
			observers.add(obs);
		}
	}

	public void removeObserver(Observer obs){
		synchronized (observers){
			observers.remove(obs);
		}
	}
}
