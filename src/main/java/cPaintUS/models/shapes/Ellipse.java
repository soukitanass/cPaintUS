package cPaintUS.models.shapes;

public class Ellipse extends Shape {
	private String fillColor;

	public Ellipse(String shapeId, int canvasId, double x, double y, int z, double width, double height, int lineWidth,
			String strokeColor, String fillColor) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor);

		this.fillColor = fillColor;
	}

	public Ellipse() {
	}

	public String getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}