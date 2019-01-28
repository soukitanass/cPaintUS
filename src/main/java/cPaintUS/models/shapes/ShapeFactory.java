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
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			String text)
	{
		String shapeId;
		Shape shape;

		if (persistent)
			totalShapeNb++;

		switch (shapeType) {
		case Rectangle:
			shapeId = "Rectangle_" + rectangleNb;
			if (persistent)
				rectangleNb++;
			shape = new Rectangle(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor,
					fillColor,ShapeType.Rectangle);
			break;
		case Ellipse:
			shapeId = "Ellipse_" + ellipseNb;
			if (persistent)
				ellipseNb++;
			shape = new Ellipse(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor, fillColor,ShapeType.Ellipse);
			break;
		case Line:
			shapeId = "Line_" + lineNb;
			if (persistent)
				lineNb++;
			shape = new Line(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor,ShapeType.Line);
			break;
		case Pokeball:
			shapeId = "Pokeball_" + pokeballNb;
			if (persistent)
				pokeballNb++;
			shape = new Pokeball(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor,
					fillColor,ShapeType.Pokeball);
			break;
		case Heart:
			shapeId = "Heart_" + heartNb;
			if (persistent)
				heartNb++;
			shape = new Heart(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor, fillColor, ShapeType.Heart);
			break;
		case Picture:
			shapeId = "Picture_" + pictureNb;
			if (persistent)
				pictureNb++;
			shape = new Picture(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor, ShapeType.Picture);
			break;
		case Text:
			shapeId = "Text_" + textNb;
			if (persistent)
				textNb++;
			shape = new Text(shapeId, x, y, totalShapeNb, width, height, rotation, lineWidth, strokeColor, fillColor, text,
					ShapeType.Text);
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
