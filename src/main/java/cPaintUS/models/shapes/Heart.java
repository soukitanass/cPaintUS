package cpaintus.models.shapes;


public class Heart extends Shape2D {
	
	public Heart(
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
	}

	public Heart() {
		
	}
}