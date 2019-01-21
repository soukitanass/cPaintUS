package cPaintUS.models;

public class LineWidth {
	private static LineWidth lineWidth = null;
	private int[] widths;
	
	private LineWidth() {
		widths = new int[4];
		widths[0] = 1;
		widths[1] = 3;
		widths[2] = 5;
		widths[3] = 8;
	}
	
	private static class LineWidthInstance {
		private static final LineWidth instance = new LineWidth();
	}
	
	public static LineWidth getInstance() {
		return LineWidthInstance.instance;
	}
	
	public int[] getWidths() {
		return widths;
	}
}
