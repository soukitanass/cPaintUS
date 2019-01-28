package cPaintUS.controllers;

import cPaintUS.models.observable.IAddObserver;
import cPaintUS.models.observable.Observable;

public class AddTextSingleton extends Observable<CenterPaneController>{
	
	private static AddTextSingleton INSTANCE = null;
	private String text;
	
	private AddTextSingleton() {
		
	}
	
	public static AddTextSingleton getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new AddTextSingleton();
		}
		return INSTANCE;	
	}

	@Override
	public void notifyAllObservers() {
		for (IAddObserver obs : getObserverList()) {
			obs.update(getText());
		}
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
