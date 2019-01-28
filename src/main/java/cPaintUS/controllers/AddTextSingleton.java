package cPaintUS.controllers;

import cPaintUS.models.observable.IAddTextObserver;
import cPaintUS.models.observable.Observable;

public class AddTextSingleton extends Observable<IAddTextObserver>{
	
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
		for (IAddTextObserver obs : getObserverList()) {
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
