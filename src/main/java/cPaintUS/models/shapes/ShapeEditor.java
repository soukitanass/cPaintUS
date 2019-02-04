package cPaintUS.models.shapes;

import cPaintUS.models.BoundingBox;
import cPaintUS.models.Point;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;

public class ShapeEditor extends Observable<IObserver> {
	private ShapesDict shapesDict;
	private Shape shapeToEdit;
	private BoundingBox boundingBox;

	private ShapeEditor() {
		shapesDict = ShapesDict.getInstance();
		shapeToEdit = null;
		boundingBox = BoundingBox.getInstance();
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
		updateBoundingBox(shape);
		notifyAllObservers();
	}
	
	private void updateBoundingBox(Shape shape) {
		boundingBox.setOrigin(shape.getX(), shape.getY());
		boundingBox.setRotation(shape.getRotation());
		if (shape.getShapeType() == ShapeType.Line) {
			boundingBox.updateBoundingBox(new Point(((Line)shape).getX2(),
					((Line)shape).getY2()));
		} else {
			boundingBox.updateBoundingBox(new Point(shape.getX() + shape.getWidth(),
					shape.getY() + shape.getHeight()));
		}
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
