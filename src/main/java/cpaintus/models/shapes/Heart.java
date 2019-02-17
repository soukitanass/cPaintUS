package cpaintus.models.shapes;

import cpaintus.models.Point;

public class Heart extends Shape2D {
	
	public Heart(
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
		super(shapeType, shapeId, canvasHash,  position, z, rotation, stroke, fillColor, size);
	}

	public Heart() {
		
	}
}