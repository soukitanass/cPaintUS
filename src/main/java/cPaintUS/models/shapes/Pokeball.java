package cPaintUS.models.shapes;

import javafx.scene.paint.Color;

public class Pokeball extends Shape {
	private String fillColor;
	private Color backColor;

	public Pokeball(
			String shapeId,
			double x,
			double y,
			int z,
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			ShapeType shapeType) 
	{
		super(shapeId, x, y, z, width, height, rotation, lineWidth, strokeColor,shapeType);

		this.fillColor = fillColor;
		this.backColor = Color.WHITE;
	}

	public Pokeball() {

	}

	@Override
	public String getFillColor() {
		return this.fillColor;
	}

	@Override
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	// Can't modify this property!
	public Color getBackColor() {
		return this.backColor;
	}
}
