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
	
	public String getShapeId() {
		return this.shapeId;
	}
	
	public int getCanvasId() {
		return this.canvasId;
	}
	
	public BoundingBox getBoundingBox() { 
		return this.box;
	}
	
	public int getLineWidth() {
		return this.lineWidth;
	}
	
	public Color getStrokeColor() {
		return this.strokeColor;
	}
 }
