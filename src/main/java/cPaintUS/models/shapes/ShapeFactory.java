package cPaintUS.models.shapes;

import java.util.HashMap;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public class ShapeFactory {
	private static ShapeFactory factory = null;
	private int rectangleNb;
	private int ellipseNb;
	private int lineNb;
	private int pokeballNb;
	private int totalShapeNb;
	
	private ShapeFactory() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		totalShapeNb = 0;
	}
	
	public static ShapeFactory getInstance() {
		if (factory == null) {
			factory = new ShapeFactory();
		}
		
		return factory;
	}
	
	public Shape getShape(ShapeType shape, int canvasId, double x, double y, double width, double height, int lineWidth, Color strokeColor, Color fillColor) {
		String shapeId;
		
		totalShapeNb++;

		switch (shape) {
			case Rectangle:
				shapeId = "Rectangle:" + rectangleNb;
				rectangleNb++;
				return new Rectangle(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor);
			case Ellipse:
				shapeId = "Ellipse:" + ellipseNb;
				ellipseNb++;
				return new Ellipse(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor);
			case Line:
				shapeId = "Line:" + lineNb;
				lineNb++;
				return new Line(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor);
			case Pokeball:
				shapeId = "Pokeball:" + pokeballNb;
				pokeballNb++;
				return new Pokeball(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor);
			default:
				totalShapeNb--;
				return null;
		}
	}
	
	// Package private
	void clear() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		totalShapeNb = 0;
	}
}
