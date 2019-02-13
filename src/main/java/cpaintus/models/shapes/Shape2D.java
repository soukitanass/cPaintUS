package cpaintus.models.shapes;

import cpaintus.models.Point;

public abstract class Shape2D extends Shape {
	protected double width;
	protected double height;
	private String fillColor;

	public Shape2D(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
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
		super(shapeType, shapeId, canvasHash, x, y, z, rotation, lineWidth, strokeColor);
		this.shapeDim = ShapeDimension.SHAPE2D;
		this.fillColor = fillColor;
		this.width = width;
		this.height = height;
	}
	
	public Shape2D() {
		
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public Point getUpLeftCorner() {
		return new Point(x, y);
	}
	
	public String getFillColor() {
		return fillColor;
	}
	
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}
