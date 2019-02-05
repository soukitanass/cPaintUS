package cPaintUS.models.shapes;

@FunctionalInterface
public interface ShapeFunctionalInterface {
	Shape create(
			ShapeType shapeType,
			boolean persistent,
			double x,
			double y,
			double x2,
			double y2,
			double width,
			double height,
			double rotation,
			int lineWidth,
			String strokeColor,
			String fillColor,
			String base64,
			String text);
}
