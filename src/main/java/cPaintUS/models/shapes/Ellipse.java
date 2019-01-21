package cPaintUS.models.shapes;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public class Ellipse extends Shape {
	private Color fillColor;

	public Ellipse(String shapeId, int canvasId,  BoundingBox box, int lineWidth, Color strokeColor, Color fillColor) {
		super(shapeId, canvasId, box, lineWidth, strokeColor);
		
		this.fillColor = fillColor;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
}