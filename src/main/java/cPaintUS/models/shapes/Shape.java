package cPaintUS.models.shapes;

public abstract class Shape {
	private String shapeId;
	private double x;
	private double y;
	private int z;
	private double width;
	private double height;
	private double rotation;
	private int lineWidth;
	private String strokeColor;
	private ShapeType shapeType;

	public Shape(
			String shapeId,
			double x,
			double y,
			int z,
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			ShapeType shapeType)
	{
		this.shapeId = shapeId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.lineWidth = lineWidth;
		this.strokeColor = strokeColor;
		this.shapeType = shapeType;
	}

	public ShapeType getShapeType() {
		return shapeType;
	}

	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
	}

	public Shape() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return this.shapeId;
	}

	public void setShapeId(String id) {
		shapeId = id;
	}
	
	public String getShapeId() {
		return this.shapeId;
	}

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
	
	public double getRotation() {
		return this.rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public int getLineWidth() {
		return this.lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public String getStrokeColor() {
		return this.strokeColor;
	}
	
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	public String getFillColor() {
		return null;
	}
	
	public void setFillColor(String strokeColor) {
		// do nothing
	}

}
