package cPaintUS.models.shapes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ShapesDict {
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
	
	public void addShape(Shape shape) {
		shapesDict.put(shape.getShapeId(), shape);
	}
	
	public void clearShapes() {
		shapesDict.clear();
		shapeFactory.clear();
	}
}