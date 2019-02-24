package cpaintus.models.shapes;

import java.util.ArrayList;
import java.util.List;

import cpaintus.models.Flip;
import cpaintus.models.FlipTransform;
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

	protected List<FlipTransform> flipTransforms;

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
		this.flipTransforms = new ArrayList<FlipTransform>();
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

	public void addTransform(Flip type) {
		flipTransforms.add(new FlipTransform(type, new Point(getWidth() / 2, getHeight() / 2)));
	}

	public void addTransform(Flip type, Point pivot) {
		flipTransforms.add(new FlipTransform(type, pivot));
	}

	public List<FlipTransform> getTransforms() {
		return flipTransforms;
	}
}
