package cPaintUS.models.shapes;

import javafx.scene.paint.Color;

public class Pokeball extends Shape {
	private String fillColor;
	private Color backColor;

	public Pokeball(String shapeId, int canvasId, double x, double y, int z, double width, double height, int lineWidth,
			String strokeColor, String fillColor,ShapeType shapeType) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor,shapeType);

		this.fillColor = fillColor;
		this.backColor = Color.WHITE;
	}

	public Pokeball() {

	}

	public String getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	// Can't modify this property!
	public Color getBackColor() {
		return this.backColor;
	}
}