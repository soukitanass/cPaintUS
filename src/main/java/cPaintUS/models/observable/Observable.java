package cPaintUS.models.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {
	private List<T> observerList;
	
	public Observable(){
		this.observerList = new ArrayList<T>();
	}
	
	public void register(T obj) {
		if(!this.observerList.contains(obj)) {
			observerList.add(obj);
		}
	}
	
	public boolean unregister(T obj) {
		return this.observerList.remove(obj);
	}
	
	public void unregisterAll() {
		this.observerList.clear();
	}
	
	public List<T> getObserverList() {
		return this.observerList;
	}
	
	public abstract void notifyAllObservers();
	
}
