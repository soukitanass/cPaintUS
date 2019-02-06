package cpaintus.models.shapes;

public abstract class Shape1D extends Shape {
	private double x2;
	private double y2;
	
	public Shape1D(
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
		super(shapeType, shapeId, x, y, z, rotation, lineWidth, strokeColor);
		this.x2 = x2;
		this.y2 = y2;
		this.shapeDim = ShapeDimension.Shape1D;
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
}
