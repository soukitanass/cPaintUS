package cpaintus.models.shapes;

import cpaintus.models.Point;

public class Ellipse extends Shape2D {

	public Ellipse(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			Point position,
			int z,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			double width,
			double height)
	{
		super(shapeType, shapeId, canvasHash, position, z, rotation, lineWidth, strokeColor, fillColor, width, height);
	}

	public Ellipse() {
		
	}
}