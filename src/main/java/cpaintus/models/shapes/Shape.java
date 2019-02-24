package cpaintus.models.shapes;

import cpaintus.models.Point;

public abstract class Shape {
	private ShapeType shapeType;
	private String shapeId;
	protected double x;
	protected double y;

	private int canvasHash;

	private int z;
	private double rotation;
	private int lineWidth;
	private String strokeColor;
	protected ShapeDimension shapeDim;

	private boolean isFlippedHorizontally;
	private boolean isFlippedVertically;

	public Shape(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			Point position,
			int z,
			double rotation,
			Stroke stroke)
	{
		this.shapeType = shapeType;
		this.shapeId = shapeId;
		this.canvasHash = canvasHash;
		this.x = position.getX();
		this.y = position.getY();
		this.z = z;
		this.rotation = rotation;
		this.lineWidth = stroke.getLinewidth();
		this.strokeColor = stroke.getStrokeColor();
		this.isFlippedHorizontally = false;
		this.isFlippedVertically = false;
	}
	
	public Shape() {
		// do not remove
	}

	public ShapeType getShapeType() {
		return shapeType;
	}
	
	public abstract double getWidth();
	public abstract void setWidth(double width);
	public abstract double getHeight();
	public abstract void setHeight(double height);
	public abstract Point getUpLeftCorner();

	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
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
	
	public void setCanvasHash(int hash) {
		this.canvasHash = hash;
	}
	
	public int getCanvasHash() {
		return this.canvasHash;
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
	
	public double getRotation() {
		double currentRotation = this.rotation;
		if (isFlippedVertically ^ isFlippedHorizontally)
			currentRotation = -currentRotation;
		return currentRotation;
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
	
	public ShapeDimension getShapeDimension() {
		return shapeDim;
	}

	public abstract Shape makeCopy();

	public void setUpLeftCornerX(double x) {
		this.setX(x);
	}
	
	public void setUpLeftCornerY(double y) {
		this.setY(y);
	}

	public Point getCenter() {
		return new Point(getUpLeftCorner().getX() + getWidth() / 2,
				getUpLeftCorner().getY() + getHeight() / 2);
	}

	public void flipHorizontally() {
		this.isFlippedHorizontally = !this.isFlippedHorizontally;
	}

	public void flipVertically() {
		this.isFlippedVertically = !this.isFlippedVertically;
	}

	public boolean isFlippedHorizontally() {
		return this.isFlippedHorizontally;
	}

	public boolean isFlippedVertically() {
		return this.isFlippedVertically;
	}
}
