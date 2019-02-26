package cpaintus.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RulerSingleton {
	
	private DoubleProperty verticalScrollPosition;
	private DoubleProperty horizontalScrollPosition;
	
	private DoubleProperty verticalCanvasSize;
	private DoubleProperty horizontalCanvasSize;

	private static class SingletonHelper {
		private static final RulerSingleton INSTANCE = new RulerSingleton();
	}

	private RulerSingleton() {
		verticalScrollPosition = new SimpleDoubleProperty();
		horizontalScrollPosition = new SimpleDoubleProperty();
		verticalCanvasSize = new SimpleDoubleProperty();
		horizontalCanvasSize = new SimpleDoubleProperty();
	}

	public static RulerSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public DoubleProperty getHorizontalScrollPosition() {
		return horizontalScrollPosition;
	}

	public DoubleProperty getVerticalScrollPosition() {
		return verticalScrollPosition;
	}

	public DoubleProperty getVerticalCanvasSize() {
		return verticalCanvasSize;
	}

	public DoubleProperty getHorizontalCanvasSize() {
		return horizontalCanvasSize;
	}

}
