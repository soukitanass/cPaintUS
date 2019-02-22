package cpaintus.models.shapes;

import java.util.HashMap;

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

	private HashMap<ShapeType, ShapeFunctionalInterface> creatorDictionary;

	private ShapeFactory() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		totalShapeNb = 0;
		heartNb = 0;
		pictureNb = 0;

		ShapeFunctionalInterface rectangleCreator = (ShapeType shapeType, boolean persistent, int canvasHash,
				Point position, Point position2, Size size, double rotation, Stroke stroke, String fillColor,
				String base64, String text) -> {
			String shapeId = "Rectangle " + rectangleNb;
			if (persistent)
				rectangleNb++;
			return new Rectangle(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, fillColor,
					size);
		};

		ShapeFunctionalInterface ellipseCreator = (ShapeType shapeType, boolean persistent, int canvasHash,
				Point position, Point position2, Size size, double rotation, Stroke stroke, String fillColor,
				String base64, String text) -> {
			String shapeId = "Ellipse " + ellipseNb;
			if (persistent)
				ellipseNb++;
			return new Ellipse(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, fillColor,
					size);
		};

		ShapeFunctionalInterface lineCreator = (ShapeType shapeType, boolean persistent, int canvasHash, Point position,
				Point position2, Size size, double rotation, Stroke stroke, String fillColor, String base64,
				String text) -> {
			String shapeId = "Line " + lineNb;
			if (persistent)
				lineNb++;
			return new Line(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, position2);
		};

		ShapeFunctionalInterface pokeballCreator = (ShapeType shapeType, boolean persistent, int canvasHash,
				Point position, Point position2, Size size, double rotation, Stroke stroke, String fillColor,
				String base64, String text) -> {
			String shapeId = "Pokeball " + pokeballNb;
			if (persistent)
				pokeballNb++;
			return new Pokeball(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, fillColor,
					size);
		};

		ShapeFunctionalInterface heartCreator = (ShapeType shapeType, boolean persistent, int canvasHash,
				Point position, Point position2, Size size, double rotation, Stroke stroke, String fillColor,
				String base64, String text) -> {
			String shapeId = "Heart " + heartNb;
			if (persistent)
				heartNb++;
			return new Heart(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, fillColor, size);
		};

		ShapeFunctionalInterface pictureCreator = (ShapeType shapeType, boolean persistent, int canvasHash,
				Point position, Point position2, Size size, double rotation, Stroke stroke, String fillColor,
				String base64, String text) -> {
			String shapeId = "Picture " + pictureNb;
			if (persistent)
				pictureNb++;
			return new Picture(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, fillColor,
					size, base64);
		};

		ShapeFunctionalInterface textCreator = (ShapeType shapeType, boolean persistent, int canvasHash, Point position,
				Point position2, Size size, double rotation, Stroke stroke, String fillColor, String base64,
				String text) -> {
			String shapeId = "Text " + textNb;
			if (persistent)
				textNb++;
			return new Text(shapeType, shapeId, canvasHash, position, totalShapeNb + 1, rotation, stroke, fillColor, size,
					text);
		};

		creatorDictionary = new HashMap<>();
		creatorDictionary.put(ShapeType.RECTANGLE, rectangleCreator);
		creatorDictionary.put(ShapeType.ELLIPSE, ellipseCreator);
		creatorDictionary.put(ShapeType.HEART, heartCreator);
		creatorDictionary.put(ShapeType.LINE, lineCreator);
		creatorDictionary.put(ShapeType.PICTURE, pictureCreator);
		creatorDictionary.put(ShapeType.POKEBALL, pokeballCreator);
		creatorDictionary.put(ShapeType.TEXT, textCreator);
	}

	public static ShapeFactory getInstance() {
		if (factory == null) {
			factory = new ShapeFactory();
		}

		return factory;
	}

	public Shape getShape(ShapeType shapeType, boolean persistent, int canvasHash, Point position, Point position2,
			Size size, double rotation, Stroke stroke, String fillColor, String base64, String text) {
		return creatorDictionary.get(shapeType).create(shapeType, persistent, canvasHash, position, position2, size,
				rotation, stroke, fillColor, base64, text);
	}

	void clear() {
		rectangleNb = 0;
		ellipseNb = 0;
		lineNb = 0;
		pokeballNb = 0;
		heartNb = 0;
		pictureNb = 0;
		totalShapeNb = 0;
	}

	public int getTotalShapeNb() {
		return totalShapeNb;
	}

	public int getRectangleNb() {
		return rectangleNb;
	}

	public int getEllipseNb() {
		return ellipseNb;
	}

	public int getLineNb() {
		return lineNb;
	}

	public int getPokeballNb() {
		return pokeballNb;
	}

	public int getHeartNb() {
		return heartNb;
	}

	public int getPictureNb() {
		return pictureNb;
	}

	public int getTextNb() {
		return textNb;
	}

	public void setRectangleNb(int rectangleNb) {
		this.rectangleNb = rectangleNb;
	}

	public void setEllipseNb(int ellipseNb) {
		this.ellipseNb = ellipseNb;
	}

	public void setLineNb(int lineNb) {
		this.lineNb = lineNb;
	}

	public void setPokeballNb(int pokeballNb) {
		this.pokeballNb = pokeballNb;
	}

	public void setHeartNb(int heartNb) {
		this.heartNb = heartNb;
	}

	public void setPictureNb(int pictureNb) {
		this.pictureNb = pictureNb;
	}

	public void setTextNb(int textNb) {
		this.textNb = textNb;
	}

	public void setTotalShapeNb(int totalShapeNb) {
		this.totalShapeNb = totalShapeNb;
	}

}
