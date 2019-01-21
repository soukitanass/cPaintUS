package cPaintUS.models.shapes;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public class Pokeball extends Shape {
	private Color fillColor;
	private Color backColor;

	public Pokeball(String shapeId, int canvasId,  BoundingBox box, int lineWidth, Color strokeColor, Color fillColor) {
		super(shapeId, canvasId, box, lineWidth, strokeColor);
		
		this.fillColor = fillColor;
		this.backColor = Color.WHITE;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	// Can't modify this property!
	public Color getBackColor() {
		return this.backColor;
	}
}
