package cPaintUS.models;

public class PaintModel {

	private PaintModel() {
	}

	private static class SingletonHelper {
		private static final PaintModel INSTANCE = new PaintModel();
	}

	public static PaintModel getInstance() {
		return SingletonHelper.INSTANCE;
	}
}
