package cPaintUS.models.shapes;

public class Text extends Shape{

	private String text;
	private String fillColor;
	
	public Text(String shapeId, double x, double y, int z, double width, double height,
			int lineWidth, String strokeColor, String fillColor, String text, ShapeType shapeType) {
		super(shapeId, canvasId, x, y, z, width, height, rotation, lineWidth, strokeColor,shapeType);
		this.setText(text);
		this.setFillColor(fillColor);

	}
	public Text() {
		
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
}
