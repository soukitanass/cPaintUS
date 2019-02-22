package cpaintus.models.shapes;

import cpaintus.models.Point;

public class Text extends Shape2D {

	private String theText;

	public Text(ShapeType shapeType, String shapeId, int canvasHash, Point position, int z, double rotation,
			Stroke stroke, String fillColor, Size size, String text) {
		super(shapeType, shapeId, canvasHash, position, z, rotation, stroke, fillColor, size);
		this.theText = text;
	}

	public Text() {

	}

	public String getText() {
		return theText;
	}

	public void setText(String text) {
		this.theText = text;
	}

	@Override
	public Text makeCopy() {
		return new Text(this.getShapeType(), this.getShapeId(), this.getCanvasHash(),
				new Point(this.getX(), this.getY()), this.getZ(), this.getRotation(),
				new Stroke(this.getLineWidth(), this.getStrokeColor()), this.getFillColor(),
				new Size(this.getWidth(), this.getHeight()), this.getText());
	}
}
