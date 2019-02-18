package cpaintus.models.composite;

import java.util.ArrayList;
import java.util.List;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.Shape2D;
import cpaintus.models.shapes.ShapeDimension;
import cpaintus.models.shapes.ShapeType;

public class ShapesGroup extends Shape2D {

	private List<Shape> shapes = new ArrayList<>();
	private static int groupNb = 0;

	public ShapesGroup() {
		setShapeId("Group " + groupNb++);
		setShapeType(ShapeType.GROUP);
		this.shapeDim = ShapeDimension.SHAPE2D;
	}

	public void add(Shape shape) {
		this.shapes.add(shape);
	}

	public void remove(Shape shape) {
		shapes.remove(shape);
	}

	public void clear() {
		this.shapes.clear();
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	public void setHeightGroup(double height) {
		this.height = height;

	}

	public void setWidthGroup(double width) {
		this.width = width;

	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public void setWidth(double width) {
		for (Shape shape : shapes) {
			shape.setWidth(width);
		}
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public void setHeight(double height) {
		for (Shape shape : shapes) {
			shape.setHeight(height);
		}

	}

	public void setXGroup(double x) {
		this.x = x;
	}

	public void setYGroup(double y) {
		this.y = y;
	}

	public void setX(double x) {
		double marge = getUpLeftCorner().getX() - x;
		setXGroup(x);
		for (Shape shape : shapes) {
			shape.setUpLeftCornerX(shape.getUpLeftCorner().getX() - marge);
		}
	}

	public void setY(double y) {
		double marge = getUpLeftCorner().getY() - y;
		setYGroup(y);
		for (Shape shape : shapes) {
			shape.setUpLeftCornerY(shape.getUpLeftCorner().getY() - marge);
		}
	}

	public void setRotation(double rotation) {
		for (Shape shape : shapes) {
			shape.setRotation(rotation);
		}
	}

	public void setLineWidth(int lineWidth) {
		for (Shape shape : shapes) {
			shape.setLineWidth(lineWidth);
		}
	}

	public void setStrokeColor(String strokeColor) {
		for (Shape shape : shapes) {
			shape.setStrokeColor(strokeColor);
		}
	}

	public void setFillColor(String fillColor) {
		for (Shape shape : shapes) {
			if (shape.getShapeDimension() == ShapeDimension.SHAPE2D) {
				((Shape2D) shape).setFillColor(fillColor);
			}

		}
	}
}
