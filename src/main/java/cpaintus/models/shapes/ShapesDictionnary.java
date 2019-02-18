package cpaintus.models.shapes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.Point;
import cpaintus.models.composite.ShapesGroup;

public class ShapesDictionnary extends Observable<IObserver> {
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String ERROR_MESSAGE = "addShape error : Unknown shape";
	private static ShapesDictionnary instance = null;
	private LinkedHashMap<String, Shape> shapesDict;
	private ShapeFactory shapeFactory;
	private String lastCreatedShapeId;

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
	
	public List<Shape> getFullShapesList() {
		List<Shape> fullList = new ArrayList<>();
		List<Shape> list = getShapesList();
		
		appendChildren(fullList, list);
		
		return fullList;
	}
	
	private void appendChildren(List<Shape> fullList, List<Shape> list) {
		for (Shape shape : list) {
			if (shape.getShapeType() == ShapeType.GROUP) {
				appendChildren(fullList, ((ShapesGroup) shape).getShapes());
			} else {
				fullList.add(shape);
			}
		}
	}
	
	public Shape getLastCreatedShape() {
		return shapesDict.get(lastCreatedShapeId);
	}

	public void addShape(Shape shape) {
		if (shape != null) {
			if (shape.getShapeType() == ShapeType.GROUP) {
				for (Shape child : ((ShapesGroup) shape).getShapes()) {
					removeShape(child, false);
				}
			}

			addShape(shape, true);
		}
	}
	
	public void addShape(Shape shape, List<ShapesGroup> parents) {
		if (parents.size() != 0) {
			for (ShapesGroup parent : parents) {
				parent.add(shape);
			}
			notifyAddAllObservers();
		} else {
			addShape(shape);
		}
	}

	public void addShape(Shape shape, boolean shouldNotify) {
		if (shape != null) {
			lastCreatedShapeId = shape.getShapeId();
			shapesDict.put(shape.getShapeId(), shape);
			if (shouldNotify)
				notifyAddAllObservers();
		} else
			LOGGER.log(Level.INFO, ERROR_MESSAGE);
	}

	public void addListShapes(List<Shape> shapesList) {
		for (Shape shape : shapesList) {
			if (shape != null) {
				Shape temp = shapeFactory.getShape(shape.getShapeType(), true, 0,new Point(0,0), new Point(0,0),new Size(0, 0), 0, new Stroke(1, "#000000"),
						"#000000", "", "");
				shape.setShapeId(temp.getShapeId());
				shape.setZ(shapeFactory.getTotalShapeNb());
				shapesDict.put(shape.getShapeId(), shape);
			} else
				LOGGER.log(Level.INFO, ERROR_MESSAGE);
		}
	}

	public void clearShapesTempo() {
		shapesDict.clear();
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

	public void notifyRemoveAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.SHAPE_REMOVED);
		}
	}
	
	public void findParents(Shape child, List<Shape> shapesList, List<ShapesGroup> parents) {
		for (Shape shape : shapesList) {
			if (shape.getShapeType() == ShapeType.GROUP) {
				if (((ShapesGroup) shape).getShapes().contains(child)) {
					parents.add((ShapesGroup) shape);
				}
				
				findParents(child, ((ShapesGroup) shape).getShapes(), parents);
			}
		}
	}
	
	public void removeShape(Shape shape) {
		List<ShapesGroup> parents = new ArrayList<ShapesGroup>();
		findParents(shape, getShapesList(), parents);
		
		for (ShapesGroup parent : parents) {
			parent.remove(shape);
		}
		
		if (shape.getShapeType() == ShapeType.GROUP && parents.size() == 0) {
			for (Shape s : ((ShapesGroup) shape).getShapes()) {
				addShape(s);
			}
		}

		removeShape(shape, true);
	}

	public void removeShape(Shape shape, boolean shouldNotify) {
    if(shapesDict.containsKey(shape.getShapeId())) {
			shapesDict.remove(shape.getShapeId());
		}
		if (shouldNotify)
			notifyRemoveAllObservers();
	}
}