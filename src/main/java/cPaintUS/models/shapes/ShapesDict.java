package cPaintUS.models.shapes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cPaintUS.controllers.CenterPaneController;

public class ShapesDict {
	private static ShapesDict instance = null;
	private HashMap<String, Shape> shapesDict;
	private ShapeFactory shapeFactory;
	private final CenterPaneController centerPaneController;

	private ShapesDict(CenterPaneController centerPaneController) {
		this.shapesDict = new HashMap<String, Shape>();
		this.shapeFactory = ShapeFactory.getInstance();
		this.centerPaneController = centerPaneController;
	}

	public static ShapesDict getInstance(CenterPaneController centerPaneController) {
		if (instance == null) {
			instance = new ShapesDict(centerPaneController);
		}

		return instance;
	}
	
	public static ShapesDict getInstance() {
		return instance;
	}

	public Collection<Shape> getShapes() {
		return shapesDict.values();
	}

	public List<Shape> getShapesList() {

		return new ArrayList<Shape>(this.getShapes());
	}

	public void addShape(Shape shape) {
		shapesDict.put(shape.getShapeId(), shape);
	}
	
	public void addListShapes(List<Shape> shapesList) {
		for(Shape shape : shapesList) {
			this.addShape(shape);
		}
	}

	public void clearShapes() {
		shapesDict.clear();
		shapeFactory.clear();
	}

	public CenterPaneController getCenterPaneController() {
		return centerPaneController;
	}
}