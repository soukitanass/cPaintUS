package cPaintUS.models.shapes;

public class Rectangle extends Shape {
	private String fillColor;

	public Rectangle(
			String shapeId,
			double x,
			double y,
			int z,
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			ShapeType shapeType) 
	{
		super(shapeId, x, y, z, width, height, rotation, lineWidth, strokeColor,shapeType);

		this.fillColor = fillColor;
	}

	public Rectangle() {
	}

	@Override
	public String getFillColor() {
		return this.fillColor;
	}

	@Override
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}
