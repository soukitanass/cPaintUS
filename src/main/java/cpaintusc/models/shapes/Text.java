package cpaintusc.models.shapes;

public class Text extends Shape2D {

	private String theText;
	
	public Text(
			ShapeType shapeType,
			String shapeId,
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
		super(shapeType, shapeId, x, y, z, rotation, lineWidth, strokeColor, fillColor, width, height);
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
}
