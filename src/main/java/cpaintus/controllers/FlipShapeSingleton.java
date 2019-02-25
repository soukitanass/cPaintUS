package cpaintus.controllers;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class FlipShapeSingleton extends Observable<IObserver> {

	private static FlipShapeSingleton instance = null;

	private FlipShapeSingleton() {
	}

	public static FlipShapeSingleton getInstance() {
		if (instance == null) {
			instance = new FlipShapeSingleton();
		}

		return instance;
	}

	public void notifyFlipVerticallyObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.FLIP_VERTICAL);
		}

	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.FLIP_HORIZONTAL);
		}

	}
}
