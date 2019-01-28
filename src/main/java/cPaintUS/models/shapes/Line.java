package cPaintUS.models.shapes;

public class Line extends Shape {

	public Line(
			String shapeId,
			double x,
			double y,
			int z,
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			ShapeType shapeType) 
	{
		super(shapeId, x, y, z, width, height, rotation, lineWidth, strokeColor, shapeType);
	}

	public Line() {
		
	}
}