package cpaintusc.models.shapes;

import cpaintusc.models.BoundingBox;
import cpaintusc.models.Point;
import cpaintusc.models.observable.IObserver;
import cpaintusc.models.observable.Observable;
import cpaintusc.models.observable.ObservableList;

public class ShapeEditor extends Observable<IObserver> {
	private ShapesDictionnary shapesDict;
	private Shape shapeToEdit;
	private BoundingBox boundingBox;

	private ShapeEditor() {
		shapesDict = ShapesDictionnary.getInstance();
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
		if (shape.getShapeType() == ShapeType.LINE) {
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
