package cpaintus.models.shapes;

import cpaintus.models.Point;

public class Rectangle extends Shape2D {

	public Rectangle(ShapeType shapeType, String shapeId, int canvasHash, Point position, int z, double rotation,
			Stroke stroke, String fillColor, Size size) {
		super(shapeType, shapeId, canvasHash, position, z, rotation, stroke, fillColor, size);
	}

	public Rectangle() {

	}

	@Override
	public Rectangle makeCopy() {
		return new Rectangle(this.getShapeType(), this.getShapeId(), this.getCanvasHash(),
				new Point(this.getX(), this.getY()), this.getZ(), this.getRotation(),
				new Stroke(this.getLineWidth(), this.getStrokeColor()), this.getFillColor(),
				new Size(this.getWidth(), this.getHeight()));
	}
}
