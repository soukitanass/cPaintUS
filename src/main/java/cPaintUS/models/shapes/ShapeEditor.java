package cPaintUS.models.shapes;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;

public class ShapeEditor extends Observable<IObserver> {
	private static ShapeEditor instance = null;
	private ShapesDict shapesDict;
	private Shape shapeToEdit;

	private ShapeEditor() {
		shapesDict = ShapesDict.getInstance();
		shapeToEdit = null;
	}

	public static ShapeEditor getInstance() {
		if (instance == null) {
			instance = new ShapeEditor();
		}

		return instance;
	}
	
	public void edit(Shape shape) {
		shapeToEdit = shape;
		shapesDict.addShapeSilent(shape);
		notifyAllObservers();
	}
	
	public void done() {
		shapeToEdit = null;
	}
	
	public Shape getShapeToEdit() {
		return shapeToEdit;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.EDIT_SHAPE);
		}
	}
}
