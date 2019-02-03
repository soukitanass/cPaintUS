package cPaintUS.models.shapes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;

public class ShapesDict extends Observable<IObserver> {
	private static ShapesDict instance = null;
	private LinkedHashMap<String, Shape> shapesDict;
	private ShapeFactory shapeFactory;

	private ShapesDict() {
		this.shapesDict = new LinkedHashMap<String, Shape>();
		this.shapeFactory = ShapeFactory.getInstance();
	}

	public static ShapesDict getInstance() {
		if (instance == null) {
			instance = new ShapesDict();
		}

		return instance;
	}

	public List<Shape> getShapesList() {
		return new ArrayList<Shape>(shapesDict.values());
	}

	public void addShape(Shape shape) {
		if(shape != null) {
			shapesDict.put(shape.getShapeId(), shape);
			notifyAddAllObservers();
		}
		else
			System.out.println("addShape error : Unknown shape");
	}
	
	public void addShapeSilent(Shape shape) {
		if(shape != null) {
			shapesDict.put(shape.getShapeId(), shape);
		}
		else
			System.out.println("addShape error : Unknown shape");
	}
		
	public void addShapeSilentForList(Shape shape) {
		if(shape != null) {
			Shape temp = shapeFactory.getShape(shape.getShapeType(), true,0, 0, 0, 0, 0, 1, "#000000", "#000000", null);
			shape.setShapeId(temp.getShapeId());
			shape.setZ(shapeFactory.getTotalShapeNb());
			shapesDict.put(shape.getShapeId(), shape);			
		}
			
		else
			System.out.println("addShape error : Unknown shape");
	}

	public void addListShapes(List<Shape> shapesList) {
		for (Shape shape : shapesList) {
			this.addShapeSilentForList(shape);
		}
	}

	public void clearShapes() {
		shapesDict.clear();
		shapeFactory.clear();
		notifyAllObservers();
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.SHAPES_LOADED);
		}
	}
	
	public void notifyAddAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.SHAPE_ADDED);
		}
	}
}