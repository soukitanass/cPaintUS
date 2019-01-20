package cPaintUS.models;

public class BoundingBox {

	private static BoundingBox instance;

	private BoundingBox() {
	}

	public static BoundingBox getInstance() {
		if (instance == null)
			instance = new BoundingBox();
		return instance;
	}
}
