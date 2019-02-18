package cpaintus.models.shapes;

public class Size {
	
	private double height;
	private double width;
	
	public Size(double width, double height) {
		this.height = height;
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

}
