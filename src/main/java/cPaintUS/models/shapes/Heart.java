package cPaintUS.models.shapes;


public class Heart extends Shape {
	private String fillColor;

	public Heart(
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

	public Heart() {
	}

	public String getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}