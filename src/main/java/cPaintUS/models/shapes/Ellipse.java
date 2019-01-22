package cPaintUS.models.shapes;

import javafx.scene.paint.Color;

public class Ellipse extends Shape {
	private Color fillColor;

	public Ellipse(String shapeId, int canvasId, double x, double y, int z, double width, double height, int lineWidth, Color strokeColor, Color fillColor) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor);
		
		this.fillColor = fillColor;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
}