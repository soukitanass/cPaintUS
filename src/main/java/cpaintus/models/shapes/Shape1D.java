package cpaintus.models.shapes;

import cpaintus.models.Point;

public abstract class Shape1D extends Shape {
	private double x2;
	private double y2;
	
	public Shape1D(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			Point position,
			int z,
			double rotation,
			Stroke stroke,
			Point position2)
	{
		super(shapeType, shapeId, canvasHash, position, z, rotation, stroke);
		this.x2 = position2.getX();
		this.y2 = position2.getY();
		this.shapeDim = ShapeDimension.SHAPE1D;
	}
	
	public Shape1D() {
		
	}
	
	@Override
	public double getWidth() {
		return Math.max(this.getX(), this.getX2()) - Math.min(this.getX(), this.getX2());
	}

	@Override
	public void setWidth(double width) {
		if (this.getX() < this.getX2()) {
			this.setX2(this.getX() + width);
		} else {
			this.setX(this.getX2() + width);
		}
	}

	@Override
	public double getHeight() {
		return Math.max(this.getY(), this.getY2()) - Math.min(this.getY(), this.getY2());
	}

	@Override
	public void setHeight(double height) {
		if (this.getY() < this.getY2()) {
			this.setY2(this.getY() + height);
		} else {
			this.setY(this.getY2() + height);
		}
	}
	
	public double getX2() {
		return x2;
	}
	
	public void setX2(double x2) {
		this.x2 = x2;
	}
	
	public double getY2() {
		return y2;
	}
	
	public void setY2(double y2) {
		this.y2 = y2;
	}
	
	@Override
	public Point getUpLeftCorner() {
		return new Point(Math.min(x, x2),
				Math.min(y, y2));
	}

	@Override
	public void setUpLeftCornerX(double x) {
		double diffX = getUpLeftCorner().getX() - x;
		this.x -= diffX;
		this.x2 -= diffX;
	}

	@Override
	public void setUpLeftCornerY(double y) {
		double diffY = getUpLeftCorner().getY() - y;
		this.y -= diffY;
		this.y2 -= diffY;
	}
}
