package cPaintUS.models.shapes;

public class ShapeFactory {
	private static ShapeFactory factory = null;
	private int rectangleNb;
	private int ellipseNb;
	private int lineNb;
	private int pokeballNb;
	private int heartNb;
	private int totalShapeNb;

	private ShapeFactory() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		totalShapeNb = 0;
		heartNb = 0;
	}

	public static ShapeFactory getInstance() {
		if (factory == null) {
			factory = new ShapeFactory();
		}

		return factory;
	}

	public Shape getShape(ShapeType shape, int canvasId, double x, double y, double width, double height, int lineWidth,
			String strokeColor, String fillColor) {
		String shapeId;

		totalShapeNb++;

		switch (shape) {
		case Rectangle:
			shapeId = "Rectangle:" + rectangleNb;
			rectangleNb++;
			return new Rectangle(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor,
					fillColor,ShapeType.Rectangle);
		case Ellipse:
			shapeId = "Ellipse:" + ellipseNb;
			ellipseNb++;
			return new Ellipse(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor,ShapeType.Ellipse);
		case Line:
			shapeId = "Line:" + lineNb;
			lineNb++;
			return new Line(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor,ShapeType.Line);
		case Pokeball:
			shapeId = "Pokeball:" + pokeballNb;
			pokeballNb++;
			return new Pokeball(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor,
					fillColor,ShapeType.Pokeball);
		case Heart:
			shapeId = "Heart:" + heartNb;
			heartNb++;
			return new Heart(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor, ShapeType.Heart);
		default:
			totalShapeNb--;
			return null;
		}
	}

	// Package private
	void clear() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		heartNb = 0;
		totalShapeNb = 0;
	}
}
