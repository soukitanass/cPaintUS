package cpaintus.models.shapes;

import cpaintus.models.Point;

public class ShapeFactory {
	private static ShapeFactory factory = null;
	private int rectangleNb;
	private int ellipseNb;
	private int lineNb;
	private int pokeballNb;
	private int heartNb;
	private int pictureNb;
	private int textNb;
	private int totalShapeNb;

	public int getTotalShapeNb() {
		return totalShapeNb;
	}

	private ShapeFactory() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		totalShapeNb = 0;
		heartNb = 0;
		pictureNb = 0;
	}

	public static ShapeFactory getInstance() {
		if (factory == null) {
			factory = new ShapeFactory();
		}

		return factory;
	}

	public Shape getShape(ShapeType shapeType, boolean persistent, int canvasHash, Point position, Point position2,
			Size size, double rotation, Stroke stroke, String fillColor, String base64,
			String text) {
		String shapeId;
		Shape shape;

		if (persistent)
			totalShapeNb++;

		switch (shapeType) {
		case RECTANGLE:
			shapeId = "Rectangle " + rectangleNb;
			if (persistent)
				rectangleNb++;
			shape = new Rectangle(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke, fillColor, size);
			break;
		case ELLIPSE:
			shapeId = "Ellipse " + ellipseNb;
			if (persistent)
				ellipseNb++;
			shape = new Ellipse(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke, fillColor, size);
			break;
		case LINE:
			shapeId = "Line " + lineNb;
			if (persistent)
				lineNb++;
			shape = new Line(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke,
					position2);
			break;
		case POKEBALL:
			shapeId = "Pokeball " + pokeballNb;
			if (persistent)
				pokeballNb++;
			shape = new Pokeball(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke, fillColor, size);
			break;
		case HEART:
			shapeId = "Heart " + heartNb;
			if (persistent)
				heartNb++;
			shape = new Heart(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke,
					fillColor, size);
			break;
		case PICTURE:
			shapeId = "Picture " + pictureNb;
			if (persistent)
				pictureNb++;
			shape = new Picture(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke, fillColor, size, base64);
			break;
		case TEXT:
			shapeId = "Text " + textNb;
			if (persistent)
				textNb++;
			shape = new Text(shapeType, shapeId, canvasHash, position, totalShapeNb, rotation, stroke,
					fillColor, size, text);
			break;
		default:
			if (persistent)
				totalShapeNb--;
			shape = null;
			break;
		}

		return shape;
	}

	// Package private
	void clear() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		heartNb = 0;
		pictureNb = 0;
		totalShapeNb = 0;
	}
}
