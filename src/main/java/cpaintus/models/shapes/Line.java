package cpaintus.models.shapes;

import cpaintus.models.Point;

public class Line extends Shape1D {

	public Line(ShapeType shapeType, String shapeId, int canvasHash, Point position, int z, double rotation,
			Stroke stroke, Point position2) {
		super(shapeType, shapeId, canvasHash, position, z, rotation, stroke, position2);
	}

	public Line() {

	}

	@Override
	public Line makeCopy() {
		return new Line(this.getShapeType(), this.getShapeId(), this.getCanvasHash(),
				new Point(this.getX(), this.getY()), this.getZ(), this.getRotation(),
				new Stroke(this.getLineWidth(), this.getStrokeColor()), new Point(this.getX2(), getY2()));
	}
}