package cpaintus.models.shapes;

public class Rectangle extends Shape2D {
	
	public Rectangle(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			double x,
			double y,
			int z,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			double width,
			double height)
	{
		super(shapeType, shapeId, canvasHash, x, y, z, rotation, lineWidth, strokeColor, fillColor, width, height);
	}

	public Rectangle() {
		
	}
	
	@Override
	public Rectangle makeCopy() {
		Rectangle rectangle = new Rectangle(
				this.getShapeType(),
				this.getShapeId(),
				this.getCanvasHash(),
				this.getX(),
				this.getY(),
				this.getZ(),
				this.getRotation(),
				this.getLineWidth(),
				this.getStrokeColor(),
				this.getFillColor(),
				this.getWidth(),
				this.getHeight());
		return rectangle;
	}
}
