package cPaintUS.models.shapes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;

public class ShapesDict extends Observable<IObserver> {
	private static ShapesDict instance = null;
	private HashMap<String, Shape> shapesDict;
	private ShapeFactory shapeFactory;

	private ShapesDict() {
		this.shapesDict = new HashMap<String, Shape>();
		this.shapeFactory = ShapeFactory.getInstance();
	}

	public static ShapesDict getInstance() {
		if (instance == null) {
			instance = new ShapesDict();
		}

		return instance;
	}

	public Collection<Shape> getShapes() {
		return shapesDict.values();
	}

	public List<Shape> getShapesList() {

		return new ArrayList<Shape>(this.getShapes());
	}

	public void addShape(Shape shape) {
		if(shape != null)
			shapesDict.put(shape.getShapeId(), shape);
		else
			System.out.println("addShape error : Unknown shape");
	}

	public void addListShapes(List<Shape> shapesList) {
		for (Shape shape : shapesList) {
			this.addShape(shape);
		}
	}

	public void clearShapes() {
		shapesDict.clear();
		shapeFactory.clear();
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.SHAPES_LOAD);
		}

	}
}