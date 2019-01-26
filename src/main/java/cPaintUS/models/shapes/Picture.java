package cPaintUS.models.shapes;

public class Picture extends Shape {
	private String base64;
	
	public Picture(String shapeId, int canvasId, double x, double y, int z, double width, double height,
			int lineWidth, String strokeColor, String base64,ShapeType shapeType) {
		super(shapeId, canvasId, x, y, z, width, height, lineWidth, strokeColor,shapeType);

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
