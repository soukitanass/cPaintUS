package cpaintus.models.composite;

import java.util.ArrayList;
import java.util.List;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.Shape2D;
import cpaintus.models.shapes.ShapeDimension;
import cpaintus.models.shapes.ShapeType;

public class ShapesGroup extends Shape2D {

	private List<Shape> shapes = new ArrayList<>();

	public ShapesGroup() {
		setShapeId("test");
		setShapeType(ShapeType.GROUP);
		this.shapeDim = ShapeDimension.SHAPE2D;

	}

	public void add(Shape s) {
		this.shapes.add(s);
	}

	public void remove(Shape s) {
		shapes.remove(s);
	}

	public void clear() {
		this.shapes.clear();
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWidth(double width) {
		for (Shape shape : shapes) {
			shape.setWidth(width);
		}

	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
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
		double marge = getX() - x;
		setXGroup(x);
		for (Shape shape : shapes) {
			shape.setX(shape.getX() - marge);
		}
	}

	public void setY(double y) {
		double marge = getY() - y;
		setYGroup(y);
		for (Shape shape : shapes) {
			shape.setY(shape.getY() - marge);
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
