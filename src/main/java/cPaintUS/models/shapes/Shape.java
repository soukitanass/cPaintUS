package cPaintUS.models.shapes;

public abstract class Shape {
	private String shapeId;
	private int canvasId;
	private double x;
	private double y;
	private int z;
	private double width;
	private double height;
	private int lineWidth;
	private String strokeColor;

	public Shape(String shapeId, int canvasId, double x, double y, int z, double width, double height, int lineWidth,
			String strokeColor) {
		this.shapeId = shapeId;
		this.canvasId = canvasId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.lineWidth = lineWidth;
		this.strokeColor = strokeColor;
	}

	public Shape() {
		// TODO Auto-generated constructor stub
	}

	// Can't modify the shapeId!
	public String getShapeId() {
		return this.shapeId;
	}

	// These properties might be modified. So, provide setters
	// CanvasId
	public int getCanvasId() {
		return this.canvasId;
	}

	public void setCanvasId(int canvasId) {
		this.canvasId = canvasId;
	}

	// Position
	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getZ() {
		return this.z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	// Dimensions
	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	// LineWidth
	public int getLineWidth() {
		return this.lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	// StrokeColor
	public String getStrokeColor() {
		return this.strokeColor;
	}

	public void getStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
}
