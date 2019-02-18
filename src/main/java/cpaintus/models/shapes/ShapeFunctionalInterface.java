package cpaintus.models.shapes;

import cpaintus.models.Point;

@FunctionalInterface
public interface ShapeFunctionalInterface {
	Shape create(
			ShapeType shapeType,
			boolean persistent,
			int canvasHash,
			Point position,
			Point position2,
			Size size,
			double rotation,
			Stroke stroke,
			String fillColor,
			String base64,
			String text
			);
}