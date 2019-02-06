package cpaintusc.models;

import cpaintusc.models.shapes.ShapeType;
import javafx.scene.paint.Color;

public class DrawSettings {
	private ShapeType shape;
	private int lineWidth;
	private Color fillColor;
	private Color strokeColor;
	private String text;
	
	private DrawSettings() {
		// set defaults
		shape = ShapeType.LINE;
		fillColor = Color.BLACK;
		strokeColor = Color.BLACK;
		lineWidth = LineWidth.getInstance().getDefault();
	}

	private static class DrawSettingsInstance {
		private static final DrawSettings instance = new DrawSettings();
	}
	
	public static DrawSettings getInstance() {
		return DrawSettingsInstance.instance;
	}
	
	public void setShape(ShapeType selectedShape) {
		shape = selectedShape;
	}
	
	public ShapeType getShape() {
		return shape;
	}
	
	public void setLineWidth(int selectedLineWidth) {
		lineWidth = selectedLineWidth;
	}
	
	public int getLineWidth() {
		return lineWidth;
	}
	
	public void setFillColor(Color selectedFillColor) {
		fillColor = selectedFillColor;
	}
	
	public Color getFillColor() {
		return fillColor;
	}
	
	public void setStrokeColor(Color selectedStrokeColor) {
		strokeColor = selectedStrokeColor;
	}
	
	public Color getStrokeColor() {
		return strokeColor;
	}
	
	public void setText(String textToAdd) {
		text = textToAdd;
	}
	
	public String getText() {
		return text;
	}
}