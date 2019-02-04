package cPaintUS.models.shapes;

public abstract class Shape2D extends Shape {
	private double width;
	private double height;
	private String fillColor;

	public Shape2D(
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
		super(shapeType, shapeId, x, y, z, rotation, lineWidth, strokeColor);
		this.shapeDim = ShapeDimension.Shape2D;
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
	
	public String getFillColor() {
		return fillColor;
	}
	
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}
