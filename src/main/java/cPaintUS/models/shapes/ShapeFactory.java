package cPaintUS.models.shapes;

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

	public Shape getShape(
			ShapeType shapeType,
			boolean persistent,
			double x,
			double y,
			double x2,
			double y2,
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			String base64,
			String text)
	{
		String shapeId;
		Shape shape;

		if (persistent)
			totalShapeNb++;

		switch (shapeType) {
		case Rectangle:
			shapeId = "Rectangle " + rectangleNb;
			if (persistent)
				rectangleNb++;
			shape = new Rectangle(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break; 
		case Ellipse:
			shapeId = "Ellipse " + ellipseNb;
			if (persistent)
				ellipseNb++;
			shape = new Ellipse(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break;
		case Line:
			shapeId = "Line " + lineNb;
			if (persistent)
				lineNb++;
			shape = new Line(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, x2, y2);
			break;
		case Pokeball:
			shapeId = "Pokeball " + pokeballNb;
			if (persistent)
				pokeballNb++;
			shape = new Pokeball(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break;
		case Heart:
			shapeId = "Heart " + heartNb;
			if (persistent)
				heartNb++;
			shape = new Heart(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break;
		case Picture:
			shapeId = "Picture " + pictureNb;
			if (persistent)
				pictureNb++;
			shape = new Picture(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height, base64);
			break;
		case Text:
			shapeId = "Text " + textNb;
			if (persistent)
				textNb++;
			shape = new Text(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height, text);
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
