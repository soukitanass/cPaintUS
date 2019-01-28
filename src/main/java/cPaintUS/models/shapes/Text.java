package cPaintUS.models.shapes;

public class Text extends Shape{

	private String text;
	
	public Text(String shapeId, int canvasId, double x, double y, int z, double width, double height,
			int lineWidth, String strokeColor, String text,ShapeType shapeType) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor,shapeType);
		this.setText(text);

	}
	public Text() {
		
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
