package cPaintUS.models.shapes;

import cPaintUS.models.BoundingBox;
import javafx.scene.paint.Color;

public abstract class Shape {
	private String shapeId;
	private int canvasId;
	private BoundingBox box;
	private int lineWidth;
	private Color strokeColor;
	
	public Shape(String shapeId, int canvasId, BoundingBox box, int lineWidth, Color strokeColor) {
		this.shapeId = shapeId;
		this.canvasId = canvasId;
		this.box = box;
		this.lineWidth = lineWidth;
		this.strokeColor = strokeColor;
	}
	
	// Can't modify the shapeId!
	public String getShapeId() {
		return this.shapeId;
	}
	
	// These properties might be modified.
	// So, provide setters
	public int getCanvasId() {
		return this.canvasId;
	}
	
	public void setCanvasId(int canvasId) {
		this.canvasId = canvasId;
	}
	
	public BoundingBox getBoundingBox() { 
		return this.box;
	}
	
	public void setBoundingBox(BoundingBox box) { 
		this.box = box;
	}
	
	public int getLineWidth() {
		return this.lineWidth;
	}
	
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	public Color getStrokeColor() {
		return this.strokeColor;
	}
	
	public void getStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
 }
