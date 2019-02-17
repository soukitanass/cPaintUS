package cpaintus.controllers;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;

public class DeleteShapeSingleton extends Observable<IObserver> {

	private static DeleteShapeSingleton instance = null;
	private Shape shapeToDelete;

	private DeleteShapeSingleton() {
	}

	public static DeleteShapeSingleton getInstance() {
		if (instance == null) {
			instance = new DeleteShapeSingleton();
		}

		return instance;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.DELETE_SHAPE);
		}
	}

	public void setShapeToDelete(Shape shape) {
		shapeToDelete = shape;
	}

	public Shape getShapeToDelete() {
		return shapeToDelete;
	}

}
