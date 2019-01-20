package cPaintUS.models;

public class ShapeFactory {
	public static Shape getShape(ShapeType shapeType) {
		switch (shapeType) {
		case Rectangle:
			return new Rectangle();

		default:
			return null;
		}
	}

}
