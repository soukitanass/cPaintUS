package cpaintus.models.shapes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class ShapesDictionnary extends Observable<IObserver> {
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String ERROR_MESSAGE = "addShape error : Unknown shape";
	private static ShapesDictionnary instance = null;
	private LinkedHashMap<String, Shape> shapesDict;
	private ShapeFactory shapeFactory;

	private ShapesDictionnary() {
		this.shapesDict = new LinkedHashMap<>();
		this.shapeFactory = ShapeFactory.getInstance();
	}

	public static ShapesDictionnary getInstance() {
		if (instance == null) {
			instance = new ShapesDictionnary();
		}

		return instance;
	}

	public LinkedHashMap<String, Shape> getShapesDict() {
		return shapesDict;
	}

	public void setShapesDict(LinkedHashMap<String, Shape> shapesDict) {
		this.shapesDict = shapesDict;
	}

	public List<Shape> getShapesList() {
		return new ArrayList<>(shapesDict.values());
	}

	public void addShape(Shape shape) {
		if(shape != null) {
			shapesDict.put(shape.getShapeId(), shape);
			notifyAddAllObservers();
		}
		else
			LOGGER.log(Level.INFO, ERROR_MESSAGE);
	}
	
	public void addShapeSilent(Shape shape) {
		if(shape != null) {
			shapesDict.put(shape.getShapeId(), shape);
		}
		else
			LOGGER.log(Level.INFO, ERROR_MESSAGE);
	}
		
	public void addShapeSilentForList(Shape shape) {
		if(shape != null) {
			Shape temp = shapeFactory.getShape(shape.getShapeType(), true, 0, 0, 0, 0, 0, 0, 0, 0, 1, "#000000", "#000000", "", "");
			shape.setShapeId(temp.getShapeId());
			shape.setZ(shapeFactory.getTotalShapeNb());
			shapesDict.put(shape.getShapeId(), shape);			
		}
		else
			LOGGER.log(Level.INFO, ERROR_MESSAGE);
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
	
	public void removeShape (Shape shape) {
		if(shapesDict.containsKey(shape.getShapeId())) {
			shapesDict.remove(shape.getShapeId());
		}
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
	
	public void notifyRemoveAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.SHAPE_REMOVED);
		}
	}
	
	public void removeShapeByType(ShapeType shapeType) {
		for(Shape shape: getShapesList()) {
			if(shape.getShapeType() == shapeType) {
				shapesDict.remove(shape.getShapeId(), shape);
			}
		}
		notifyRemoveAllObservers();
	}
}