package cPaintUS.models.shapes;

import javafx.scene.paint.Color;

public class Line extends Shape {

	public Line(String shapeId, int canvasId, double x, double y, int z, double width, double height, int lineWidth, Color strokeColor) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor);
	}
}