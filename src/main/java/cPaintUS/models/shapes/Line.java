package cPaintUS.models.shapes;

public class Line extends Shape {

	public Line(String shapeId, int canvasId, double x, double y, int z, double width, double height, int lineWidth,
			String strokeColor,ShapeType shapeType) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor,shapeType);
	}

	public Line() {
		super();
	}
}