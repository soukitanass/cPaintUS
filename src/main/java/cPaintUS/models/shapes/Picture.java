package cpaintus.models.shapes;

public class Picture extends Shape2D {
	private String base64;
	
	public Picture(
			ShapeType shapeType,
			String shapeId,
			double x,
			double y,
			int z,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			double width,
			double height,
			String base64)
	{
		super(shapeType, shapeId, x, y, z, rotation, lineWidth, strokeColor, fillColor, width, height);
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
