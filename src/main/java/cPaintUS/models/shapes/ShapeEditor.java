package cPaintUS.models.shapes;

import cPaintUS.controllers.drawers.DrawerStrategyContext;
import cPaintUS.controllers.drawers.DrawerStrategyContext.SingletonHelper;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;

public class ShapeEditor extends Observable<IObserver> {
	private ShapesDict shapesDict;
	private Shape shapeToEdit;

	private ShapeEditor() {
		shapesDict = ShapesDict.getInstance();
		shapeToEdit = null;
	}

	private static class SingletonHelper {
		private static final ShapeEditor INSTANCE = new ShapeEditor();
	}

	public static ShapeEditor getInstance() {
		return SingletonHelper.INSTANCE;
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
