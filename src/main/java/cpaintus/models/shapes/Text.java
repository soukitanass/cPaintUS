package cpaintus.models.shapes;

public class Text extends Shape2D {

	private String theText;
	
	public Text(
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
			double height,
			String text)
	{
		super(shapeType, shapeId, canvasHash, x, y, z, rotation, lineWidth, strokeColor, fillColor, width, height);
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
		Text text = new Text(
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
				this.getHeight(),
				this.getText());
		return text;
	}
}
