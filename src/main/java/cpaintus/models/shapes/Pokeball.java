package cpaintus.models.shapes;

import cpaintus.models.Point;
import javafx.scene.paint.Color;

public class Pokeball extends Shape2D {
	private Color backColor;

	public Pokeball(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			Point position,
			int z,
			double rotation,
			Stroke stroke,
			String fillColor,
			Size size)
	{
		super(shapeType, shapeId, canvasHash, position, z, rotation, stroke, fillColor, size);
		this.backColor = Color.WHITE;
	}

	public Pokeball() {

	}

	// Can't modify this property!
	public Color getBackColor() {
		return this.backColor;
	}
}
