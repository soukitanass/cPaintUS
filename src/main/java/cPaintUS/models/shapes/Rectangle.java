package cPaintUS.models.shapes;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
	private Color fillColor;

	public Rectangle(String shapeId, int canvasId, BoundingBox box, int lineWidth, Color strokeColor, Color fillColor) {
		super(shapeId, canvasId, box, lineWidth, strokeColor);
		
		this.fillColor = fillColor;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
}
