package cpaintus.models.shapes;

import javafx.scene.paint.Color;

public class Pokeball extends Shape2D {
	private Color backColor;

	public Pokeball(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			double x,
			double y,
			int z,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			double width,
			double height)
	{
		super(shapeType, shapeId, canvasHash, x, y, z, rotation, lineWidth, strokeColor, fillColor, width, height);
		this.backColor = Color.WHITE;
	}

	public Pokeball() {

	}

	// Can't modify this property!
	public Color getBackColor() {
		return this.backColor;
	}
	
	@Override
	public Pokeball makeCopy() {
		Pokeball pokeball = new Pokeball(
				this.getShapeType(),
				this.getShapeId(),
				this.getCanvasHash(),
				this.getX(),
				this.getY(),
				this.getZ(),
				this.getRotation(),
				this.getLineWidth(),
				this.getStrokeColor(),
				this.getFillColor(),
				this.getWidth(),
				this.getHeight());
		return pokeball;
	}
}
