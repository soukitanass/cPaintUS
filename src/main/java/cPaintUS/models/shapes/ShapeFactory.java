package cPaintUS.models.shapes;

import java.util.HashMap;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public class ShapeFactory {
	private static ShapeFactory factory = null;
	private int rectangleNb = 0;
	private int ellipseNb = 0;
	private int lineNb = 0;
	private int pokeballNb = 0;
	
	private ShapeFactory() {
	}
	
	public static ShapeFactory getInstance() {
		if (factory == null) {
			factory = new ShapeFactory();
		}
		
		return factory;
	}
	
	public Shape getShape(ShapeType shape, int canvasId, BoundingBox box, int lineWidth, Color strokeColor, Color fillColor) {
		String shapeId;

		switch (shape) {
			case Rectangle:
				shapeId = "Rectangle:" + rectangleNb;
				rectangleNb++;
				return new Rectangle(shapeId, canvasId, box, lineWidth, strokeColor, fillColor);
			case Ellipse:
				shapeId = "Ellipse:" + ellipseNb;
				ellipseNb++;
				return new Ellipse(shapeId, canvasId, box, lineWidth, strokeColor, fillColor);
			case Line:
				shapeId = "Line:" + lineNb;
				lineNb++;
				return new Line(shapeId, canvasId, box, lineWidth, strokeColor);
			case Pokeball:
				shapeId = "Pokeball:" + pokeballNb;
				pokeballNb++;
				return new Pokeball(shapeId, canvasId, box, lineWidth, strokeColor, fillColor);
			default:
				return null;
		}
	}
	
	// Package private
	void clear() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
	}
}
