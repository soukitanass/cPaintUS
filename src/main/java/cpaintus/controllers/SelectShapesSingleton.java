package cpaintus.controllers;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class SelectShapesSingleton extends Observable<CenterPaneController> {

	private static SelectShapesSingleton instance = null;

	private SelectShapesSingleton() {

	}

	public static SelectShapesSingleton getInstance() {
		if (instance == null) {
			instance = new SelectShapesSingleton();
		}

		return instance;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.SELECT_SHAPES);
		}

	}
	public void notifyUnselectObsevers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.UNSELECT_SHAPES);
		}
	}

}
