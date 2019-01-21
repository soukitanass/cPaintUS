package cPaintUS.models.shapes;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public class Line extends Shape {

	public Line(String shapeId, int canvasId, BoundingBox box, int lineWidth, Color strokeColor) {
		super(shapeId, canvasId, box, lineWidth, strokeColor);
	}
}