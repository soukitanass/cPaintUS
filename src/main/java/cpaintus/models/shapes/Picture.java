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
			int lineWidth,
			String strokeColor,
			String fillColor,
			double width,
			double height,
			String base64)
	{
		super(shapeType, shapeId, canvasHash,position, z, rotation, lineWidth, strokeColor, fillColor, width, height);
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
}
