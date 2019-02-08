package cpaintus.models;

import java.util.prefs.Preferences;

import cpaintus.models.shapes.ShapeType;
import javafx.scene.paint.Color;

public class DrawSettings {
	private ShapeType shape;
	private int lineWidth;
	private Color fillColor;
	private Color strokeColor;
	private String text;
	private Preferences prefs;  
	
	private DrawSettings() {
		// set defaults
		prefs = Preferences.userNodeForPackage(this.getClass());
		shape = ShapeType.valueOf(prefs.get("shape","LINE"));
		fillColor = Color.valueOf(prefs.get("fillcolor","BLACK"));
		strokeColor = Color.valueOf(prefs.get("strokecolor","BLACK"));
		lineWidth = prefs.getInt("linewidth",LineWidth.getInstance().getDefault());

	}

	private static class DrawSettingsInstance {
		private static final DrawSettings instance = new DrawSettings();
	}
	
	public static DrawSettings getInstance() {
		return DrawSettingsInstance.instance;
	}
	
	public void setShape(ShapeType selectedShape) {
		shape = selectedShape;
		prefs.put("shape",shape.name());
	}
	
	public ShapeType getShape() {
		return shape;
	}
	
	public void setLineWidth(int selectedLineWidth) {
		lineWidth = selectedLineWidth;
		prefs.putInt("linewidth", lineWidth);
	}
	
	public int getLineWidth() {
		return lineWidth;
	}
	
	public void setFillColor(Color selectedFillColor) {
		fillColor = selectedFillColor;
		prefs.put("fillcolor",fillColor.toString());
	}
	
	public Color getFillColor() {
		return fillColor;
	}
	
	public void setStrokeColor(Color selectedStrokeColor) {
		strokeColor = selectedStrokeColor;
		prefs.put("strokeColor",strokeColor.toString());
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