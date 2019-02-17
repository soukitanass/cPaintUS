package cpaintus.models.shapes;

public class Stroke {
	String strokeColor;
	int linewidth;

	public Stroke(int linewidth, String strokeColor) {
		this.linewidth = linewidth;
		this.strokeColor = strokeColor;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public int getLinewidth() {
		return linewidth;
	}

	public void setLinewidth(int linewidth) {
		this.linewidth = linewidth;
	}

}
