package cPaintUS.models.shapes;

public class Rectangle extends Shape {
	private String fillColor;

	public Rectangle(String shapeId, int canvasId, double x, double y, int z, double width, double height,
			int lineWidth, String strokeColor, String fillColor) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor);

		this.fillColor = fillColor;
	}

	public Rectangle() {
	}

	public String getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}
