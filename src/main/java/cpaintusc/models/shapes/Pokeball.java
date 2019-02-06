package cpaintusc.models.shapes;

import javafx.scene.paint.Color;

public class Pokeball extends Shape2D {
	private Color backColor;

	public Pokeball(
			ShapeType shapeType,
			String shapeId,
			double x,
			double y,
			int z,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			double width,
			double height)
	{
		super(shapeType, shapeId, x, y, z, rotation, lineWidth, strokeColor, fillColor, width, height);
		this.backColor = Color.WHITE;
	}

	public Pokeball() {

	}

	// Can't modify this property!
	public Color getBackColor() {
		return this.backColor;
	}
}
