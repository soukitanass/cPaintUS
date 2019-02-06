package cpaintus.models;

import java.util.ArrayList;
import java.util.List;

public class LineWidth {
	private static LineWidth lineWidth = null;
	private int[] widths;
	private List<String> strings;
	
	private LineWidth() {
		widths = new int[4];
		widths[0] = 1;
		widths[1] = 3;
		widths[2] = 5;
		widths[3] = 8;
		
		strings = new ArrayList<String>();
		for (int w : widths) {
			strings.add(w + "px");
		}
	}
	
	public static LineWidth getInstance() {
		if (lineWidth == null) {
			lineWidth = new LineWidth();
		}

		return lineWidth;
	}
	
	public List<String> getStrings() {
		return strings;
	}
	
	public String getDefaultString() {
		return strings.get(0);
	}
	
	public int getDefault() {
		return widths[0];
	}
}
