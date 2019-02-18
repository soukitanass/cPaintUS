package cpaintus.models.shapes;

import cpaintus.models.Point;

public class Picture extends Shape2D {
	private String base64;
	
	public Picture(
			ShapeType shapeType,
			String shapeId,
			int canvasHash,
			Point position,
			int z,
			double rotation,
			Stroke stroke,
			String fillColor,
			Size size,
			String base64)
	{
		super(shapeType, shapeId, canvasHash,position, z, rotation, stroke, fillColor, size);
		this.setBase64(base64);
	}
	
	public Picture() {
		
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	@Override
	public Picture makeCopy() {
		Picture picture = new Picture(
				this.getShapeType(),
				this.getShapeId(),
				this.getCanvasHash(),
				new Point(this.getX(),this.getY()),
				this.getZ(),
				this.getRotation(),
				new Stroke(this.getLineWidth(),this.getStrokeColor()),
				this.getFillColor(),
				new Size(this.getWidth(), this.getHeight()),
				this.getBase64());
		return picture;
	}
}
