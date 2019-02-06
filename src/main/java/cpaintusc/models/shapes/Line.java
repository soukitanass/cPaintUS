package cpaintusc.models.shapes;

public class Line extends Shape1D {

	public Line(
			ShapeType shapeType,
			String shapeId,
			double x,
			double y,
			int z,
			double rotation,
			int lineWidth,
			String strokeColor,
			double x2,
			double y2) 
	{
		super(shapeType, shapeId, x, y, z, rotation, lineWidth, strokeColor, x2, y2);
	}

	public Line() {
		
	}
}