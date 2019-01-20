package cPaintUS.models.shapes;

public class ShapeFactory {
	public static Shape getShape(ShapeType shapeType) {
		Shape shape;
		switch (shapeType) {
		case Rectangle:
			shape = new Rectangle();

		default:
			shape = null;
		}
		return shape;
	}

}
