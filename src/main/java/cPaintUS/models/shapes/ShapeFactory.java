package cPaintUS.models.shapes;

import java.util.HashMap;

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
		
		ShapeFunctionalInterface rectangleCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId;
			shapeId = "Rectangle " + rectangleNb;
			if (persistent)
				rectangleNb++;
			return new Rectangle(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
		};
		
		ShapeFunctionalInterface ellipseCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId = "Ellipse " + ellipseNb;
			if (persistent)
				ellipseNb++;
			return new Ellipse(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
		};
		
		ShapeFunctionalInterface lineCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId = "Line " + lineNb;
			if (persistent)
				lineNb++;
			return new Line(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, x2, y2);
		};
		
		ShapeFunctionalInterface pokeballCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId = "Pokeball " + pokeballNb;
			if (persistent)
				pokeballNb++;
			return new Pokeball(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
		};
		
		ShapeFunctionalInterface heartCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId = "Heart " + heartNb;
			if (persistent)
				heartNb++;
			return new Heart(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
		};
		
		ShapeFunctionalInterface pictureCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId = "Picture " + pictureNb;
			if (persistent)
				pictureNb++;
			return new Picture(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height, base64);
		};
		
		ShapeFunctionalInterface textCreator = (shapeType, persistent, x, y, x2, y2, width, height, rotation, lineWidth, strokeColor, fillColor, base64, text) -> {
			String shapeId = "Text " + textNb;
			if (persistent)
				textNb++;
			return new Text(shapeType, shapeId, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height, text);
		};
		
		creatorDictionary = new HashMap<ShapeType, ShapeFunctionalInterface>();
		creatorDictionary.put(ShapeType.Rectangle, rectangleCreator);
		creatorDictionary.put(ShapeType.Ellipse, ellipseCreator);
		creatorDictionary.put(ShapeType.Heart, heartCreator);
		creatorDictionary.put(ShapeType.Line, lineCreator);
		creatorDictionary.put(ShapeType.Picture, pictureCreator);
		creatorDictionary.put(ShapeType.Pokeball, pokeballCreator);
		creatorDictionary.put(ShapeType.Text, textCreator);
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
		
		return creatorDictionary.get(shapeType).create(
				shapeType, 
				persistent, 
				x, 
				y, 
				x2,
				y2, 
				width, 
				height, 
				rotation, 
				lineWidth, 
				strokeColor, 
				fillColor, 
				base64, 
				text);
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
