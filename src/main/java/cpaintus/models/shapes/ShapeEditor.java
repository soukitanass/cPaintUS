package cpaintus.models.shapes;

import cpaintus.models.BoundingBox;
import cpaintus.models.Point;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class ShapeEditor extends Observable<IObserver> {
	private Shape shapeToEdit;
	private BoundingBox boundingBox;
	private Boolean edittingZ;

	private ShapeEditor() {
		shapeToEdit = null;
		boundingBox = BoundingBox.getInstance();
		edittingZ = false;
	}

	private static class SingletonHelper {
		private static final ShapeEditor INSTANCE = new ShapeEditor();
	}

	public static ShapeEditor getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public void edit(Shape shape) {
		shapeToEdit = shape;
		updateBoundingBox(shape);
		notifyAllObservers();
	}

	public void editZ(Shape shape) {
		edittingZ = true;
		edit(shape);
	}

	private void updateBoundingBox(Shape shape) {
		boundingBox.setOrigin(shape.getX(), shape.getY());
		boundingBox.setRotation(shape.getRotation());
		if (shape.getShapeType() == ShapeType.LINE) {
			boundingBox.updateBoundingBox(new Point(((Line) shape).getX2(), ((Line) shape).getY2()));
		} else {
			boundingBox.updateBoundingBox(new Point(shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()));
		}
	}

	public void done() {
		shapeToEdit = null;
		edittingZ = false;
	}

	public Shape getShapeToEdit() {
		return shapeToEdit;
	}

	public Boolean edittingZ() {
		return edittingZ;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.EDIT_SHAPE);
		}
	}
}
