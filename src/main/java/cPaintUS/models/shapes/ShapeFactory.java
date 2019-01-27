package cPaintUS.models.shapes;

public class ShapeFactory {
	private static ShapeFactory factory = null;
	private int rectangleNb;
	private int ellipseNb;
	private int lineNb;
	private int pokeballNb;
	private int heartNb;
	private int pictureNb;
	private int totalShapeNb;

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

	public Shape getShape(ShapeType shapeType, boolean persistent, int canvasId, double x, double y, double width, double height, int lineWidth,
			String strokeColor, String fillColor) {
		String shapeId;
		Shape shape;

		if(persistent)
			totalShapeNb++;
		
		switch (shapeType) {
		case Rectangle:
			shapeId = "Rectangle_" + rectangleNb;
			if(persistent)
				rectangleNb++;
			shape = new Rectangle(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor,
					fillColor,ShapeType.Rectangle);
			break;
		case Ellipse:
			shapeId = "Ellipse_" + ellipseNb;
			if(persistent)
				ellipseNb++;
			shape = new Ellipse(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor,ShapeType.Ellipse);
			break;
		case Line:
			shapeId = "Line_" + lineNb;
			if(persistent)
				lineNb++;
			shape = new Line(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor,ShapeType.Line);
			break;
		case Pokeball:
			shapeId = "Pokeball_" + pokeballNb;
			if(persistent)
				pokeballNb++;
			shape = new Pokeball(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor,
					fillColor,ShapeType.Pokeball);
			break;
		case Heart:
			shapeId = "Heart_" + heartNb;
			if(persistent)
				heartNb++;
			shape = new Heart(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor, ShapeType.Heart);
			break;
		case Picture:
			shapeId = "Picture_" + pictureNb;
			if(persistent)
				pictureNb++;
			shape = new Picture(shapeId, canvasId, x, y, totalShapeNb, width, height, lineWidth, strokeColor, fillColor, ShapeType.Picture);
			break;
		default:
			if(persistent)
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
