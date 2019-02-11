package cpaintus.models.shapes;

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
			int canvasHash,
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
		case RECTANGLE:
			shapeId = "Rectangle " + rectangleNb;
			if (persistent)
				rectangleNb++;
			shape = new Rectangle(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break; 
		case ELLIPSE:
			shapeId = "Ellipse " + ellipseNb;
			if (persistent)
				ellipseNb++;
			shape = new Ellipse(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break;
		case LINE:
			shapeId = "Line " + lineNb;
			if (persistent)
				lineNb++;
			shape = new Line(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, x2, y2);
			break;
		case POKEBALL:
			shapeId = "Pokeball " + pokeballNb;
			if (persistent)
				pokeballNb++;
			shape = new Pokeball(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break;
		case HEART:
			shapeId = "Heart " + heartNb;
			if (persistent)
				heartNb++;
			shape = new Heart(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height);
			break;
		case PICTURE:
			shapeId = "Picture " + pictureNb;
			if (persistent)
				pictureNb++;
			shape = new Picture(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height, base64);
			break;
		case TEXT:
			shapeId = "Text " + textNb;
			if (persistent)
				textNb++;
			shape = new Text(shapeType, shapeId, canvasHash, x, y, totalShapeNb, rotation, lineWidth, strokeColor, fillColor, width, height, text);
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
