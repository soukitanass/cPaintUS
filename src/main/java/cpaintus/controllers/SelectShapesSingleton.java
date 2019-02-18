package cpaintus.controllers;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;

public class SelectShapesSingleton extends Observable<IObserver> {

	private static SelectShapesSingleton instance = null;
	private Shape selectedShape;

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
			obs.update(ObservableList.GROUP_SHAPES);
		}
	}

	public void notifyUngroupObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.UNGROUP_SHAPES);
		}
	}

	public void notifyUnselectShapeObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.UNSELECT_SHAPE);
		}
	}

	public void setSelectedShape(Shape shape) {
		selectedShape = shape;
	}

	public Shape getSelectedShape() {
		return selectedShape;
	}

}
